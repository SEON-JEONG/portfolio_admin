package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.dbcp.BasicDataSource;

public class db_update {

		Connection con = null;
		PreparedStatement ps = null;
		String result = null;
		
		public db_update(BasicDataSource dataSource) {
			try {
				this.con = dataSource.getConnection();
			} catch (Exception e) {
				System.out.println("Database 오류!");
			}
		};

		//로그인 오류 횟수 제한
		public String loginnum(String aid ,int num) {
			
			try {
				String sql = "update masteradmin set acount=? where aid=?";
				this.ps = this.con.prepareStatement(sql);
				this.ps.setInt(1,num);
				this.ps.setString(2,aid);
				int success = this.ps.executeUpdate();
				
				if(success > 0) {
					this.result = "ok";
				}
				else {
					this.result = "no";
				}	
				this.ps.close();
				this.con.close();
			} catch (Exception e) {
				System.out.println("DB 데이터 수정 오류!" + e);
			}
			return this.result;
		};
				
		public String agree(String aidx) {
			try {
				String sql = "update masteradmin set approve='Y' where aidx=?";
				this.ps = this.con.prepareStatement(sql);
				
				this.ps.setString(1,aidx);
				int success = this.ps.executeUpdate();
				
				if(success > 0) {
					this.result = "ok";
				}
				else {
					this.result = "no";
				}
				
				this.ps.close();
				this.con.close();
								
			} catch (Exception e) {
				System.out.println("DB 데이터 수정 오류!" + e);
			}
			return this.result;
		};


		// 회원가입 비승인1
		public String disagree(String aidx) {
			try {
				String sql = "update masteradmin set approve='N' where aidx=?";
				this.ps = this.con.prepareStatement(sql);
				
				this.ps.setString(1,aidx);
				int success = this.ps.executeUpdate();
				
				if(success > 0) {
					this.result = "ok";
				}
				else {
					this.result = "no";
				}
				
				this.ps.close();
				this.con.close();
				
				
			} catch (Exception e) {
				System.out.println("DB 데이터 수정 오류!" + e);
			}		
			return this.result;
		};
		
		
		// 회원가입 비승인2
		public String disagree_log(String aid) {
			try {
				String sql = "update masteradmin set approve='N' where aid=?";
				this.ps = this.con.prepareStatement(sql);
				
				this.ps.setString(1,aid);
				int success = this.ps.executeUpdate();
				
				if(success > 0) {
					this.result = "ok";
				}
				else {
					this.result = "no";
				}
				this.ps.close();
				this.con.close();
							
			} catch (Exception e) {
				System.out.println("DB 데이터 수정 오류!" + e);
			}	
			return this.result;
		};
		

		public String passloss(String aidx,String aid, String approve) {
			try {
				String slq = "update masteradmin set approve=? where aidx=? and aid=?";
				this.ps = this.con.prepareStatement(slq);
				this.ps.setString(1, approve);
				this.ps.setString(2, aidx);
				this.ps.setString(3, aid);
				int success = this.ps.executeUpdate();
				
				if(success > 0) {
					this.result = "ok";
				}
				else {
					this.result = "no";
				}
					this.ps.close();
					this.con.close();
				
			} catch (Exception e) {
				System.out.println("SQL 문법 오류");
			}
			return this.result;
		};
		
		
		
		public String fail5(String aid) {
			try {
				String sql = "update masteradmin set approve=? where aid=?";
				this.ps = this.con.prepareStatement(sql);
				this.ps.setString(1, "N");			
				this.ps.setString(2, aid);			
				int success = this.ps.executeUpdate();
				if(success > 0) {
					this.result = "ok"; 
				}
				else {
					this.result = "no";
				}
				this.ps.close();
				this.con.close();
			}
			catch(Exception e) {
				System.out.println("SQL문법 오류!!");
			}
			return this.result;
		};
};