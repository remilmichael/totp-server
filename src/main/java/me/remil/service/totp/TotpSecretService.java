package me.remil.service.totp;

import java.util.List;

import me.remil.dto.TotpSecret;

public interface TotpSecretService {
	public void saveSecretKey(TotpSecret body);
	
	public List<TotpSecret> retreiveTotpSecrets();
}
