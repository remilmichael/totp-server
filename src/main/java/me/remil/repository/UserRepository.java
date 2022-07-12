package me.remil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import me.remil.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	boolean existsByEmail(String email);
	
	@Query("select count(*) from User where email=:email")
	int countByEmail(@Param("email") String email);
	
	User findByEmail(String email);
	
	@Query("select encKey from User where email=:email")
	String getEnckeyByEmail(@Param("email") String email);
}
