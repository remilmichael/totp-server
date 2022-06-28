package me.remil.component;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtTokenProvider {
	
	Algorithm algorithm = null;
	
	private String jwtSecret;
	
	public JwtTokenProvider(String jwtSecret) {
		super();
		this.jwtSecret = jwtSecret;
	}



	public String getAccessToken(String subject, String issuer, int time) {
		algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
		Long currentTimeMillis = System.currentTimeMillis();
		String accessToken = JWT.create()
				.withSubject(subject)
				.withExpiresAt(new Date(currentTimeMillis + time))
				.withIssuer(issuer)
				.withClaim("iat", currentTimeMillis)
				.withClaim("iss", issuer)
				.sign(algorithm);
		return accessToken;		
	}
}
