package me.remil.service.user;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.bitbucket.thinbus.srp6.js.SRP6JavascriptServerSession;
import com.bitbucket.thinbus.srp6.js.SRP6JavascriptServerSessionSHA256;

import me.remil.component.SrpSecurityMethods;
import me.remil.component.SrpSessionProvider;
import me.remil.dto.SrpClientChallenge;
import me.remil.dto.SrpServerChallenge;
import me.remil.dto.UserDTO;
import me.remil.entity.User;
import me.remil.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private SrpSecurityMethods securityConfig;

	private SrpSessionProvider srpSessionProvider;
	
	@Value("${thinbus.N}")
    public String N;
	
	@Value("${thinbus.g}")
	public String g;
	
	@Value("${thinbus.salt.of.fake.salt}")
    public String saltOfFakeSalt;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setSecurityConfig(SrpSecurityMethods securityConfig) {
		this.securityConfig = securityConfig;
	}

	@Autowired
	public void setSrpCacheService(SrpSessionProvider srpSessionProvider) {
		this.srpSessionProvider = srpSessionProvider;
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
		User user = getUser(email);

		if (user != null) {
			SRP6JavascriptServerSession srpSession = srpSessionProvider.getSession(email);
			
			if (srpSession.getState() != "INIT") {
				srpSessionProvider.destroySession(email);
				srpSession = srpSessionProvider.getSession(email);
			}
			
			String serverChallenge = srpSession.step1(email, user.getSalt(), user.getVerifier());
			System.out.println(srpSession.getState());
			srpSessionProvider.update(srpSession, user.getEmail());
			return new SrpServerChallenge(serverChallenge, user.getSalt());
		} else {
			final SRP6JavascriptServerSession fakeSession = 
					new SRP6JavascriptServerSessionSHA256(
					N, g);
			final String fakeSalt = securityConfig.hash(saltOfFakeSalt + email);
			String b = fakeSession.step1(email, fakeSalt, "0");
			srpSessionProvider.update(fakeSession, email);
			return new SrpServerChallenge(fakeSalt, b);
		}
	}

	@Override
	public void verifyClientChallenge(SrpClientChallenge challenge) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(challenge.getEmail(), challenge));
	}
}
