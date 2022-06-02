package me.remil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import me.remil.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
}
