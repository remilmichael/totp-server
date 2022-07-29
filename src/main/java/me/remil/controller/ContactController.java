package me.remil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.remil.dto.ContactFormDto;
import me.remil.service.contact.ContactService;

@RequestMapping("/api/v1/ticket")
@RestController
public class ContactController {
	
	private ContactService contactService;

	@PostMapping("/add")
	public ResponseEntity<?> addnewUserTicket(@RequestBody ContactFormDto body) {
		contactService.saveMessage(body);
		return ResponseEntity.ok().build();
	}
	
	@Autowired
	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}
}
