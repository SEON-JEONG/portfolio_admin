package admin;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("faqm")
public class faq_module {

	@Inject
	private SqlSessionFactory sqlSessionFactory_faq;
	
	@Resource(name="sqlSession_faq")
	private SqlSessionTemplate ft;

	// list전체 글 불러오기
	public List<faq_dao> select_faq_listall(){
		List<faq_dao> result = ft.selectList("FaqDB.select_faq_all");
		
		return result;
	};
	
	//faq view
	public faq_dao select_faq_one(String fidx) {
		faq_dao result = ft.selectOne("FaqDB.select_faq_one",fidx);
		
		return result;
	};
	
	//faq modify
	public int update_faq(faq_dao dao) {
		int result = ft.update("FaqDB.update_faq", dao);
		
		return result;
	};
	
	//faq 글 삭제
	public int delete_faq(String fidx){
		int result = ft.delete("FaqDB.delete_faq", fidx);
		
		return result;
	};
	
	//검색
	public List<faq_dao> search_faq(String search){
		List<faq_dao> result = ft.selectList("FaqDB.search_faq", search);
		
		return result;
	};
	
	//글 등록
		public int insert_faq(faq_dao dao){
			int result = ft.insert("FaqDB.insert_faq",dao);
		
			return result;
		};
};