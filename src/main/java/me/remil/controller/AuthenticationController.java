package me.remil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.remil.dto.CheckEmailExists;
import me.remil.dto.SrpClientChallenge;
import me.remil.dto.SrpServerChallenge;
import me.remil.dto.UserDTO;
import me.remil.service.user.UserService;

@RequestMapping("/api/v1")
@RestController
@CrossOrigin(origins = { "http://localhost:3000", "https://c-auth.azurewebsites.net" })
public class AuthenticationController {

	private UserService userService;
		
	@PostMapping("/register")
	public void register(@RequestBody UserDTO body) {
		userService.saveNewUser(body);
	}

	@GetMapping("/check-username")
	public CheckEmailExists checkUserExists(@RequestParam(value = "email", required = true) String email) {
		CheckEmailExists obj = new CheckEmailExists(userService.checkUserExists(email));

		return obj;
	}

	@GetMapping("/salt")
	public SrpServerChallenge loginChallenge(@RequestParam(value = "email", required = true) String email) {
		return userService.fetchUserSalt(email);
	}

	@PostMapping("/login")
	public void clientResponse(@RequestBody SrpClientChallenge challenge) {
		userService.verifyClientChallenge(challenge);
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}