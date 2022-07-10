package me.remil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import me.remil.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    @Modifying
	@Query(
            value = "truncate table role",
            nativeQuery = true
    )
	void truncateTable();
    
    Role findByName(String name);
}
