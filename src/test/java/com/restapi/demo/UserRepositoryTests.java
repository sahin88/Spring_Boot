package com.restapi.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.restapi.user.entity.User;
import com.restapi.user.repository.UserRepository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
//@Rollback(false)

public class UserRepositoryTests {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	

	@Test
	public void testCreateUser() {
		User user= new User();
		user.setEmail("arif@sag.mail.com");
		BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
		String new_pass= encoder.encode("arif");
		user.setPassword(new_pass);
		user.setFirstname("arif");	
		user.setLastname("sag");
		user.setEnabled(false);
		user.setVerificationCode(RandomString.make(64));
		user.setCreatedTime(new Date());
		User saveduser=repo.save(user);
		User existuser= entityManager.find(User.class,saveduser.getId());
		assertThat(existuser.getEmail()).isEqualTo(user.getEmail());
		
		
		
		
		
		
	}
	@Test
	public void testFindUserByEmail() {
		String email="erdal@sag.mail.com";
		User user= repo.findByEmail(email);
		System.out.println("Eyyyyyyyyyy amerikkkkka"+user.getEmail());
		
		
		assertThat(user).isNotNull();
	}
	

}
