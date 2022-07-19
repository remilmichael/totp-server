package me.remil.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenCredentials {
	
	private String[] roles;
	private String username;	
}
