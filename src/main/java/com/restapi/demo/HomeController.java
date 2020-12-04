package com.restapi.demo;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.restapi.user.entity.User;
import com.restapi.user.repository.UserRepository;

import net.bytebuddy.utility.RandomString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;

@Controller

public class HomeController {
	@Autowired
	private UserRepository repo;
	@Autowired
	private MyUserDetailService service;
	@Autowired
	JavaMailSender mailSender;
	@GetMapping("")
	public String Hello(Model model) {
		List<User> Listusers=repo.findAll();
		model.addAttribute("Listusers",Listusers);
		
		return "users";
	}
	
	@GetMapping("/register")
	public String user(Model model) {
		model.addAttribute("user",new User());

		
		return "signup_form";
	}
	public void sendEmailVerification(User user, String SiteURL) throws UnsupportedEncodingException, MessagingException {
		String subject="please verify your Registration";
		String senderName="Sahin Gmbh";
		String mailContent="<p> Dear "+user.getFirstname()+" "+ user.getLastname()+" ,"+"</p>";
		mailContent+="Please click the link below to verify your Account!";
		String verifyURL=SiteURL+"/verify?code="+user.getVerificationCode();
		mailContent+="<h3><a href=\""+verifyURL+"\">VERIFY</a></h3>";
		System.out.println("verifyURL   "+verifyURL);
		mailContent+="Fuck you alll!";
		MimeMessage message=mailSender.createMimeMessage();
		MimeMessageHelper helper= new MimeMessageHelper(message);
		helper.setFrom("msahinnihasm@gmail.com",senderName);
		helper.setTo(user.getEmail());
		helper.setSubject(subject);
		helper.setText(mailContent, true);
		mailSender.send(message);
	}
	public boolean verify(String verification) {
		User user= repo.findByVerificationCode(verification);
		if(user.isEnabled() || user ==null) {
			return false;
		}
		else {
			System.out.println("user evi yaorul"+user.getId());
			repo.enable(user.getId());
			return true;
		}
			
	}
	
	@PostMapping("/process_registor")
	public String process_registor(User user, HttpServletRequest request) {
		BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
		String new_pass= encoder.encode(user.getPassword());
		user.setPassword(new_pass);
		user.setCreatedTime(new Date());
		String randomstr=RandomString.make(64);
		System.out.println("randomstr"+randomstr);
		user.setVerificationCode(randomstr);
		user.setEnabled(false);
		repo.save(user);
		String siteURL=Utility.getSiteURL(request);
	
			try {
				sendEmailVerification(user, siteURL);
			} catch (UnsupportedEncodingException | MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	
		
		return "register_succes";
	}
	
	


	@GetMapping("/list_user")
	public String ListUserView(Model model) {
		List<User> Listusers=repo.findAll();
		model.addAttribute("Listusers",Listusers);
		
		return "users";
	}
	
	@GetMapping("/verify")
	public String VerificationAccount(@Param("code") String code, Model model) {
		boolean verified=verify(code);
		String pageTitle= verified?"Verification has been done sucessfully":"Verification failed!";
		model.addAttribute("pageTitle",pageTitle);
		
		return verified ? "verified_sucess":"verified_failed";
	}
	

	@GetMapping("/login")
	public String loginPage() {
		
		return "login";
	}

}
