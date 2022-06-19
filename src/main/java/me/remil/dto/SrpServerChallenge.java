package me.remil.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SrpServerChallenge {
	
	private final String B;
	private final String salt;
}
