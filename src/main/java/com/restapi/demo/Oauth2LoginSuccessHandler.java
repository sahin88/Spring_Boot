package com.restapi.demo;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.restapi.oauth2.service.CustomOaut2User;
import com.restapi.user.entity.User;
import com.restapi.user.repository.UserRepository;
@Component
public class Oauth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	@Autowired
	UserRepository repo;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		CustomOaut2User oauth2user= (CustomOaut2User) authentication.getPrincipal();
		String loginName=oauth2user.getLogin();
		User user=repo.findByEmail(loginName);
		System.out.println("loginName"+loginName);
		if (user==null ) {
			User usr= new User();
			usr.setEnabled(true);
			usr.setEmail(loginName);
			usr.setCreatedTime(new Date());
			usr.setFirstname(oauth2user.getFullName());
			usr.setLastname("hanci");
			usr.setPassword("null");
			usr.setAuthProvider(AuthenticationProvider.GITHUB);
			repo.save(usr);
			System.out.println("New User");
		}
		else {
			System.out.println("Existing Customer");
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
