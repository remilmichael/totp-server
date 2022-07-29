package me.remil.service.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.remil.dto.ContactFormDto;
import me.remil.entity.Contact;
import me.remil.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {

	private ContactRepository contactRepository;
	
	@Override
	public void saveMessage(ContactFormDto form) {
		Contact newContactForm = new Contact();
		newContactForm.setEmail(form.getEmail());
		newContactForm.setMessage(form.getMessage());
		newContactForm.setName(form.getName());
		newContactForm.setSubject(form.getSubject());
		contactRepository.save(newContactForm);
	}

	@Autowired
	public void setContactRepository(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}
}
