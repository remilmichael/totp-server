package me.remil.config;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.bitbucket.thinbus.srp6.js.HexHashedRoutines;
import com.bitbucket.thinbus.srp6.js.SRP6JavascriptServerSession;
import com.bitbucket.thinbus.srp6.js.SRP6JavascriptServerSessionSHA256;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import me.remil.component.SrpAuthenticationProvider;
import me.remil.entity.User;

@Configuration
public class SrpSecurityConfig  {

	private SrpAuthenticationProvider srpAuthenticationProvider;
	
	
	@Value("${thinbus.N}")
    public String N;
	
	@Value("${thinbus.g}")
	public String g;
	
	@Value("${thinbus.salt.of.fake.salt}")
    public String saltOfFakeSalt;
	
	@Bean
	LoadingCache<User, SRP6JavascriptServerSession> sessionCache() {
		return CacheBuilder
				.newBuilder()
				.expireAfterAccess(120, TimeUnit.SECONDS)
				.build(new CacheLoader<User, SRP6JavascriptServerSession>(){

					@Override
					public SRP6JavascriptServerSession load(User user) throws Exception {
						SRP6JavascriptServerSession session = new SRP6JavascriptServerSessionSHA256(
								N, g);
						session.step1(user.getEmail(), user.getSalt(), user.getVerifier());
						return session;
					}
					
				});
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
        .authorizeRequests()
			.antMatchers("/", "/favicon.ico", "/resources/**", "/api/authentication/*")
			.permitAll()
            .anyRequest().authenticated()
            .and()
        .logout()
            .logoutUrl("/api/logout")
            .permitAll();
		
		return http.build();
    }
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
		return builder.authenticationProvider(srpAuthenticationProvider).build();
	}

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

	
	@Autowired
	public void setSrpAuthenticationProvider(SrpAuthenticationProvider srpAuthenticationProvider) {
		this.srpAuthenticationProvider = srpAuthenticationProvider;
	}
}
