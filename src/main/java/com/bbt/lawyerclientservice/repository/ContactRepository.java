package com.bbt.lawyerclientservice.repository;

import com.bbt.lawyerclientservice.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Boolean findFirstByEmail(String email);
    Boolean existsByEmail(String email);
}
