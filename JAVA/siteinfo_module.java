package admin;

import javax.annotation.Resource;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;


@Repository("siteif")
public class siteinfo_module {
	
	@Inject
	private SqlSessionFactory sqlSessionFactory2;
	
	@Resource(name="sqlSession2")	
	private SqlSessionTemplate sq;
	
	
	// 등록된 데이터 업데이트
	public int update_siteinfo(siteinfo_dao siteinfo_dao) {
		
		sq.delete("siteinfoDB.siteinfo_update");
		int result = sq.insert("siteinfoDB.siteinfo_insert",siteinfo_dao);
		System.out.println(result);
		
		return result;
	};
	
	
	//이미 등록된 데이터가 있는지 확인
	public int selectCk_siteinfo() {
		Integer result = sq.selectOne("siteinfoDB.siteinfo_selectOne");
		
		return  result;
	};
	
	//info 저장
	public int insert_siteinfo(siteinfo_dao siteinfo_dao) {
		System.out.println("aaa");
		int result = sq.insert("siteinfoDB.siteinfo_insert",siteinfo_dao);
		
		return result;
	};
	
	
	// 등록된 데이터 조회
	public siteinfo_dao select_siteinfo() {
		//System.out.println("dddd");
		siteinfo_dao result = sq.selectOne("siteinfoDB.siteinfo_select");
		
		return result;
	};
};
