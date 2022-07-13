package me.remil.service.autologin;

import me.remil.dto.AutologinSessionCreate;
import me.remil.dto.AutologinSessionRetrieve;

public interface AutologinService {
	public void saveAutoLogin(AutologinSessionCreate body);
	
	AutologinSessionRetrieve retrieveSessionKey(String id);
}
