package me.remil.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.bitbucket.thinbus.srp6.js.SRP6JavascriptServerSession;
import com.bitbucket.thinbus.srp6.js.SRP6JavascriptServerSessionSHA256;

@Component
public class SrpSessionProvider {

	@Value("${thinbus.N}")
	public String N;

	@Value("${thinbus.g}")
	public String g;

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