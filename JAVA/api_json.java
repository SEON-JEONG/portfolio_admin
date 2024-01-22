package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.dbcp.BasicDataSource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class api_json {
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String result = null;
	
	public api_json(BasicDataSource dataSource) {
		try {
			this.con = dataSource.getConnection();
		}
		catch(Exception e) {
			System.out.println("데이터 베이스 접근 오류!");
		}
	};
	
	@SuppressWarnings("unckecked")
	public String api() {
		
		try {
			String sql ="select * from masteradmin order by aidx desc";
			this.ps = this.con.prepareStatement(sql);
			this.rs = this.ps.executeQuery();
			
			JSONArray ja = new JSONArray();
			while(this.rs.next()) {
				JSONObject jo = new JSONObject(); // 부속키 생성
				jo.put("aidx", this.rs.getString("aidx"));
				jo.put("aid", this.rs.getString("aid"));
				jo.put("aname", this.rs.getString("aname"));
				jo.put("aemail", this.rs.getString("aemail"));
				jo.put("atell", this.rs.getString("atell"));
				jo.put("adepart", this.rs.getString("adepart"));
				jo.put("aposition", this.rs.getString("aposition"));
				jo.put("approve", this.rs.getString("approve"));
				jo.put("adate", this.rs.getString("adate"));
				ja.add(jo); // 1차 배열로 그룹
			}
			JSONObject jo2 = new JSONObject(); // 대표 키 생성
			jo2.put("admin", ja); 
			this.result = jo2.toJSONString();
		
			this.rs.close();
			this.ps.close();
			this.con.close();
		}
		catch(Exception e) {
			System.out.println("데이터베이스 문법 오류!");
		}
		
		return this.result;
	};
};
