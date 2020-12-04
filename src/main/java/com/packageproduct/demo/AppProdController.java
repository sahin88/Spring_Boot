package com.packageproduct.demo;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.restapi.demo.MyUserDetailService;
import com.restapi.demo.Utility;
import com.restapi.product.entity.Product;
import com.restapi.user.entity.User;
import com.restapi.user.repository.UserRepository;

import net.bytebuddy.utility.RandomString;



@Controller
public class AppProdController {

	@Autowired
	ProductService productservice;
	@Autowired
	MyUserDetailService userservice;
	@Autowired
	JavaMailSender mailsender;
	
	@Autowired
	UserRepository repo;

	
	@RequestMapping("/producthome")
	public String productHome(Model model) {
		
		List<Product> listProducts= productservice.listall();
		model.addAttribute("listProducts", listProducts);
		return "production/product";
	}
		
	@RequestMapping("/product/new")
	public String New(Model model) {
		Product product= new Product();
		model.addAttribute("product", product);
		return "production/new_product_form";
	}
	@PostMapping("/product/save")
	public String ProdSave(@ModelAttribute("product") Product product) {
		
		productservice.save(product);
		return "redirect:/producthome";
	}
	
	@RequestMapping("/edit/{prod_id}")
	public ModelAndView ProdUpdate(@PathVariable(name="prod_id") Long prod_id) {
		ModelAndView maw= new ModelAndView("edit_product_form");
		Product product = productservice.get(prod_id);
		System.out.println("meltem "+product.toString());
		maw.addObject("product", product);
		return maw;
	}
	
	@RequestMapping("/delete/{prod_id}")
	public String ProdDelete(@PathVariable(name="prod_id") Long prod_id) {
		productservice.delete(prod_id);
		return "redirect:/producthome";
	}
	@RequestMapping("/forget_password")
	public String forgotPass() {
		
		return "forget_password_form";
	}
	
	
	@RequestMapping("/forget_password_submit")
	public  String ForgetPass(HttpServletRequest request, Model model) throws UnsupportedEncodingException, MessagingException {
		String email= request.getParameter("email");
		String token= RandomString.make(45);
		try {
		
		userservice.resetUpdatedPassword(token, email);
		
		String resetPasswordLink=Utility.getSiteURL(request)+"/reset_password?token="+token;
		System.out.println(resetPasswordLink);
		sendMail(email,resetPasswordLink);
		}
		catch(UsernameNotFoundException ex){
		model.addAttribute("error", ex.getMessage() );
		}
		return "forgot_password_sucess";
		
	}
	@RequestMapping("/reset_password")
	public String resetPassword(@Param(value="token") String token,Model model) {
		User user= userservice.get(token);
		if (user==null){
			model.addAttribute("title", "Reset your Password");
			model.addAttribute("message", "Invalid Token");
			return "message";
			}
		else{
			model.addAttribute("token", token );
			model.addAttribute("pageTitle", "Reset | Password" );
			}
		return "reset_password_form";
		
		
		
	}
	
	@RequestMapping("/change_password")
	public String changePass(HttpServletRequest request, Model model) {
		String token= request.getParameter("token");
		String password= request.getParameter("password");
		System.out.println("ibnaaa"+token+password);
		User user= userservice.get(token);
		user.setPassword(password);
		user.setResetPassToken("null");
		
		repo.save(user);
		
		
		return "login";
	}
	
	
	public void sendMail(String email,String resetLink) throws UnsupportedEncodingException, MessagingException {
			MimeMessage mime= mailsender.createMimeMessage();
			MimeMessageHelper helper= new MimeMessageHelper(mime);
			helper.setFrom("msahinnihasm@gmail.com","Helper team");
			helper.setTo(email);
			helper.setSubject("Link to reset your password!");
			String mailContent="<p> Hello Dear Sir or Madam,</p>";
			mailContent+="<p> You have requestet a  password reset,</p>";
			mailContent+="<p> Please click the Following link</p>";
			mailContent+="<br/>";
			mailContent+="<h3><a href=\""+resetLink+"\">Reset password</a></h3>";
			mailContent+="<br/>";
			mailContent+="<p>Your sincerly,</p>";
			mailContent+="<p>Sahin Support team</p>";
			helper.setText(mailContent, true);
			mailsender.send(mime);
			
	}
}
