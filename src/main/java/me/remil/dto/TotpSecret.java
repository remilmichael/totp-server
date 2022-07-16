package me.remil.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotpSecret {
	
	private String uuid;
	private String username;
	private String account;
	private String secretKey;
}
