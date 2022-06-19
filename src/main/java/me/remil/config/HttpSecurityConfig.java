package me.remil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class HttpSecurityConfig {

	private final String[] permitAllPath = {
			"/",
			"/api/v1/salt",
			"/api/v1/register",
			"/api/v1/check-username",
			"/api/v1/login",
			"/api/v1/test",
			"/api/v1/test-evict",
			"/api/v1/test-update"
	};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
        .authorizeRequests((authz) -> authz
            .antMatchers(permitAllPath).permitAll()
            .anyRequest().authenticated()
        );
		http.csrf().disable();
		return http.build();
	}
}
