package me.remil.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SrpClientChallenge {
	private String email;
	private String a;
	private String m1;	
}
