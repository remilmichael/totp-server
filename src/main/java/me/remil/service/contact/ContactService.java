package me.remil.service.contact;

import me.remil.dto.ContactFormDto;

public interface ContactService {
	public void saveMessage(ContactFormDto form);
}
