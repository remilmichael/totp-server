package me.remil.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import me.remil.component.JwtTokenProvider;
import me.remil.dto.SrpClientChallenge;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	private final String jwtSecret;

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String email = authResult.getName();
		int timeForExpiry = 30 * 24 * 60 * 60 * 1000;
		JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(jwtSecret);
		String accessToken = jwtTokenProvider.getAccessToken(email, request.getRequestURL().toString(), timeForExpiry);
		Map<String, String> token = new HashMap<>();
		token.put("access_token", accessToken);

		Cookie cookie = new Cookie("token", accessToken);
		cookie.setMaxAge(timeForExpiry);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setDomain(request.getServerName());
		cookie.setPath("/");
		response.addCookie(cookie);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		new ObjectMapper().writeValue(response.getOutputStream(), token);
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


