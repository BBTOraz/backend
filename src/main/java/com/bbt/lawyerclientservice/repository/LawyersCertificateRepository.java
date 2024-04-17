package com.bbt.lawyerclientservice.repository;

import com.bbt.lawyerclientservice.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LawyersCertificateRepository extends JpaRepository<Certificate, Long> {

}
