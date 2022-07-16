package me.remil.service.totp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import me.remil.dto.TotpSecret;
import me.remil.entity.TotpSecretKeys;
import me.remil.repository.TotpSecretRepository;

@Service
public class TotpSecretServiceImpl implements TotpSecretService {
	
	private TotpSecretRepository totpSecretRepository;


	@Override
	public void saveSecretKey(TotpSecret body) {
		// add more validation
		
		String usernameFromToken  = 
				SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		System.out.println("pass");
		TotpSecretKeys secret = new TotpSecretKeys();
		secret.setAccount(body.getAccount());
		secret.setUsername(body.getUsername());
		secret.setEmail(usernameFromToken);
		secret.setKeyId(body.getUuid());
		secret.setSecret(body.getSecretKey());
		totpSecretRepository.save(secret);
	}
	
	@Autowired
	public void setAutologinRepository(TotpSecretRepository totpSecretRepository) {
		this.totpSecretRepository = totpSecretRepository;
	}
	
}
