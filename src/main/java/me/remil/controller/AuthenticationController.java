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
import me.remil.dto.UserDTO;
import me.remil.service.UserService;

@RequestMapping("/api/authentication")
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://c-auth.azurewebsites.net"})
public class AuthenticationController {
	
	private UserService userService;

	@PostMapping("/register")
	public void register(@RequestBody UserDTO body) {
		userService.saveNewUser(body);
	}
	
	@GetMapping("/check-username")
	public CheckEmailExists checkUserExists(@RequestParam String email) {
		System.out.println(email);
		CheckEmailExists obj = 
				new CheckEmailExists(
						userService.checkUserExists(email)
						);
		
		return obj;
	}
	
	

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
