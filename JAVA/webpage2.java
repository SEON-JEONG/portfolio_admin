package admin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

@Controller
public class webpage2 {

	@Resource(name="freeboard")
	private freeboard_module nm; 
	@Resource(name="cdn")
	private cdn_module cdn;
	String script ="";
	SqlSession se = null;
	int result = 0;
	
	// 글 보기
		@GetMapping("/admin_notice_view.do")
		public String admin_notice_view(@RequestParam(defaultValue = "", required = false) String aidx,@RequestParam int num, Model m) {
			this.script="";
			if(aidx.equals("")) {
				script ="<script>"
						+ "alert('올바른 접근 방식이 아닙니다.');"
						+ "location.href='./admin_notice.do';"
						+ "</script>";
			}
			else {
				freeboard_dao ndOne = nm.select_noticeOne(aidx);
				m.addAttribute("ndOne",ndOne);
				m.addAttribute("num",num);
			}
			
			m.addAttribute("script",this.script);
			return "admin_notice_view";
		};
		
		
		// 글 List에서 삭제
		@GetMapping("/admin_notice_deleteAll.do")
		public String admin_notice_deleteAll(@RequestParam(defaultValue = "", required = false) ArrayList aidx, Model m) {	
			ArrayList falses = new ArrayList(); 
			
			this.script="";
			if(aidx.size() == 0) {
				script ="<script>"
						+ "alert('올바른 접근 방식이 아닙니다.')"
						+ "location.href='./admin_notice.do'"
						+ "</script>";
			}
			else {
				Collections.reverse(aidx); 
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("datas", aidx);
				
				//FTP 서버에서 데이터 삭제
				List<freeboard_dao> files = nm.select_files(map);
				
				this.cdn = new cdn_module();
				ArrayList idx = cdn.cdn_delete_list(files);
				
				if(idx.size() == 0) {
					this.result = nm.delete_list(map); 	
					if(this.result > 0) {
						script ="<script>"
								+ "alert('삭제가 완료 되었습니다.');"
								+ "location.href='./admin_notice.do';"
								+ "</script>";
					}
					else {
						script ="<script>"
								+ "alert('삭제가 불가능합니다.');"
								+ "history.go(-1);"
								+ "</script>";
					}
				}else {
					int w = 0;
					int f = 0;
					
					while(w < idx.size()) {
						falses.add(aidx.remove((int)idx.get(w)-f));
						f++;
						w++;
					}
					
					map.put("datas", aidx);
					this.result = nm.delete_list(map); 
				}
				if(this.result > 0 ) {			
					script ="<script>"
							+ "alert('해당 게시물을 삭제하였습니다.');"
							+ "location.href='./admin_notice.do';"
							+ "</script>";
				}
				else {
					script ="<script>"
							+ "alert('오류로 삭제가 불가능합니다');"
							+ "history.go(-1);"
							+ "</script>";			
				}	
			}
			m.addAttribute("script",this.script);
			return "script";
		};
		
		
		// 글view에서 삭제
		@GetMapping("/admin_notice_delete.do")
		public String admin_notice_delete(@RequestParam(defaultValue = "", required = false) String aidx,
				@RequestParam(defaultValue = "", required = false) String xfile,
				Model m) {
				
			this.script="";
			if(aidx.equals("")) {
				script ="<script>"
						+ "alert('올바른 접근 방식이 아닙니다');"
						+ "location.href='./admin_notice.do';"
						+ "</script>";
			}
			else {
				this.result = nm.delete_view(aidx);
					
				if(xfile.equals("")) {
					if(this.result > 0) {
						script ="<script>"
								+ "alert('삭제가 완료 되었습니다');"
								+ "location.href='./admin_notice.do';"
								+ "</script>";		
					}
					else {
						script ="<script>"
								+ "alert('게시글 삭제에 실패하였습니다');"
								+ "history.go(-1);"
								+ "</script>";
					}		
				}else {
					this.cdn = new cdn_module();
					boolean ok = cdn.cdn_delete(xfile);
					if(ok == false) {
						script ="<script>"
								+ "alert('게시글 삭제에 실패하였습니다');"
								+ "history.go(-1);"
								+ "</script>";
					}
					else if(ok == true && this.result > 0) {
						script ="<script>"
								+ "alert('삭제가 완료 되었습니다');"
								+ "location.href='./admin_notice.do';"
								+ "</script>";
					}
				}	
			}
			m.addAttribute("script",this.script);
			return "script";
		};
		
		// 글 수정 화면
		@GetMapping("/admin_notice_modify.do")
		public String admin_notice_modify(@RequestParam(defaultValue = "", required = false) String aidx,@RequestParam int num, Model m) {
			this.script="";
			
			if(aidx.equals("")) {
				this.script ="<script>"
						+ "alert('올바른 접근 방식이 아닙니다.');"
						+ "location.href='./admin_notice.do';"
						+ "</script>";
			}
			else {
				freeboard_dao ndOne = nm.select_noticeOne(aidx);
				m.addAttribute("ndOne",ndOne);
				m.addAttribute("num",num);
			}
			
			m.addAttribute("script",this.script);
			return "admin_notice_modify";
		};
		

