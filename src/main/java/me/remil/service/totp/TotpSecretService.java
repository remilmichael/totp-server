package me.remil.service.totp;

import me.remil.dto.TotpSecret;

public interface TotpSecretService {
	public void saveSecretKey(TotpSecret body);
}
