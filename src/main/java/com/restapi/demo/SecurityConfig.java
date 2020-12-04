package com.restapi.demo;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.restapi.oauth2.service.CustomOauth2UserService; 

@EnableWebSecurity
@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsService userDetailsService;
//	@Autowired
//	private DataSource  datasource;
//	
	@Autowired
	Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;
//	
//	
//	@Bean 
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	
//	@Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider ();
//		provider.setUserDetailsService(userDetailsService);
//		provider.setPasswordEncoder( passwordEncoder());
//		return provider;
//		
//	}
//	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		// TODO Auto-generated method stub
//		
//		
//		auth.authenticationProvider(authenticationProvider());
//	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http
			.authorizeRequests()
			.antMatchers("/oauth2/**").permitAll()
			.antMatchers("/list_user").authenticated()
			.anyRequest().permitAll()
			.and()
				.formLogin()
					.loginPage("/login")
					.usernameParameter("email")
					.permitAll()
					.defaultSuccessUrl("/product/new")
			.and()
			.oauth2Login()
				.loginPage("/login")
				.userInfoEndpoint().userService(customauto)
				.and()
				.successHandler(oauth2LoginSuccessHandler)
				.defaultSuccessUrl("/producthome")
			.and()
			.logout().permitAll();
			
	}
//	@Bean
//	public PersistentTokenRepository PersistentTokenRepository() {
//		JdbcTokenRepositoryImpl tokenRepository= new JdbcTokenRepositoryImpl();
//		tokenRepository.setDataSource(datasource);
//		return tokenRepository;
//		
//	}
	
	@Autowired
	CustomOauth2UserService customauto ;
}

//.passwordParameter("password")
//.loginProcessingUrl("/doLogin")
//.failureUrl("/login_failure")
//.and()
//.rememberMe().tokenRepository(PersistentTokenRepository());
