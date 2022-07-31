package me.remil.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.remil.dto.CheckEmailExists;
import me.remil.dto.SrpServerChallenge;
import me.remil.dto.UserDTO;
import me.remil.dto.UserEncryptionKey;
import me.remil.service.user.UserService;

@RequestMapping("/api/v1")
@RestController
public class UserController {

	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserDTO body) {
		userService.saveNewUser(body);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/check-username")
	public ResponseEntity<CheckEmailExists> checkUserExists(@RequestParam(value = "email", required = true) String email) {
		CheckEmailExists obj = new CheckEmailExists(userService.checkUserExists(email));
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping("/salt")
	public ResponseEntity<SrpServerChallenge> loginChallenge(@RequestParam(value = "email", required = true) String email) {
		return ResponseEntity.ok().body(userService.fetchUserSalt(email));
	}
	
	@GetMapping("/enckey")
	public ResponseEntity<?> getEncryptionKey(@RequestParam(value = "email", required = true) String email) {
		String encKey = userService.getEncryptionKey(email);
		if (encKey == null) {
			return ResponseEntity.badRequest().build();
		} 
		return ResponseEntity.ok().body(new UserEncryptionKey(encKey));
	}
	
	@GetMapping("/logout")
	public ResponseEntity<?> clearCredentials(HttpServletRequest request) {
		
		String domainName = request.getServerName().equals("localhost") ? "localhost" : ".cauth.remil.me";
		
		ResponseCookie jwtCookie = ResponseCookie.from("token", "")
				.maxAge(0)
				.httpOnly(true)
				.secure(true)
				.domain(domainName)
				.path("/")
				.sameSite("None")
				.build();
		
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).build();
	}
	
	@PostMapping("/change-password")
	public ResponseEntity<?> updateCredentials(@RequestBody UserDTO body) {
		userService.updateCredentials(body);
		return ResponseEntity.ok().build();
	}
	

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}