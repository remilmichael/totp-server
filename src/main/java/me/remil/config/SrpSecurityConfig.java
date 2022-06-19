package me.remil.config;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.context.annotation.Configuration;

import com.bitbucket.thinbus.srp6.js.HexHashedRoutines;

@Configuration
public class SrpSecurityConfig {

	static MessageDigest sha256() {
		try {
			return MessageDigest.getInstance("SHA-256");
		} catch(NoSuchAlgorithmException e) {
			throw new AssertionError("not possible in jdk1.7 and 1.8: "+e);
		}
	}

	MessageDigest md = sha256();

	public String hash(String value) {
		try {
			md.update(value.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError("not possible in jdk1.7 and 1.8: "+e);
		}
		return HexHashedRoutines.toHexString(md.digest());
	}

}