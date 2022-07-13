package me.remil.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutologinSessionCreate {
	
	private String email;
	private String encKey;
	private Timestamp expiry;
	private String uuid;
}
