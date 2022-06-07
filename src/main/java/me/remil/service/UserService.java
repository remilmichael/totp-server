package me.remil.service;

import me.remil.dto.UserDTO;

public interface UserService {
	public void saveNewUser(UserDTO userDTO);
	
	public boolean checkUserExists(String email);
}
