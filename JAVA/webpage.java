package admin;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

import javax.jws.WebParam.Mode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbcp.BasicDataSource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class webpage {	
	int count = 0;
	
	@Autowired
	BasicDataSource dataSource;

	@GetMapping("/admin_main.do")
	public void admin_main() {
	};
	
	
	//admin_main 회원정보 리스트 출력
	@PostMapping("/mainlist.do")	
	public String new_pass(@RequestParam(defaultValue="") String aid,
							@RequestParam(defaultValue="") String aidx,
							@RequestParam(defaultValue="") String approve,
							Model m) {
		
		String script = "";
		String result ="";
		
		if(aidx.equals("") || aid.equals("")) {
			script = "<script>"
					+ "alert('승인/미승인 에 실패하였습니다');"
					+ "history.go(-1);"
					+ "</script>";
		}
		else {
			db_update db = new db_update(dataSource);
			result = db.passloss(aidx,aid,approve);
			if(result.equals("y")) {
				script ="<script>"
						+ "alert('가입이 승인 되었습니다.')"
						+ "location.href='./admin_main.do'"
						+ "</script>";
			}
			else {
				script ="<script>"
						+ "alert('데이터 오류로 인하여 에러가 발생하였습니다.')"
						+ "location.href='./admin_main.do'"
						+ "</script>";
				}	
			}

			m.addAttribute("script", script);
			return "script";
	};
	
	// 로그아웃 부분 ok
		@GetMapping("/logout.do")
		public String logout(HttpServletRequest req, Model m) {
			HttpSession session = req.getSession();
			session.removeAttribute("aid");
			session.removeAttribute("aname");	//두개 날림
			String script = "<script>"
					+"alert('정상적으로 로그아웃 되셨습니다.');"
					+"location.href='./index.jsp';"
					+"</script>";
					
			m.addAttribute("script", script);		
					
			return "script";
		};
		

	
	// 로그인 중복체크 ok
	@GetMapping("/idcheck.do")
	public String id_ajax(@RequestParam String aid, Model m) {

		String script = "";
		String msg = null;
		
		try {
			Connection con = dataSource.getConnection();
			String slq = "select count(*) as ctn from masteradmin where aid=?";
			PreparedStatement ps = con.prepareStatement(slq);
			ps.setString(1, aid);
			ResultSet rs = ps.executeQuery();
			
				while(rs.next()) {
					if(rs.getString("ctn").equals("0")) {
						msg = "0";
					}
					else {
						msg="1";
					}
				}
				rs.close();
				ps.close();
				con.close();
			} catch (Exception e) {
				msg = "error";
			}
			m.addAttribute("msg",msg);
			return null;
	};
	

	// 회원가입 페이지 (add_master) ok
	@PostMapping("/joinok.do")
	public String joinok(@RequestParam String aid,
						@RequestParam String apass,
						@RequestParam String aname,
						@RequestParam String aemail,
						@RequestParam String atell,
						@RequestParam String adepart,
						@RequestParam String aposition,
						Model m) {
		//암호화 부분
		security sc = new security(apass);
		String password = sc.md5_se();
		String script ="";
		
		join_dto jd = new join_dto();
		jd.setAid(aid);	
		jd.setAname(aname);
		jd.setAemail(aemail);
		jd.setAtell(atell);
		jd.setAdepart(adepart);	
		jd.setAposition(aposition);
		
		db_insert insert = new db_insert(dataSource);
		int success = insert.user_insert(password,jd);

		if(success >= 0) {
				script = "<script>"
							+"alert('관리자 등록이 정상적으로 완료 되었습니다.');"
							+"location.href='./index.jsp';"
							+"</script>";
		}
		else {
				script = "<script>"
						+"alert('관리자 등록에 문제가 발생 하였습니다.');"
						+"history.go(-1);"
						+"</script>";
		}
	
		m.addAttribute("script",script);		
		return "script";
	};
	
	// 회원가입 승인 처리!!
	@GetMapping("/agree.do")
	public String inok(@RequestParam String aidx, Model m) {
		String script = "";
		db_update dbu = new db_update(dataSource);
		String result =  dbu.agree(aidx);
		if(result == "ok") {
			script = "<script>"
					+"alert('정상적으로 승인처리 되었습니다');"
					+"history.go(-1);"
					+"</script>";
		}
		else {
			script = "<script>"
					+"alert('가입승인 처리가 불가능합니다.');"
					+"history.go(-1);"
					+"</script>";
		}
		
		m.addAttribute("script",script);
		return "script";
	};
	
	@GetMapping("/disagree.do")
	public String inblock(@RequestParam String aidx, Model m) {
		String script = "";
		db_update dbu = new db_update(dataSource);
		String result =  dbu.disagree(aidx);
		
		if(result == "ok") {
			script = "<script>"
					+"alert('정상적으로 비승인처리 되었습니다');"
					+"history.go(-1);"
					+"</script>";
		}
		else {
			script = "<script>"
					+"alert('비승인 처리가 불가능합니다.');"
					+"history.go(-1);"
					+"</script>";
		}
		
		m.addAttribute("script",script);
		return "script";
	}
	
	
	/******************************************/


	// index 로그인 파트  -- 성공
	@PostMapping("/loginok.do")
	public String loginck(HttpServletRequest req, 
			@RequestParam String aid, 
			@RequestParam String apass,
			Model m) {
		
		HttpSession session = req.getSession();
	
		db_select dbs = new db_select(dataSource);
		String success = dbs.loginck(aid,apass);
		String script = "";
		
		if(success == null|| success == "") {
			script = "<script>"
					+"alert('아이디 및 패스워드를 확인하세요');"
					+"history.go(-1);"
					+"</script>";
			
			if (this.count > 5) {
				script = "<script>"
						+"alert('패스워드 오류를 5번 초과하셨습니다. 문의해주시기 바랍니다.');"
						+"history.go(-1);"
						+"</script>";
				db_update dbu = new db_update(dataSource);
				String result =  dbu.disagree_log(aid);
			}
			else {
				db_update dbu = new db_update(dataSource);
				dbu.loginnum(aid, this.count);
				script = "<script>"
						+"alert('아이디 및 비밀번호를 다시 확인하여 주세요.\\n(5회이상 오류 발생시 로그인접근이 차단됩니다)');"
						+"history.go(-1);"
						+"</script>";
				this.count++;
			}
		}
		else{
			String admininfo[] = success.split(",");			
			System.out.println(Arrays.asList(admininfo));
			
			if(admininfo[2].equals("N")) {
				script = "<script>"
						+"alert('관리자의 승인처리가 필요합니다');"
						+"history.go(-1);"
						+"</script>";
			}
			else {
					session.setAttribute("aid", admininfo[0]);
					session.setAttribute("aname", admininfo[1]);
					script = "<script>"
							+"alert('정상적으로 로그인 되었습니다.');"
							+"location.href='./admin_main.do';"
							+"</script>";

					// 횟수 초기화
					db_update dbu = new db_update(dataSource);
					dbu.loginnum(aid, 0);
			}
		}
		
		m.addAttribute("script",script);
		return "script";
	};


	@RequestMapping("/api_json.do")
	public String api_server(Model m) {
		api_json aj = new api_json(dataSource);
		String datalist = aj.api();
		m.addAttribute("datalist",datalist);
		return null;
	};
};
