package me.remil.service.totp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import me.remil.dto.TotpSecret;
import me.remil.entity.TotpSecretKeys;
import me.remil.exception.InvalidUserException;
import me.remil.exception.UnauthorizedRequestException;
import me.remil.repository.TotpSecretRepository;
import me.remil.repository.UserRepository;

public class TotpSecretServiceImpl implements TotpSecretService {
	
	private TotpSecretRepository totpSecretRepository;
	private UserRepository userRepository;


	@Override
	public void saveSecretKey(TotpSecret body) {
		// add more validation
		
		String usernameFromToken  = 
				SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		
		if (!userRepository.existsByEmail(body.getEmail())) {
			throw new InvalidUserException("Unable to perform this action with the user");
		} else if (usernameFromToken.equals(body.getEmail())) {
			throw new UnauthorizedRequestException("User not authorized to perform this action");
		} else {
			TotpSecretKeys secret = new TotpSecretKeys();
			secret.setEmail(body.getEmail());
			secret.setKeyId(body.getUuid());
			secret.setSecret(body.getSecretKey());
			totpSecretRepository.save(secret);
		}
	}
	
	@Autowired
	public void setAutologinRepository(TotpSecretRepository totpSecretRepository) {
		this.totpSecretRepository = totpSecretRepository;
	}
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
