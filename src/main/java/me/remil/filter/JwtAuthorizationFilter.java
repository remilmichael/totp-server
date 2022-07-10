package me.remil.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import me.remil.component.JwtTokenProvider;
import me.remil.dto.TokenCredentials;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	
	public final JwtTokenProvider jwtTokenProvider;
	
	private final Collection<String> excludePattern = Arrays.asList("/api/v1/salt", "/api/v1/register", "/api/v1/check-username",
			"/api/v1/login");


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		TokenCredentials tokenCredentials = null;
		
		try {
			String authorizationToken = request.getHeader(HttpHeaders.COOKIE);
			String token = authorizationToken.substring("token=".length());
			tokenCredentials = jwtTokenProvider.verifyToken(token);
		} catch(NullPointerException e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			Map<String, String> error = new HashMap<>();
			error.put("message", "No authorization token found");
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			new ObjectMapper().writeValue(response.getOutputStream(), error);
		}
		
		
		if (tokenCredentials == null) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			Map<String, String> error = new HashMap<>();
			error.put("message", "User not authorized to perform this operation!!!");
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			new ObjectMapper().writeValue(response.getOutputStream(), error);
		} else {
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
			Arrays.stream(tokenCredentials.getRoles()).forEach(role -> {
				authorities.add(new SimpleGrantedAuthority(role));
			});
			UsernamePasswordAuthenticationToken authenticationToken =
	                new UsernamePasswordAuthenticationToken(tokenCredentials.getUsername(), null, authorities);
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			filterChain.doFilter(request, response);
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		
		AntPathMatcher pathMatcher = new AntPathMatcher();
		
		return excludePattern.stream().anyMatch(p -> {
			return pathMatcher.match(p, request.getServletPath());
		});
	}

}
