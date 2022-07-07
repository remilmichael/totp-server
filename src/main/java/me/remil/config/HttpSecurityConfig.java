package me.remil.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.web.filter.CorsFilter;

import me.remil.component.JwtTokenProvider;
import me.remil.filter.JwtAuthenticationFilter;
import me.remil.filter.JwtAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

	private final String[] permitAllPath = {"/api/v1/salt", "/api/v1/register", "/api/v1/check-username",
			"/api/v1/login"};
	
	private final String[] validOrigins = {
		"https://cauth.remil.me",
		"http://localhost:3000",
		"http://127.0.0.1:3000",
	};
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.cors().and()
			.csrf().disable();
		http.authorizeRequests((authz) -> authz.antMatchers(permitAllPath).permitAll().anyRequest().authenticated());
		http.apply(MyCustomDsl.customDsl(jwtTokenProvider));
		return http.build();
	}
	
	
	@Bean
    public CorsFilter corsFilter() {
		 UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true);
	        config.setAllowedOrigins(Arrays.asList(validOrigins));
	        config.addAllowedHeader("*");
	        config.addAllowedMethod(HttpMethod.GET);
	        config.addAllowedMethod(HttpMethod.POST);
	        config.addAllowedMethod(HttpMethod.DELETE);
	        config.addAllowedMethod(HttpMethod.OPTIONS);
	        config.addAllowedMethod(HttpMethod.PUT);
	        source.registerCorsConfiguration("/**", config);
	        return new CorsFilter(source);
    }
}


class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
	
	private JwtTokenProvider jwtTokenProvider;
	
	public MyCustomDsl(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenProvider);
		jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
		http.addFilter(jwtAuthenticationFilter);
        http.addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
	}

	public static MyCustomDsl customDsl(JwtTokenProvider jwtTokenProvider) {
		return new MyCustomDsl(jwtTokenProvider);
	}
}