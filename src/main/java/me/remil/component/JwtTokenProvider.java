package me.remil.component;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import me.remil.dto.TokenCredentials;
import me.remil.entity.Role;
import me.remil.repository.UserRepository;

@Component
public class JwtTokenProvider {

	Algorithm algorithm = null;
	
	@Value("{jwt.secret}")
	String jwtSecret;
	
	@Autowired
	private UserRepository userRepository;

	public String getAccessToken(String subject, String issuer, long time) {
		algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
		Long currentTimeMillis = System.currentTimeMillis();
		long expiry = currentTimeMillis + time * 1000;
		Collection<Role> roles = userRepository.findByEmail(subject).getRoles();
		
		String accessToken = 
				JWT.create()
				.withSubject(subject)
				.withExpiresAt(new Date(expiry))
				.withIssuer(issuer)
				.withClaim("iat", new Date(currentTimeMillis))
				.withClaim("iss", issuer)
				.withClaim("roles", roles.stream().map(p -> p.getName()).collect(Collectors.toList()))
				.sign(algorithm);
		return accessToken;
	}

	public TokenCredentials verifyToken(String token) {
		algorithm = Algorithm.HMAC256(jwtSecret.getBytes());

		try {
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT decodedJWT = verifier.verify(token);
			String username = decodedJWT.getSubject();
			String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
			return new TokenCredentials(roles, username);
		} catch (Exception e) {
			
		}
		
		return null;
	}
}
