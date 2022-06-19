package me.remil.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitbucket.thinbus.srp6.js.SRP6JavascriptServerSession;

import me.remil.config.SrpSecurityConfig;
import me.remil.dto.SrpClientChallenge;
import me.remil.dto.SrpServerChallenge;
import me.remil.dto.UserDTO;
import me.remil.entity.User;
import me.remil.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private SrpSecurityConfig securityConfig;

	private SrpSessionService srpSessionService;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Autowired
	public void setSecurityConfig(SrpSecurityConfig securityConfig) {
		this.securityConfig = securityConfig;
	}

	@Autowired
	public void setSrpCacheService(SrpSessionService srpCacheService) {
		this.srpSessionService = srpCacheService;
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

	@Override
	public SrpServerChallenge fetchUserSalt(String email) {
		final String fakeSalt = securityConfig.hash(securityConfig.saltOfFakeSalt + email);

		User user = getUser(email);

		if (user != null) {
			SRP6JavascriptServerSession srpSession = srpSessionService.getSession(user.getEmail());
			String serverChallenge = srpSession.step1(email, user.getSalt(), user.getVerifier());
			System.out.println(srpSession.getState());
			srpSessionService.update(srpSession, user.getEmail());
			return new SrpServerChallenge(serverChallenge, user.getSalt());
		}
		
		// implement fake user
		return null;
	}

	@Override
	public void verifyClientChallenge(SrpClientChallenge challenge) {
		User user = getUser(challenge.getEmail());
		String m2 = "";
		
		if (user != null) {
			SRP6JavascriptServerSession srpSession = srpSessionService
					.getSession(user.getEmail());
			if (srpSession != null) {
				try {
					m2 = srpSession.step2(challenge.getA(), challenge.getM1());
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(m2);
			}
		}
	}
}
