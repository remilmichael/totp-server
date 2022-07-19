package me.remil.service.totp;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public List<TotpSecret> retreiveTotpSecrets() {
		String usernameFromToken  = 
				SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		
		List<Object[]> data = totpSecretRepository.fetchAllSecretsByEmail(usernameFromToken);
		List<TotpSecret> totpSecrets = new ArrayList<>();
		
		data.forEach(row -> {
			// keyId, secret, account, username
			// row[0], row[1], row[2], row[3]
			TotpSecret secret = new TotpSecret();
			secret.setUuid(row[0].toString());
			secret.setSecretKey(row[1].toString());
			secret.setAccount(row[2].toString());
			secret.setUsername(row[3].toString());
			totpSecrets.add(secret);
		});
		
		return totpSecrets;
	}
	
}
