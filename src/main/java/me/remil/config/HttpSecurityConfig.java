package me.remil.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.web.filter.CorsFilter;

import me.remil.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

	private final String[] permitAllPath = { "/", "/api/v1/salt", "/api/v1/register", "/api/v1/check-username",
			"/api/v1/login"};
	
	private final String[] validOrigins = {
		"https://c-auth.azurewebsites.net",
		"http://localhost:3000"
	};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and()
			.csrf().disable();
		http.authorizeRequests((authz) -> authz.antMatchers(permitAllPath).permitAll().anyRequest().authenticated());
		http.apply(MyCustomDsl.customDsl());
		return http.build();
	}
	
	
	@Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList(validOrigins));
        config.addAllowedHeader("*");
//        config.addExposedHeader(HttpHeaders.AUTHORIZATION);
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


}

class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
	@Override
	public void configure(HttpSecurity http) throws Exception {
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
		jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
		http.addFilter(jwtAuthenticationFilter);
//        http.addFilterBefore(new Jwt, UsernamePasswordAuthenticationFilter.class);
	}

	public static MyCustomDsl customDsl() {
		return new MyCustomDsl();
	}
}