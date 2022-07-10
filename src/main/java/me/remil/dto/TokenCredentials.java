package me.remil.dto;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.remil.entity.Role;

@Getter
@Setter
@AllArgsConstructor
public class TokenCredentials {
	
	private String[] roles;
	private String username;	
}
