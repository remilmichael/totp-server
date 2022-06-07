package me.remil.service;

import java.text.SimpleDateFormat;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.remil.dto.UserDTO;
import me.remil.entity.User;
import me.remil.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void saveNewUser(UserDTO userDTO) {
		User user = new User();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		user.setEmail(userDTO.getEmail());
		user.setSalt(userDTO.getSalt());
		user.setVerifier(userDTO.getVerifier());
		user.setEncKey(userDTO.getEncKey());
		user.setCreationDate(new Date(System.currentTimeMillis()));
	}

	@Override
	public boolean checkUserExists(String email) {
		int count = userRepository.countByEmail(email);
		if (count == 0) {
			return false;
		}
		return true;
	}
}
