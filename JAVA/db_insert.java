package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.commons.dbcp.BasicDataSource;

public class db_insert {
	Connection con = null;
	PreparedStatement ps = null;
	int result = 0; 
	
	public db_insert(BasicDataSource dataSource) {
		try {
			this.con = dataSource.getConnection();
			
		} catch (Exception e) {
			System.out.println("Database 접속 오류 발생");
		}
	};
	
	// 사용자 추가 파트
	public int user_insert(String password, join_dto jd) {
		try {
			String sql ="insert into masteradmin values('0',?,?,?,?,?,?,?,'N',0,now())";
			this.ps = this.con.prepareStatement(sql);
			
			this.ps.setString(1, jd.getAid());
			this.ps.setString(2, password);
			this.ps.setString(3, jd.getAname());
			this.ps.setString(4, jd.getAemail());
			this.ps.setString(5, jd.getAtell());
			this.ps.setString(6, jd.getAdepart());
			this.ps.setString(7, jd.getAposition());
			
			int result = this.ps.executeUpdate();
			System.out.println(this.ps);
			
			this.ps.close();
			this.con.close();
			
		} catch (Exception e) {
			System.out.println("Database 문법 오류" +e);
		}
		return this.result;					
	};
};
