package me.remil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.remil.dto.AutologinSessionCreate;
import me.remil.dto.AutologinSessionRetrieve;
import me.remil.service.autologin.AutologinService;

@RequestMapping("/api/v1")
@RestController
public class AutoLoginController {
	
	private AutologinService autologinService;

	@PostMapping("/autologin/create")
	public ResponseEntity<?> register(@RequestBody AutologinSessionCreate body) {
		autologinService.saveAutoLogin(body);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/autologin/get")
	public ResponseEntity<AutologinSessionRetrieve> getSession(@RequestParam(value = "id", required = true) String id) {
		return ResponseEntity.ok().body(autologinService.retrieveSessionKey(id));
	}
	
	@Autowired
	public void setAutologinService(AutologinService autologinService) {
		this.autologinService = autologinService;
	}
}
