package me.remil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.remil.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>{

}
