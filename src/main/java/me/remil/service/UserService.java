package me.remil.service;

import me.remil.dto.SrpClientChallenge;
import me.remil.dto.SrpServerChallenge;
import me.remil.dto.UserDTO;
import me.remil.entity.User;

public interface UserService {
	public void saveNewUser(UserDTO userDTO);
	
	public boolean checkUserExists(String email);
	
	public User getUser(String email);
	
	public SrpServerChallenge fetchUserSalt(String email);
	
	public void verifyClientChallenge(SrpClientChallenge challenge);
}
