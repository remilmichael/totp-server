package me.remil.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {

	private String email;
	
	private String salt;
	
	private String verifier;
	
	private String encKey;
}
