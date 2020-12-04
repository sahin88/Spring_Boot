package com.restapi.demo;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.restapi.user.entity.User;

public class MyUserDetails implements UserDetails {
	private User user;
	
	
	public MyUserDetails(User user) {
		
		
		this.user=user;
//		;
//		this.active=user.isActive();
//		this.authorities=Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority:: new).collect(Collectors.toList());
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}


	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}


	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


	public String getFirstname() {
		return user.getFirstname();
	}




	public String getLastname() {
		return user.getLastname();
	}
	public String getFullname() {
		return user.getFirstname()+ "  "+ user.getLastname();
	}

}
	

//
//}
