package com.restapi.user.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.restapi.demo.AuthenticationProvider;


@Entity
@Table(name="users")
public class User {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable=false, unique=true, length=65)
	private String email;
	@Column(nullable=false, unique=true, length=65)
	private String firstname;
	@Column(nullable=false, unique=true, length=65)
	private String lastname;
	private String password;
	@Column(name="verification_code",updatable=false)
	private String verificationCode;
	private boolean enabled;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name="auth_provider")
	private AuthenticationProvider authProvider;
	
	
	@Column(name="created_time", updatable=false)
	private Date createdTime;
	
	@Column(name="reset_pass_token")
	private String resetPassToken;
	
	
	public String getResetPassToken() {
		return resetPassToken;
	}
	public void setResetPassToken(String resetPassToken) {
		this.resetPassToken = resetPassToken;
	}
	public AuthenticationProvider getAuthProvider() {
		return authProvider;
	}
	public void setAuthProvider(AuthenticationProvider authProvider) {
		this.authProvider = authProvider;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	private String roles;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	

}