		// 글 수정 완료
		@PostMapping("/admin_notice_modifyok.do")
		public String admin_notice_modifyok(@ModelAttribute("nd") freeboard_dao freeboard_dao,
				@RequestParam("userfile") MultipartFile xfile,
				@RequestParam(defaultValue = "", required = false) String oldfile,
				HttpServletRequest req,
				Model m) {
		
			if(freeboard_dao.getAck() == null) {
				freeboard_dao.setAck("N");
			}
			
			if(xfile.getOriginalFilename() == "") {
				this.result = nm.update_notice(freeboard_dao,"none");
				if(this.result > 0) {
					this.script ="<script>"
							+ "alert('게시글 수정이 완료 되었습니.');"
							+ "location.href='./admin_notice.do';"
							+ "</script>";
				}else {
					this.script ="<script>"
								+ "alert('게시글 수정에 실패하였습니다.');"
								+ "history.go(-1);"
								+ "</script>";
				}
			}
			else { 
				long filesize = xfile.getSize();
				
				if(filesize > 2097152) { // 2MB
					
					System.out.println("첨부파일 용량 초과 되었습니다");
					this.script ="<script>"
							+ "alert('첨부파일 용량은 최대 '2MB' 입니다.');"
							+ "history.go(-1);"
							+ "</script>";
				}
				else {
					this.cdn = new cdn_module();
					boolean oldok = cdn.cdn_delete(oldfile);
					
					if(oldok == true) {
						Date today = new Date();
						SimpleDateFormat fr = new SimpleDateFormat("yyyyMMddHHmmss"); 	
						String day = fr.format(today);
						String filename = day+"-"+xfile.getOriginalFilename();
						//------------------------------------------------------------------------
						this.cdn = new cdn_module();
						boolean ok = cdn.cdn(xfile, filename);
						System.out.println(filename);
						if(ok == true) {
							System.out.println("정상적으로 업로드 되었습니다");
							freeboard_dao.setAfile(filename);
							
							this.result = nm.update_notice(freeboard_dao,"yse");
							
								if(this.result > 0) {
									this.script ="<script>"
											+ "alert('게시글 수정이 완료 되었습니.');"
											+ "location.href='./admin_notice.do';"
											+ "</script>";
								}else {
									this.script ="<script>"
											+ "alert('게시글 수정이 불가능합니다.');"
											+ "history.go(-1);"
											+ "</script>";
								}
						}
						else {
							System.out.println("FTP 경로 오류 발생 되었습니다.");
							this.script ="<script>"
									+ "alert('게시글 수정이 불가능합니다.');"
									+ "history.go(-1);"
									+ "</script>";
						}
					}else {
						System.out.println("FTP 경로 오류 발생 되었습니다2");
						this.script ="<script>"
								+ "alert('게시글 수정이 불가능합니다.');"
								+ "history.go(-1);"
								+ "</script>";
					}		
				}
			}							
			m.addAttribute("script",this.script);			
			return "script";
		};
		
		
		// 전체글 불러오기
		@GetMapping("/admin_notice.do")
		public String admin_notice( Model m,@ModelAttribute("nd") freeboard_dao freeboard_dao,@RequestParam(required = false, defaultValue = "0") String page) {
			
			int ea = 0; 
			int pg_l= 0; 
			int view_no = 1; 
			
			if(page.equals("1") || page.equals("0")) {
				
				pg_l = 0;
			}
			else {
				int pg = (Integer.parseInt(page)*15)-15;
				view_no = Integer.parseInt(page);
				pg_l = pg;
			}

			m.addAttribute("view_no", view_no);
			List<freeboard_dao> result = nm.select_notice_pg(pg_l);
			m.addAttribute("list",result);
			List<freeboard_dao> nd = nm.select_notice(freeboard_dao);
			m.addAttribute("allnotice",nd);		
			return "admin_notice";
		};
		

		//글 작성 완료
		@PostMapping("/admin_notice_writeok.do")
		public String admin_notice_writeok(@ModelAttribute("nd") freeboard_dao freeboard_dao,
				@RequestParam("userfile") MultipartFile xfile,
				HttpServletRequest req,
				Model m) {
			this.script="";

			if(freeboard_dao.getAck() == null) {
				freeboard_dao.setAck("N");
			}
			if(xfile.getOriginalFilename() == "") {
				freeboard_dao.setAfile("");
				this.result = nm.insert_notice(freeboard_dao);				
			}
			else {
				long filesize = xfile.getSize();
	
				if(filesize > 2097152) { // 2MB
					
					System.out.println("첨부파일 용량 초과 되었습니다");
					script ="<script>"
							+ "alert('첨부파일 용량이 초과 되었습니다.');"
							+ "history.go(-1);"
							+ "</script>";
				}
				else {
					Date today = new Date();
					SimpleDateFormat fr = new SimpleDateFormat("yyyyMMddHHmmss"); 		
					String day = fr.format(today);
					String filename = day+"-"+xfile.getOriginalFilename();
					
					this.cdn = new cdn_module();
					boolean ok = cdn.cdn(xfile, filename);

					if(ok == true) {
						System.out.println("정상적으로 업로드 되었습니다");
						freeboard_dao.setAfile(filename);
						
						this.result = nm.insert_notice(freeboard_dao);							
					}
					else {
						System.out.println("FTP 경로 오류 발생 되었습니다");
					}
				}
			}
			if(this.result > 0) {
				this.script ="<script>"
						+ "alert('게시글 등록이 완료 되었습니.');"
						+ "location.href='./admin_notice.do';"
						+ "</script>";
			}else {
				this.script ="<script>"
						+ "alert('게시글 등록에 실패하셨습니다.');"
						+ "history.go(-1);"
						+ "</script>";	
			}	
			m.addAttribute("script",this.script);		
			return "script";
		};	
};
