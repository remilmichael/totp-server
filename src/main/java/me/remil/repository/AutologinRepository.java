package me.remil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import me.remil.entity.Autologin;

public interface AutologinRepository extends JpaRepository<Autologin, Long> {
		
	Autologin findByUuid(String uuid);
}