package me.remil.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSalt {
	
	private String email;
	
	private String salt;
}
