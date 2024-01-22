package admin;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;
import com.mysql.cj.xdevapi.JsonArray;
import com.mysql.cj.xdevapi.JsonParser;
import com.mysql.cj.xdevapi.JsonValue;

import admin.javascript;

@Controller
public class airplanepage {
	String output = "";
	javascript js = new javascript();
	int result = 0;
	PrintWriter pw = null;
	List<HashMap<String, String>> mp = null;
	
	@Resource(name="airplane")
	private airplane_module am;
	
	//고객 예매 취소
	@GetMapping("/admin_reserve_delete.do")
	public String admin_reserve_delete(@RequestParam(required = false, defaultValue = "") String uidx,Model m){
		int result = am.delete_user_reserve(uidx);
		
		if(this.result > 0) {
			this.output = js.okay("취소가 완료되었습니다","./admin_ticketing.do");
		}
		else {
			this.output = js.nope("취소가 불가능합니다");			
		}		
		m.addAttribute("output",this.output);
		return "outputpage";
	};
	
	//좌석 및 예약등록 : 검색, 미설정 항공코드
	@RequestMapping("/admin_seat.do")
	public void admin_seat_search(@RequestParam(required = false, defaultValue = "") String seat_search_ch,
			@RequestParam(required = false, defaultValue = "") String seat_search,
			@RequestParam(required = false, defaultValue = "") String air_resuerve_ck,
			Model m){
		List<airplane_dao> list = null;	
		List<airplane_dao> nonelist = am.select_air_seat_ck("none");
		List<airplane_dao> yeslist = am.select_air_seat_ck("yes");
		String ch = null;
		
		if(seat_search.equals("")) { // 아무 검색도 안했을 시 & 첫 화면
				ch = "출발지"; 
				if(seat_search_ch.equals("도착지")) {
					ch = "도착지";
				}
		}
		else if(!seat_search.equals("")){ //검색이 있을 시
			
			list = am.select_air_seat(seat_search_ch, seat_search);
			
			if(list.size() == 0) {
				list = null;
			}
			ch = "출발지"; 
			if(seat_search_ch.equals("도착지")) {
				ch = "도착지";
			}
		} 
		
		if(air_resuerve_ck.equals("none")){
			list = nonelist;
			if(list.size() == 0) {
				list = null;
			}
		}else if(air_resuerve_ck.equals("yes")) {
			list = yeslist;
			
			if(list.size() == 0) {
				list = null;
			}
		}
		
		m.addAttribute("seat_search", seat_search);
		m.addAttribute("seat_search_ch", ch);
		m.addAttribute("dataList", list);
		m.addAttribute("nonelist", nonelist);
		m.addAttribute("yeslist", yeslist);
		
	};
	
	// 좌석 예약 설정
	@PostMapping("/admin_seat_ok.do")
	public String admin_seat_ok(@ModelAttribute airplane_dao air_dao, Model m) {
		
		ArrayList falses = am.insert_air_reserve(air_dao);
		String[] pidx = air_dao.getPidx().split(",");
		
		if(falses.size() == 0) {
			this.output = js.okay("예약설정이 모두 완료되었습니다","./admin_seat.do");
		}
		else if (falses.size() == pidx.length) {
			this.output = js.nope("예약설정이 모두 실패되었습니다");
		}
		else {
			this.output = this.js.okay("해당 항공편 외 예약 설정에 성공하였습니다 [실패 예약 넘버 : "+falses+"]","./admin_seat.do");	
		}
		
		m.addAttribute("output",this.output);
		return "outputpage";
	};
	

