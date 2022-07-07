package me.remil.component;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtTokenProvider {

	Algorithm algorithm = null;
	
	@Value("{jwt.secret}")
	String jwtSecret;

	public String getAccessToken(String subject, String issuer, long time) {
		algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
		Long currentTimeMillis = System.currentTimeMillis();
		long expiry = currentTimeMillis + time * 1000;
		String accessToken = JWT.create().withSubject(subject).withExpiresAt(new Date(expiry)).withIssuer(issuer)
				.withClaim("iat", new Date(currentTimeMillis)).withClaim("iss", issuer).sign(algorithm);
		return accessToken;
	}

	public String verifyToken(String token) {
		algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
		String username = null;

		try {
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT decodedJWT = verifier.verify(token);
			username = decodedJWT.getSubject();
		} catch (Exception e) {
			
		}
		
		return username;
	}
}
