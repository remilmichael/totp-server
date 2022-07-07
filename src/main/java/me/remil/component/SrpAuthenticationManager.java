package me.remil.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.bitbucket.thinbus.srp6.js.SRP6JavascriptServerSession;
import me.remil.dto.SrpClientChallenge;


@Component
public class SrpAuthenticationManager implements AuthenticationManager {
	
	private SrpSessionProvider sessionProvider;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SrpClientChallenge challenge = (SrpClientChallenge) authentication.getCredentials();
		String email = (String) authentication.getPrincipal();
		
		SRP6JavascriptServerSession srpSession = sessionProvider.getSession(email);
		sessionProvider.destroySession(email);
		if (srpSession != null) {
			try {
				srpSession.step2(challenge.getA(), challenge.getM1());
			} catch (Exception e) {
				throw new BadCredentialsException("Invalid username or password");
			}
		}
		return authentication;
	}

	@Autowired
	public void setSessionProvider(SrpSessionProvider sessionProvider) {
		this.sessionProvider = sessionProvider;
	}
}