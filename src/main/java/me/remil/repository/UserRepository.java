package me.remil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import me.remil.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query("select count(*) from User where email=:email")
	int countByEmail(@Param("email") String email);
}
