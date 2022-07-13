package me.remil.entity;


import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "autologin")
@Data
@NoArgsConstructor
public class Autologin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String email;
	
	@Column(name = "enckey")
	private String encKey;
	
	@Column
	private Timestamp expiry;
	
	@Column(unique = true)
	private String uuid;
}
