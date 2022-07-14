package me.remil.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.remil.dto.TotpSecret;

@RequestMapping("/api/v1/secret")
@RestController
public class TotpSecretController {
	
	@PostMapping("/add")
	public ResponseEntity<?> saveNewSecretKey(@RequestBody TotpSecret body) {
		return ResponseEntity.ok().build();
	}
}
