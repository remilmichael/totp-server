package me.remil.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import me.remil.dto.SrpClientChallenge;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("Yay pass");
		super.successfulAuthentication(request, response, chain, authResult);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		SrpClientChallenge clientChallenge = new SrpClientChallenge();

		try {
			Map<String, String> requestMap = new ObjectMapper().readValue(request.getInputStream(), Map.class);
			clientChallenge.setEmail(requestMap.get("email"));
			clientChallenge.setA(requestMap.get("a"));
			clientChallenge.setM1(requestMap.get("m1"));
		} catch (IOException e) {
			throw new AuthenticationServiceException(e.getMessage(), e);
		}

		return authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(clientChallenge.getEmail(), clientChallenge));
	}
}
