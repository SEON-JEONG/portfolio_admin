package admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.mysql.cj.xdevapi.JsonParser;

@Repository("airplane")
public class airplane_module {
	@Inject  
	private SqlSessionFactory sqlSessionFactory_air; 

	@Resource(name="sqlSession_air")
	private SqlSessionTemplate as;
	
	// 항공 코드 등록
	public int insert_air_category(airplane_dao air_dao) {
		int result = as.insert("AirplaneDB.insert_air_category",air_dao);
		
		return result;
	};
	
	// 항공 코드 등록 : 항공코드 중복 확인
	public int select_air_category_aircode(String aircode) {
		int result = as.selectOne("AirplaneDB.select_air_category_aircode_ck",aircode);	
		
		return result;
	};
	
	// 항공 코드 수정
	public int update_air_category(airplane_dao air_dao) {
		int result = as.update("AirplaneDB.update_air_category",air_dao);
		return result;
	};
	
	//항공 코드 검색
	public List<airplane_dao> search_air_code(String search_ch,String search){
		
		Map<String, String> m = new HashMap<String, String>();
		m.put("search_ch", search_ch);
		m.put("search", search);
		List<airplane_dao> result = as.selectList("AirplaneDB.search_air_code",m);
		return result;
	};
	
	//다수 항공코드 삭제
	public int delete_air_code(String aidx) {
		int result = as.delete("AirplaneDB.deleteList_air_code",aidx);
		
		return result;
	};
	
	// 전체 항공 코드 불러오기
	public List<airplane_dao> select_air_category(String part){
		List<airplane_dao> result = null;
		if(part == "all") {
			result = as.selectList("AirplaneDB.select_air_category_all");
		}
		else if(part == "distinct") {
			result = as.selectList("AirplaneDB.select_air_category");
		}
		return result;
	};
	
	// 특정 항공 코드 불러오기
	public airplane_dao select_air_category_one(String aidx) {
		airplane_dao result = as.selectOne("AirplaneDB.select_air_category_one",aidx);
				
		return result;
	};
	
	// 선택한 공항 코드의 항공사명 추출
	public List<String> select_airplane_code_ch(String airplane_code){
		List<String> result = as.selectList("AirplaneDB.select_airname_ch", airplane_code);
		
		return result;
	};
	
	// 선택한 공항 코드, 항공사명의 항공코드 추출
	public List<String> select_aircode_ch(airplane_dao air_dao){
		List<String> result = as.selectList("AirplaneDB.select_aircode_ch", air_dao);
		
		return result;
	};
	
	// 선택한 항공코드의 항공편명 추출
	public String select_flight(String aircode) {
		String result = as.selectOne("AirplaneDB.select_airflight", aircode);
		
		return result;
	};
	
	// 항공편 등록
	public int insert_air_plane(airplane_dao air_dao) {
		 String idx = as.selectOne("AirplaneDB.select_aircode_index", air_dao.getAircode());
		 air_dao.setAidx(idx);
		 int result = as.insert("AirplaneDB.insert_air_plane", air_dao);
	
		return result;
	};
	
	// 항공편 리스트 출력 
	public List<airplane_dao> select_air_plane(){
		List<airplane_dao>result = as.selectList("AirplaneDB.select_air_plane_list");
		
		return result;
	};
	
	//항공편 리스트 검색
	public List<airplane_dao> search_air_plane(String search_ch,String search){
			
			Map<String, String> m = new HashMap<String, String>();
			m.put("search_ch", search_ch);
			m.put("search", search);
			List<airplane_dao> result =  as.selectList("AirplaneDB.select_air_plane_search",m);
			
			return result;
	};
	
	// 페이지 불러오기
	public List select_air_product_pg(Integer pg_l) {
		List<airplane_dao> ad = as.selectList("AirplaneDB.admin_product_pg_select", pg_l);
		
		return ad;
	};
	
	//선택 항공편 삭제
	public int delete_air_product(String pidx) {
		int result = as.delete("AirplaneDB.deleteList_air_product",pidx);
	
		return result;
	};
	
	////좌석 및 예약등록 : 검색
	public List<airplane_dao> select_air_seat(String search_ch,String search){
		Map<String, String> m = new HashMap<String, String>();
		m.put("search_ch", search_ch);
		m.put("search", search);
		List<airplane_dao> result = as.selectList("AirplaneDB.select_air_seat_search",m);
		
		return result;
	};
	
	// 예약 미설정, 설정완료  데이터 추출
	public List<airplane_dao> select_air_seat_ck(String ck){
		List<airplane_dao> result = null;
		if(ck == "none") {
			result = as.selectList("AirplaneDB.select_air_seat_none_reserve");			
		}else {
			result = as.selectList("AirplaneDB.select_air_seat_yes_reserve");			
		}
		
		return result;
	};

	
	// 예약 정보 저장
	public ArrayList insert_air_reserve(airplane_dao air_dao) {
		
		String[] pidx = air_dao.getPidx().split(",");
		String[] start_date = air_dao.getRstart_date().split(",");
		String[] end_date = air_dao.getRend_date().split(",");
		String[] rprice = air_dao.getRprice().split(",");
		System.out.println(Arrays.toString(pidx));
		System.out.println(Arrays.toString(start_date));
		System.out.println(start_date.length);
		System.out.println(start_date[0]);
		
		ArrayList<Boolean> ck = new ArrayList<Boolean>(); // 중간에 실패한 데이터 확인 용
		ArrayList idx = new ArrayList(); //false 위치값 찾기
		ArrayList falses = new ArrayList(); // 실패한 ndix 넘버들만 추출
		
		int w = 0;
		while( w < start_date.length) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("pidx", pidx[w]);
			m.put("rstart_date", start_date[w]); ////
			m.put("rend_date", end_date[w]);
			m.put("rprice", rprice[w]);
			
			int r =  as.insert("AirplaneDB.insert_air_reserve",m);
			
			if(r > 0) {
				ck.add(true);
				as.update("AirplaneDB.update_air_reserve_ck_Y",pidx[w]); // 예약 미설정 해제
			}else {
				ck.add(false);
			}	
			w++;
		}
		
		w = 0; // false의 위치찾기 
		while(w < ck.size()) {
			if(ck.get(w).equals(false)) {
				idx.add(w);
			}
			w++;
		}
		w = 0; // false대상 pidx 찾기
		while( w < idx.size()) {
			falses.add(pidx[(int) idx.get(w)]);
			w++;
		}
		
		return falses;
	};
	
	// 예약 완료 리스트
	public List<airplane_dao> select_air_reserve_ok(){
		List<airplane_dao> result = as.selectList("AirplaneDB.select_air_reserve_ok");
		
		return result;
	};
	
	// 예약 취소
	public int delete_user_reserve(String uidx) {
		int result = as.delete("AirplaneDB.delete_user_reserve",uidx);
		
		return result;
	};
	
	//예매 리스트 검색
	public List<airplane_dao> search_ticketing(String search_ch,String search){
		
		Map<String, String> m = new HashMap<String, String>();
		m.put("search_ch", search_ch);
		m.put("search", search);
		List<airplane_dao> result = as.selectList("AirplaneDB.search_user_reserve",m);
		
		return result;
	};
};
