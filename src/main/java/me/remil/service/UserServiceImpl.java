package me.remil.service;

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
		if (userRepository.existsByEmail(userDTO.getEmail())) {
			// throw exception
		}
		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setSalt(userDTO.getSalt());
		user.setVerifier(userDTO.getVerifier());
		user.setEncKey(userDTO.getEncKey());
		user.setCreationDate(new Date(System.currentTimeMillis()));
		userRepository.save(user);
	}

	@Override
	public boolean checkUserExists(String email) {
		if (userRepository.existsByEmail(email)) {
			return true;
		}
		return false;
	}

	@Override
	public User getUser(String email) {
		return userRepository.findByEmail(email);
	}
}
