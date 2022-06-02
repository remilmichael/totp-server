package me.remil.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import me.remil.dto.UserDTO;

@RestController("/authentication")
@CrossOrigin(origins = {"http://localhost:3000"})
public class AuthenticationController {
	
	
	
	@PostMapping("/register")
	public void register(@RequestBody UserDTO body) {
		
	}
}
