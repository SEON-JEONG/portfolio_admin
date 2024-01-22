package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpSession;

import org.apache.commons.dbcp.BasicDataSource;

	public class db_select {
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String result = null;	
	
	public db_select(BasicDataSource dataSource) {
		try {
			this.con = dataSource.getConnection();	
		
		} 
		catch (Exception e) {
			System.out.println("Database 접속오류");
		}
	};
	
	//로그인
	public String loginck(String aid, String apass) {
		security sec = new security(apass);
		String pass = sec.md5_se();
		String sql = "select * from masteradmin where aid=?";
		try {
			this.ps = this.con.prepareStatement(sql);
			this.ps.setString(1, aid);
			this.rs = this.ps.executeQuery();
			while(this.rs.next()) {
				if(aid.equals(this.rs.getString("aid"))) {
					if(pass.equals(this.rs.getString("apass"))) {
						this.result = this.rs.getString("aid")+","+this.rs.getString("aname")+","+this.rs.getString("approve");
					}
				}
			}
			this.rs.close();
			this.ps.close();
			this.con.close();
		} catch (Exception e) {
			System.out.println("DB 데이터 찾기 오류!!" + e);
		}
		
		return this.result;
	};
};
