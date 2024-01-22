package admin;

import java.security.MessageDigest;

	// 비밀번호 암호화 클래스
	public class security {
	String text = null;
	MessageDigest md = null;
	
	public security(String pass) {
		
	try {
		this.text = pass;
		this.md = MessageDigest.getInstance("MD5");
		
	} catch (Exception e) {
		System.out.println("올바른 데이터가 반영 되지 않았습니다.");
		}
	};
	
	//md5로 가져옴!
	public String md5_se() {
	this.md.update(this.text.getBytes());
	byte word[] = this.md.digest();
	StringBuffer sb = new StringBuffer();
	
		for(int a = 0; a < word.length; a++) {
			sb.append(Integer.toString((word[a] & 0xff)+0x100,16).substring(1));
		}
		this.text = sb.toString();
		return this.text;
	};	
};
