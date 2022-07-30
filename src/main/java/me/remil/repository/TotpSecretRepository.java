package me.remil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import me.remil.entity.TotpSecretKeys;

public interface TotpSecretRepository extends JpaRepository<TotpSecretKeys, Long>{

	// add pagination
    @Query("select keyId, secret, account, username from TotpSecretKeys where email=:email")
	List<Object[]> fetchAllSecretsByEmail(@Param("email") String email);
	
	TotpSecretKeys findByKeyId(String keyId);
}