	//예매리스트 
	@RequestMapping("/admin_ticketing.do")
	public void admin_ticketing(Model m,@RequestParam(required = false, defaultValue = "") String search,
			@RequestParam(required = false, defaultValue = "") String search_ch) {
		List<airplane_dao> list = null;
		String ch = null;
		
		if(search.equals("")) {
			list = am.select_air_reserve_ok();
			ch = "고객명";
			if(search_ch.equals("연락처")) {
				ch = "연락처";
			}		
		}else {
			list = am.search_ticketing(search_ch, search);
			ch = "고객명";
			if(search_ch.equals("연락처")) {
				ch = "연락처";
			}
		}
		m.addAttribute("list",list);
		m.addAttribute("search",search);
		m.addAttribute("search_ch",ch);
	};
	
			
	// 항공코드 등록
	@PostMapping("/admin_category_writeok.do")
	public String admin_category_writeok(@ModelAttribute("ad") airplane_dao air_dao, Model m) {

		this.result = am.insert_air_category(air_dao);
		if(this.result > 0) {
			this.output = js.okay("코드 등록이 완료되었습니다","./admin_category_write.jsp");
		}
		else {
			this.output = js.nope("코드 등록에 실패하였습니다");			
		}	
		m.addAttribute("output",this.output);
		return "outputpage";
	};
	
	
	//항공 코드 등록 : 항공코드 중복 체크
	@GetMapping("/aircode_ck.do")
	public void aircode_ck(@RequestParam String aircode, HttpServletResponse res) throws Exception{

		this.result = am.select_air_category_aircode(aircode);
		
		this.pw = res.getWriter();
		this.pw.print(this.result);
		this.pw.close();
	};
	
	
	//항공코드 리스트 & 검색
	@RequestMapping("/admin_category.do")
	public void admin_category(Model m,@RequestParam(required = false, defaultValue = "") String search,
				@RequestParam(required = false, defaultValue = "") String search_ch) {
			List<airplane_dao> datalist = null;
			List<airplane_dao> ea = am.select_air_category("all");
			String ch = null;
			if(search.equals("")) {
				datalist = ea;
				ch= "항공사명";
				if(search_ch.equals("항공코드")) {
					ch="항공코드";
				}
			}else {
				datalist = am.search_air_code(search_ch, search);
			}	
			m.addAttribute("search",search);
			m.addAttribute("search_ch", ch);
			m.addAttribute("datalist", datalist);
			m.addAttribute("ea", ea);	
	};
	
	
	//항공코드 수정 시 view
	@GetMapping("/admin_category_modify.do")
	public void admin_category_modify(@RequestParam String aidx, Model m) {
		airplane_dao adone = am.select_air_category_one(aidx);
		m.addAttribute("adone",adone);
	};
	
	
	//항공코드 수정 완료
	@PostMapping("/admin_category_modifyok.do")
	public String admin_category_modifyok(@ModelAttribute airplane_dao air_dao, Model m) {
		this.result = am.update_air_category(air_dao);
	
		if(this.result > 0 ) {
			this.output = this.js.okay("항공코드 수정을 완료 하였습니다", "./admin_category.do");					
		}
		else {
			this.output = this.js.nope("항공편 수정에 실패하였습니다");
		}
		m.addAttribute("output",this.output);
		return "outputpage";
	};
	
	
	// 항공코드 List 에서 삭제 
	@GetMapping("/admin_category_deleteAll.do")
	public String admin_category_deleteAll(@RequestParam(defaultValue = "", required = false) String aidx, Model m) {
		
		if(aidx.length() == 0) {
			this.output = this.js.okay("올바른 접근 방식이 아닙니다","./admin_category.do");
		}
		else {
			this.result = am.delete_air_code(aidx);
			if(this.result > 0 ) {
				this.output = this.js.okay("항공편 삭제를 완료 하였습니다", "./admin_category.do");					
			}
			else {
				this.output = this.js.nope("항공편 삭제에 실패하였습니다");
			}
		}
		m.addAttribute("output",this.output);
		return "outputpage";
	};
	
	
	//항공편 등록시 코드,항공사명,항공코드,항공편명 불러오기
	@GetMapping("/admin_product_write.do")
	public void admin_product_write(Model m) {
		List<airplane_dao> dataList = am.select_air_category("distinct");
		
		this.mp = new ArrayList<HashMap<String,String>>();
		int ea = dataList.size();
		int w = 0;
		while(w < ea) {
        	HashMap<String, String> a = new HashMap<>();
        	a.put("airplane_code",dataList.get(w).getAirplane_code());
			a.put("airuse",dataList.get(w).getAiruse());
        	mp.add(a);
        	w++;
		}
		m.addAttribute("dataList",mp);
	};
	
	
	// 선택한 공항코드의 데이터 추출
	@GetMapping("/admin_product_ch.do")
	public void admin_product_ch(
			@RequestParam String ck,
			@RequestParam(required = false) String airplane_code,
			@RequestParam(required = false) String airname,
			@RequestParam(required = false) String aircode,
			@ModelAttribute airplane_dao air_dao,
			HttpServletResponse res
			) throws Exception{
	
		if(ck.equals("name")) {
			List<String> dataList = am.select_airplane_code_ch(airplane_code);
			
			this.pw = res.getWriter();
			this.pw.print(dataList);
			this.pw.close();
		}
		else if(ck.equals("code")){
			air_dao.setAirname(airname);
			air_dao.setAirplane_code(airplane_code);
			List<String> dataList = am.select_aircode_ch(air_dao);
			this.pw = res.getWriter();
			this.pw.print(dataList);
			this.pw.close();
		}
		else if(ck.equals("flight")) {
			String result = am.select_flight(aircode);
			this.pw = res.getWriter();
			this.pw.print(result);
			this.pw.close();
		}		
	};
	
