package admin;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class cdn_webpage {
	
	@PostMapping("/cdn_flieok.do") 
	public String cdn_ftp(MultipartFile mfile, Model m) {
		
		System.out.println("dd");
		
		return null;
	};
};
