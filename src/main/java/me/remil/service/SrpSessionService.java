package me.remil.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.bitbucket.thinbus.srp6.js.SRP6JavascriptServerSession;
import com.bitbucket.thinbus.srp6.js.SRP6JavascriptServerSessionSHA256;

@Service
public class SrpSessionService {

	@Value("${thinbus.N}")
	public String N;

	@Value("${thinbus.g}")
	public String g;

	@Value("${thinbus.salt.of.fake.salt}")
	public String saltOfFakeSalt;

	@Cacheable(value = "srp-session", key = "#email")
	public SRP6JavascriptServerSession getSession(String email) {
		return new SRP6JavascriptServerSessionSHA256(N, g);
	}

	@CacheEvict(value = "srp-session", key = "#email")
	public void destroySession(String email) {
		
	}

	@CachePut(value = "srp-session", key = "#email")
	public SRP6JavascriptServerSession update(SRP6JavascriptServerSession session, String email) {
		return session;
	}
}