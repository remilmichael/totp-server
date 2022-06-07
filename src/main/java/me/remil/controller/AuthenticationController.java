package me.remil.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.remil.dto.UserDTO;
import me.remil.service.UserService;

@RestController("/authentication")
@CrossOrigin(origins = {"http://localhost:3000"})
public class AuthenticationController {
	
	private UserService userService;

	@PostMapping("/register")
	public void register(@RequestBody UserDTO body) {
		
	}
	
	@GetMapping("/check-username")
	public boolean checkUserExists(@RequestParam String email) {
		return userService.checkUserExists(email);
	}
	

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
