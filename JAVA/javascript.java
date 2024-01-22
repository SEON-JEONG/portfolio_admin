package admin;

public class javascript {
	String output = null;
	
	public String okay(String aa, String bb ) {
		this.output = "<script>alert('"+aa+"');"
				+ "location.href='"+bb+"';"
				+ "</script>";
		return this.output;
	};
	
	public String nope(String aa) {
		this.output = "<script>alert('"+aa+"');"
				+ "history.go(-1);"
				+ "</script>";
		
		return this.output;
	};
};
