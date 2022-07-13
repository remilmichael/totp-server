package me.remil.service.autologin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import me.remil.dto.AutologinSessionCreate;
import me.remil.dto.AutologinSessionRetrieve;
import me.remil.entity.Autologin;
import me.remil.exception.UnauthorizedRequestException;
import me.remil.repository.AutologinRepository;
import me.remil.repository.UserRepository;

@Service
public class AutoLoginServiceImpl implements AutologinService{
	
	private AutologinRepository autologinRepository;
	private UserRepository userRepository;
	
	public void saveAutoLogin(AutologinSessionCreate body) {
		
		String usernameFromToken  = 
				SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		
		if (!body.getEmail().equals(usernameFromToken)) {
			throw new UnauthorizedRequestException("User not authorized to perform this action");
		}
		
		Autologin session = new Autologin();
		
		if (userRepository.existsByEmail(body.getEmail())) {
			session.setEmail(body.getEmail());
			session.setEncKey(body.getEncKey());
			session.setExpiry(body.getExpiry());
			session.setUuid(body.getUuid());
			autologinRepository.save(session);
		}		
	}

	@Autowired
	public void setAutologinRepository(AutologinRepository autologinRepository) {
		this.autologinRepository = autologinRepository;
	}

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public AutologinSessionRetrieve retrieveSessionKey(String uuid) {
		String usernameFromToken  = 
				SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Autologin autologin = autologinRepository.findByUuid(uuid);
		
		if (usernameFromToken.equals(autologin.getEmail())) {
			return new AutologinSessionRetrieve(autologin.getEncKey());
		}
		
		throw new UnauthorizedRequestException("User not authorized to perform this action");
	}
}