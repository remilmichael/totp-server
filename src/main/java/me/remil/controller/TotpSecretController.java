package me.remil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.remil.dto.TotpSecret;
import me.remil.service.totp.TotpSecretService;

@RequestMapping("/api/v1/secret")
@RestController
public class TotpSecretController {
	
	private TotpSecretService totpSecretService;

	@PostMapping("/add")
	public ResponseEntity<?> saveNewSecretKey(@RequestBody TotpSecret body) {
		totpSecretService.saveSecretKey(body);
		return ResponseEntity.ok().build();
	}
	
	@Autowired
	public void setTotpSecretService(TotpSecretService totpSecretService) {
		this.totpSecretService = totpSecretService;
	}
}