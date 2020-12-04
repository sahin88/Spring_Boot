package com.restapi.user.repository;



import org.springframework.transaction.annotation.Transactional;

import com.restapi.user.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {

	
	
	@Query("SELECT u FROM User u  WHERE u.email=?1")
	User findByEmail(String email);
	@Transactional
	@Query("UPDATE User t SET  t.enabled=true  WHERE t.id = ?1")
	@Modifying
	public void enable(Integer id);
	
	@Query("SELECT c FROM User c WHERE c.verificationCode=?1")
	User findByVerificationCode(String code);
	
	User findByresetPassToken(String token);
}
