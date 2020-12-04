package com.restapi.demo;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.restapi.user.entity.User;
import com.restapi.user.repository.UserRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class MyUserDetailService  implements UserDetailsService {

	@Autowired
	UserRepository repo;

	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user= repo.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("Username not found");
		}

		return new MyUserDetails(user);
	

}
	public void resetUpdatedPassword( String token, String email1) throws UsernameNotFoundException {
	User user= repo.findByEmail(email1);
	if(user != null) {
		user.setResetPassToken(token);
		repo.save(user);
	}
	else {
		throw new UsernameNotFoundException("Username not found");
	}
		
	}
	
	public User get(String resetpassString) {
		return repo.findByresetPassToken(resetpassString);
	}
	
	public void UpdatePassword(User user, String newpassword) {
		BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(newpassword));
		user.setResetPassToken(null);
		repo.save(user);
	}

	
	
	
}
