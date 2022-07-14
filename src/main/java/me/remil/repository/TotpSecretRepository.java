package me.remil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.remil.entity.TotpSecretKeys;

public interface TotpSecretRepository extends JpaRepository<TotpSecretKeys, Long>{
	
}
