package me.remil.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bitbucket.thinbus.srp6.js.SRP6JavascriptServerSession;
import com.google.common.cache.LoadingCache;

import me.remil.config.SrpSecurityConfig;
import me.remil.dto.CheckEmailExists;
import me.remil.dto.UserDTO;
import me.remil.entity.User;
import me.remil.service.UserService;

@RequestMapping("/api/authentication")
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://c-auth.azurewebsites.net"})
public class AuthenticationController {
	
	private UserService userService;
	
	private SrpSecurityConfig securityConfig;
	
	protected LoadingCache<User, SRP6JavascriptServerSession> sessionCache;

	@PostMapping("/register")
	public void register(@RequestBody UserDTO body) {
		userService.saveNewUser(body);
	}
	
	@GetMapping("/check-username")
	public CheckEmailExists checkUserExists(@RequestParam(value = "email", required = true) String email) {
		CheckEmailExists obj = 
				new CheckEmailExists(
						userService.checkUserExists(email)
						);
		
		return obj;
	}
	
	
	@GetMapping("/login/challenge")
	public void loginChallenge(@RequestParam(value = "email", required = true) String email) {
		final String fakeSalt = securityConfig.hash(securityConfig.saltOfFakeSalt+email);
		
		User user = userService.getUser(email);
		
		if (user != null) {
			SRP6JavascriptServerSession srpSession = sessionCache
					.getUnchecked(user);
			// continue from here
		}
		
	}
	
	
	@Autowired
	public void setSessionCache(LoadingCache<User, SRP6JavascriptServerSession> sessionCache) {
		this.sessionCache = sessionCache;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setSecurityConfig(SrpSecurityConfig securityConfig) {
		this.securityConfig = securityConfig;
	}
}