	//신규 항공편 등록
	@PostMapping("/admin_product_writeok.do")
	public String admin_product_writeok(@ModelAttribute airplane_dao air_dao, Model m) {
		
		if(air_dao.getAirplane_code().equals("")){
			this.output = js.nope("해당 항공편에 오류가 발생하였습니다. 다시 시도하시기 바랍니다");
		}
		else {
			this.result = am.insert_air_plane(air_dao);
			if(this.result > 0) {
				this.output = js.okay("항공편 등록이 완료되었습니다","./admin_product_write.do");
			}
			else {
				this.output = js.nope("항공편 등록에 실패하였습니다");			
			}
		}
		m.addAttribute("output",this.output);
	
		return "outputpage";
	};

	
	// 항공편 리스트 & 검색
	@RequestMapping("/admin_product.do")
	public void admin_product(@RequestParam(required = false, defaultValue = "") String search,
			@RequestParam(required = false, defaultValue = "") String search_ch,
			@RequestParam(required = false, defaultValue = "0") String page
			,Model m) {
		List<airplane_dao> ea = null;
		 
		int pg_l= 0;  
		int view_no = 1; 	
		if(page.equals("1") || page.equals("0")) {
			
			ea = am.select_air_plane();
			pg_l = 0;
		}
		else {		
			ea = am.select_air_plane();
			int pg = (Integer.parseInt(page)*3)-3;
			view_no = Integer.parseInt(page);
			pg_l = pg;
		}
		m.addAttribute("view_no", view_no);
		List<airplane_dao> result = am.select_air_product_pg(pg_l);
		String ch = null;
		
		if(search.equals("")) {
			ea = am.select_air_plane();	
		}else {
			result= am.search_air_plane(search_ch, search);
			ea = result;	
		}
		ch = "s_airname"; 
		if(search_ch.equals("s_aircode")) {
			ch = "s_aircode";
		}
		m.addAttribute("search",search);
		m.addAttribute("search_ch",ch);
		m.addAttribute("ad", result);
		m.addAttribute("ea", ea.size());
	};
	
	
	// 항공편 List 삭제
	@GetMapping("/admin_product_deleteAll.do")
	public String admin_product_deleteAll(@RequestParam(defaultValue = "", required = false) String pidx, Model m) {
		if(pidx.length() == 0) {
			this.output = this.js.okay("올바른 접근 방식이 아닙니다","./admin_product.do");
		}
		else {
			int result = am.delete_air_product(pidx);
			if(this.result > 0 ) {
				this.output = this.js.okay("항공편 삭제를 완료 하였습니다", "./admin_product.do");					
			}
			else {
				this.output = this.js.nope("항공편 삭제에 실패하였습니다");
			}
		}
		m.addAttribute("output",this.output);
		return "outputpage";
	};
};