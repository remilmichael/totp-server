package me.remil.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "secrets")
@Getter
@Setter
@NoArgsConstructor
public class TotpSecretKeys {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "keyid", unique = true, nullable = false)
	private String keyId;
	
	@Column(name = "secret")
	private String secret;
	
	@Column(name = "email")
	private String email;
}
