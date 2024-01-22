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
		}
		
		
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






	
	
	
	
	/*
	/*글수정후 첨부파일 새롭게 업데이트*********************************************
	@PostMapping("/notice_modifyok.do")
	public String notice_modifyok(@ModelAttribute("board")freeboard_dao dao, 
			@RequestParam("userfile") MultipartFile files,
			HttpServletRequest req, 
			Model m) {
		
		if(files.getOriginalFilename() != "") {	// 신규 첨부파일 있을경우
			
			String file_url ="";	// db에 저장하기 위한 경로
			
			if(files.getOriginalFilename() != "") {	// 첨부파일 용량이 초과되었을떄
				String file_name = files.getOriginalFilename();
				long file_size = files.getSize();
					if(file_size > 3145728) {
						this.script = "<script>"
								+ "alert('첨부파일은 3MB 이하로 업로드해주세요.');"
								+ "history.go(-1);"
								+ "</script>";
					}
					else {	// 첨부파일 용량이 맞을경우
						String url = req.getServletContext().getRealPath("") + "admin_freeboard/" + file_name;
						
							try {
									FileCopyUtils.copy(files.getBytes(), new File(url));
									file_url = "./admin_freeboard/"+file_name;
									dao.setAfile(file_url);
									this.se = sqlSessionFactory.openSession();
									int result = this.se.update("freeboardDB.freeboard_file",dao);
										
									if(result > 0) {
											this.script = "<script>"
													+ "alert('정상적으로 게시물이 수정 완료되었습니다.');"
													+ "location.href='./notice_list.do';"
													+ "</script>";
												}
										else {
											this.script = "<script>"
													+ "alert('데이터 오류로 인하여 수정이 불가능 합니다.');"
													+ "history.go(-1);"
													+ "</script>";
											}							
							} 
							catch (Exception e) {
								System.out.println("Database 오류");
							}
							finally {
								this.se.close();
							}
					}//else끝
				}
			
			
			
		}
		else {	// 신규 첨부파일 없을 경우 글만수정할때
			try {
				
				this.se = sqlSessionFactory.openSession();
				int result = this.se.update("freeboardDB.freeboard_update",dao);//값이 한개면 값을 던지면 되는데 여러개일때는 dao로 던짐
				
					if(result > 0) {
						this.script = "<script>"
								+ "alert('정상적으로 게시물이 수정 완료되었습니다.');"
								+ "location.href='./notice_list.do';"
								+ "</script>";
							}
					else {
						this.script = "<script>"
								+ "alert('데이터 오류로 인하여 수정이 불가능 합니다.');"
								+ "history.go(-1);"
								+ "</script>";
						}
				
			} 
			catch (Exception e) {
				System.out.println("Database 오류");
			}
			finally {
				this.se.close();
			}
			
			
		}
		m.addAttribute("script",this.script);
		return "script";
	};
	
	
	
	/*글수정************************************************************
	@GetMapping("/notice_modify.do")
	public String notice_modify(@RequestParam(defaultValue = "", required = false) String aidx, @RequestParam int num, Model m) {		
		if(aidx.equals("") || aidx == "") {	// 파라미터 값이 정상적이지 않을 경우
			this.script = "<script>"
					+ "alert('정상적인 접근이 아닙니다');"
					+ "location.href='./notice_list.do';"
					+ "</script>";
			} 
			else {
				
				try {
					this.se = sqlSessionFactory.openSession();
					freeboard_dao d = this.se.selectOne("freeboardDB.freeboard_view",aidx);
					//배열로 한번에 가져오기 뷰 출력부분
					ArrayList<String> onedata = new ArrayList<String>();			
					onedata.add(d.getAidx());		// 0번
					onedata.add(d.getAck());		//1
					onedata.add(d.getAsubject());	//2
					onedata.add(d.getAname());		//3
					onedata.add(d.getAfile());		//4
					onedata.add(d.getAtext());		//5
					onedata.add(d.getAck());
					
					m.addAttribute("one",onedata);
					m.addAttribute("num",num);
					
				} catch (Exception e) {
					System.out.println("Database 접속오류");
				}
				finally {
					this.se.close();
				}
		}//else 끝
		
		m.addAttribute("script",this.script);
		return "admin_notice_modify";
	};
	
	
	/*내용수정 첨부파일 삭제 ajax**************************************************
	@GetMapping("/file_delete.do")
	public String file_delete(@RequestParam(defaultValue = "", required = false) String fidx, HttpServletResponse res) {
		
		String msg ="no"; // front 에 전송
		PrintWriter pw = null;
		
		try {
			//System.out.println(fidx);
			pw = res.getWriter();
			this.se = sqlSessionFactory.openSession();
			int result = this.se.update("freeboardDB.fileupdate",fidx);
			
				if(result > 0) {
					msg = "ok";
				}
				pw.print(msg);
				
				
			
		} catch (Exception e) {
			System.out.println("Database 오류");
		}
		finally {
			this.se.close();
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*공지글 출력파트**********************************************************
	
	@GetMapping("/notice_view.do")
	public String notice_view(@RequestParam(defaultValue = "", required = false) String aidx, Model m, @RequestParam int num) {
								// RequestParm : 실시간 (얼럿작동안됨 바로 작동되기때문에)
								// HttpServletrequest : URL을 직접 쳐야지만 작동(로드된다음 작동)
								// ModelAttribute :실시간
		
		if(aidx.equals("") || aidx == "") {	// 파라미터 값이 정상적이지 않을 경우
			this.script = "<script>"
					+ "alert('정상적인 접근이 아닙니다');"
					+ "location.href='./notice_list.do';"
					+ "</script>";
		} 
		else {
			try {
				// 한번 더쓰면 스크립트 초기화됨(없는거 초기화된 스크립트를 넣어야 정상작으로 게시글이 보임)
				this.script="";
				
				this.se = sqlSessionFactory.openSession();
				// 데이터를 한개만 가져올경우 selectOne을 이용하여 데이터값을 적용합니다
				freeboard_dao d = this.se.selectOne("freeboardDB.freeboard_view",aidx);
				
				//배열로 한번에 가져오기 뷰 출력부분
				ArrayList<String> onedata = new ArrayList<String>();
				onedata.add(d.getAidx());		// 0번
				onedata.add(d.getAck());		//1
				onedata.add(d.getAsubject());	//2
				onedata.add(d.getAname());		//3
				onedata.add(d.getAfile());		//4
				onedata.add(d.getAtext());		//5
				onedata.add(d.getAck());	//6
				m.addAttribute("one",onedata);
				m.addAttribute("num",num);
				
			}
			catch (Exception e) {
				System.out.println("DB연결 오류");
			}
			finally {
				this.se.close();
			}		
		}
		//인자값없이 강제 접근 막기
		System.out.println(this.script);
		m.addAttribute("script",this.script);	
		return "admin_notice_view";
	}
	
	
	
	
	
	
	
	
	
	
	/* 삭제파트 **************************************************
	@GetMapping("/notice_delete.do")
	public String notice_delete(@RequestParam(defaultValue = "", required = false) String aidx, Model m) {
		System.out.println(aidx);
		if(aidx.equals("") || aidx=="") {	// 파라미터 값이 정상적이지 않을 경우
			this.script = "<script>"
					+ "alert('정상적인 접근이 아닙니다');"
					+ "location.href='./notice_list.do';"
					+ "</script>";
		}
		else {	//정상적으로 파라미터가 적용될 경우 데이터베이스에서 삭제되게
			try {
				this.se = sqlSessionFactory.openSession();
				
				// 한개의 값을 이용하여 삭제를 진행시 delete("mapper 이름",데이터 값)
				int result = this.se.delete("freeboardDB.freeboard_delete",aidx);//여기서 값을 받아서 데이터베이스에 글 삭제
				
				if(result > 0) {
					this.script = "<script>"
							+ "alert('정상적으로 게시물이 삭제 되었습니다.');"
							+ "location.href='./notice_list.do';"
							+ "</script>";
				}
				else {	// 문법 오류 발생
					this.script = "<script>"
							+ "alert('데이터가 비정상적으로 활용 되었습니다.');"
							+ "location.href='./notice_list.do';"
							+ "</script>";
				}
				
			} catch (Exception e) {
				System.out.println("Database 접속 오류");
			}
			finally {
				this.se.close();
			}
		}
		
		m.addAttribute("script",this.script);
		return "script";
	};
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/******************************************
	// main 파트
	@GetMapping("/notice_list.do")
	public String notice_main(HttpServletRequest req, Model m){	// 여기에 trows Exception 을 안쓰면 아래 try catch 써야함
		
		List<freeboard_dao> da = null;
		
		try {
			this.se = sqlSessionFactory.openSession();
		
			da = this.se.selectList("freeboardDB.freeboard_select");	
			//System.out.println(da.get(3).getAsubject());
			
			
		} catch (Exception e) {
			System.out.println("Dabase 오류 발생");
		}
		finally {
			this.se.close();
		}
		
		m.addAttribute("fdata",da);	//viewpage로 전송 (notice_main.jsp로 전송)
		return "admin_notice"; 
	};
	
	
	

	/************************************************************************
	
	
	//notice write.do
	@GetMapping("/notice_write.do")
	public String notice_writedo() {
		return "admin_notice_write";
	}
	
	
	
	
	@PostMapping("/notice_write.do")
	public String notice_write(@ModelAttribute("board")freeboard_dao f_dao, 
			@RequestParam(defaultValue = "", required = false) String ack,
			@RequestParam("userfile") MultipartFile files[], 
			HttpServletRequest req, Model m) throws Exception{	
		
		
		String file_url = "";
		
	
		if(files[0].getOriginalFilename() != "") {
		
			//멀티 파일 등록방법
			int file_ea = files.length;
			System.out.println(file_ea);	// 파일 갯수 체크
			
				//반복문
				int w= 0;
				while(w < file_ea) {
					String filename = files[w].getOriginalFilename();
					System.out.println(filename);
					
					long filesize = files[w].getSize();
					String url = "";
					
					
					
					
					//
					if(filesize > 3145728) {
						System.out.println("첨부 파일 용량이 초과되었습니다");	
					}
					else {
						
						url = req.getServletContext().getRealPath("") + "admin_freeboard/" + filename;
						FileCopyUtils.copy(files[w].getBytes(), new File(url));
						System.out.println(url);
						
						System.out.println(url);
						//데이터베이스에 경로 및 파일명을 저장시키는 변수
						file_url = "./admin_freeboard/" + filename;
						System.out.println(file_url);						
						}
					w++;
					}
				//정상적으로 업로드 되었을때!
					System.out.println("정상적으로 업로드 되었습니다.");	// 웹 경로 알려줌 (파일 저장할 웹경로 폴더 하나 만들어줘야함)	
					}
			
			if(f_dao.getAck() == null) {
				System.out.println(ack);
				f_dao.setAck("n");
			}
			

	
						
				//데이터 베이스에 저장하는 부분
				this.se = sqlSessionFactory.openSession();
				f_dao.setAfile(file_url);	//afile 컬럼으로 들어가게
				this.se.insert("freeboardDB.freeboard_insert",f_dao);
				this.se.close();
				
				
				
				this.script = "<script>"
						+ "alert('정상적으로 글이 등록되었습니다.');"
						+ "location.href='./notice_list.do';"
						+ "</script>";
				
				m.addAttribute("script",script);
			
				return "script";
			
			
		/*	// 첨부파일 한개 일때
		String filename = fattach.getOriginalFilename();
		String url = "";
		
		// 첨부파일 용량 제어
		long filesize = fattach.getSize();
		if(filesize > 3145728) {
			System.out.println("첨부 파일 용량이 초과되었습니다");	
		}
		else {
			url = req.getServletContext().getRealPath("") + "freeboard/" + filename;
			FileCopyUtils.copy(fattach.getBytes(), new File(url));
			System.out.println("정상적으로 업로드 되었습니다.");	// 웹 경로 알려줌 (파일 저장할 웹경로 폴더 하나 만들어줘야함)
		}
		//System.out.println(filesize);
		 }
*/





























