package me.remil;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import me.remil.entity.Role;
import me.remil.service.autologin.AutologinService;
import me.remil.service.role.RoleService;

@SpringBootApplication
@EnableCaching
public class CAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CAuthServerApplication.class, args);
	}
	
	@Bean
	CommandLineRunner run(RoleService roleService) {
		return args -> {
			roleService.truncateTable();
			roleService.addRole(new Role(null, "ROLE_USER"));
			roleService.addRole(new Role(null, "ROLE_ADMIN"));
		};
	}

}
