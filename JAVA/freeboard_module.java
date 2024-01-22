package admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("freeboard")
public class freeboard_module {
	SqlSession se = null;
	List<freeboard_dao> nd = null;
	
	@Inject  
	private SqlSessionFactory sqlSessionFactory; 
	
	@Resource(name="sqlSession")
	private SqlSessionTemplate ns;
	
	
	// 전체글 불러오기 - ok
		public List select_notice(freeboard_dao freeboard_dao) {
			
			try {
				this.se = sqlSessionFactory.openSession();
				this.nd = this.se.selectList("freeboardDB.admin_notice_select"); 
			}
			catch (Exception e) {
				System.out.println("DB 접속 오류 발생 " + e);
			}
			finally {
				this.se.close();
			}
			return this.nd;
		};
		
	
		// 페이지 불러오기
		public List select_notice_pg(Integer aidx) {
			this.nd = ns.selectList("freeboardDB.admin_notice_pg_select", aidx);
			
			return this.nd;
		};
		
		// 글 view 불러오기 + 조회수 1 증가
		public freeboard_dao select_noticeOne(String aidx) {
			
			ns.update("freeboardDB.admin_notice_viewcount", aidx);
			freeboard_dao ndOne  = ns.selectOne("freeboardDB.admin_noticeOne_select", aidx);
			
			return ndOne;
		};
		
		// 게시글 저장
		public int insert_notice(freeboard_dao notice_dao) {
			int result = ns.insert("freeboardDB.admin_notice_insert", notice_dao);
			
			return result;
		};
		
		// 게시글 수정
		public int update_notice(freeboard_dao freeboard_dao, String a) {

			int result = 0;
			
			if(a == "none") { // 첨부파일 변경사항이 없을 경우
				result = ns.update("freeboardDB.admin_notice_updateFileNone",freeboard_dao);
			}
			else {
				result = ns.update("freeboardDB.admin_notice_updateFileYes",freeboard_dao);			
			}
			
			return result;
		};
		
		// 게시글 view 에서 삭제
		public int delete_view(String aidx) {
			int result = ns.delete("freeboardDB.admin_notice_deleteView",aidx);
			
			return result;
		};
		
		// 게시글 List 에서 삭제
		public int delete_list(Map<String, Object> aidx) {
			int result = ns.delete("freeboardDB.admin_notice_deleteList",aidx);
			System.out.println(result);
			
			return result;
		};
		
		// 파일 데이터 조회
		public List<freeboard_dao> select_files(Map<String, Object> list) {
			List<freeboard_dao> result  = ns.selectList("freeboardDB.admin_notice_file_list",list);
			
			return result;
		};
};
