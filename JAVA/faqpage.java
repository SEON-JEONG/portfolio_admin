package admin;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class faqpage {
String script="";
int result = 0;

@Resource(name="faqm")
private faq_module fm;

	//FAQ 리스트 출력
	@RequestMapping("/admin_faq.do")
	public void faq_list(@RequestParam(required = false, defaultValue = "") String search, Model m) {
		List<faq_dao> list = null;
		if(search.equals("")) {
			list = fm.select_faq_listall(); //모듈에서 리스트 전체 확인
		}
		else {
			list = fm.search_faq(search);
		}
		m.addAttribute("search", search);
		m.addAttribute("list", list);
	};

	//FAQ 글등록
	@PostMapping("/faq_writeok.do")
	public String faq_writeok(@ModelAttribute faq_dao dao, Model m) {
		this.result = fm.insert_faq(dao);
	
	if(this.result > 0) {
		this.script = "<script>"
				+ "alert('FAQ 등록이 완료 되었습니다.');"
				+ "location.href='./admin_faq.do';"
				+ "</script>";
	}
	else {
		this.script = "<script>"
				+ "alert('FAQ 등록이 불가능 합니다.');"
				+ "history.go(-1);"
				+ "</script>";
	}
		m.addAttribute("script",this.script);
		return "script";
	};

	@RequestMapping("/admin_faq_write.do")
	public void amdin_faq_write() {
		
	};

	//FAQ 확인 및 수정 페이지
		@GetMapping("/admin_faq_modify.do")
		public void faq_modify(@RequestParam(defaultValue = "", required = false) String fidx, Model m) {
			
			if(fidx.equals("")) {
				this.script = "<script>"
						+ "alert('올바른 접근 방식이 아닙니다.');"
						+ "location.href='./admin_faq.do';"
						+ "</script>";
			}
			else {
				faq_dao faq = fm.select_faq_one(fidx);
				m.addAttribute("faq",faq);
			}
		};

		// FAQ 글수정
		@PostMapping("/faq_modifyok.do")
		public String faq_modifyok(@ModelAttribute faq_dao dao, Model m){
			this.result = fm.update_faq(dao);
			if(this.result > 0) {
				this.script = "<script>"
						+ "alert('FAQ 수정이 완료 되었습니다.');"
						+ "location.href='./admin_faq.do';"
						+ "</script>";
			}else {
				this.script = "<script>"
						+ "alert('FAQ 수정에 실패하였습니다.');"
						+ "history.go(-1);"
						+ "</script>";		
			}
			m.addAttribute("script",this.script);
			return "script";
		};
		
		
		//FAQ 글삭제
		@GetMapping("/faq_deleteok.do")
		public String admin_faq_deleteok(@RequestParam(required = false, defaultValue = "") String fidx, Model m) {
			if(fidx.equals("")) {
				this.script = "<script>"
						+ "alert('올바른 접근 방식이 아닙니다.');"
						+ "location.href='./admin_faq.do';"
						+ "</script>";		
			}
			else {
				this.result = fm.delete_faq(fidx);
				if(this.result > 0) {
					this.script = "<script>"
							+ "alert('FAQ 삭제가 완료 되었습니다.');"
							+ "location.href='./admin_faq.do';"
							+ "</script>";	
				}else {
					this.script = "<script>"
							+ "alert('FAQ 삭제에 실패하였습니다.');"
							+ "history.go(-1);"
							+ "</script>";
				}
			}
			m.addAttribute("script",this.script);
			return "script"; 
		};
};
