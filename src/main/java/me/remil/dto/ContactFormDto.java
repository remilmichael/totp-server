package me.remil.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactFormDto {
	private String name;
	private String email;
	private String subject;
	private String message;
}
