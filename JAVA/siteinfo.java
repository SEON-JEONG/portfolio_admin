package admin;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

	@Controller
	public class siteinfo {
	String script="";
	int result = 0;
	
	@Autowired
	BasicDataSource dataSource;
	@Resource(name="siteif")
	private siteinfo_module sm;
	
	
	@RequestMapping("/footer.do")
	public String site_list(HttpServletRequest req, Model m) {
		
		siteinfo_dao result = sm.select_siteinfo();
		
		m.addAttribute("siteinfo",result);
		
		return "footer";
	};
	
	
	@GetMapping("/admin_siteinfo.do")
	public String admin_siteinfo(HttpServletRequest req, Model m) {
		HttpSession se = req.getSession();
		
		
		siteinfo_dao result = sm.select_siteinfo();
		m.addAttribute("siteinfo",result);
	
		return "admin_siteinfo";
	};
	
	
	@PostMapping("/admin_siteinfo_save.do")
	public String admin_siteinfo_save(@ModelAttribute("siteinfo") siteinfo_dao siteinfo_dao, Model m) {
		int count = sm.selectCk_siteinfo();
		System.out.println(count);
		if(count == 0) {
			this.result = sm.insert_siteinfo(siteinfo_dao);
			if(this.result > 0) {
				this.script = "<script>"
						+ "alert('등록이 완료 되었습니다.');"
						+ "location.href='./admin_siteinfo.do';"
						+ "</script>";
			}
			else {
				this.script = "<script>"
						+ "alert('등록에 실패 하였습니다.');"
						+ "history.go(-1);"
						+ "</script>";
			}
		}
		else {
			this.result = sm.update_siteinfo(siteinfo_dao);
			
			if(this.result > 0) {
				this.script = "<script>"
						+ "alert('등록이 완료 되었습니다..');"
						+ "location.href='./admin_siteinfo.do';"
						+ "</script>";
			}
			else {
				this.script = "<script>"
						+ "alert('등록에 실패 하였습니다.');"
						+ "history.go(-1);"
						+ "</script>";
			}
		}
		m.addAttribute("script",this.script);
		return "script";
	
	};
};
