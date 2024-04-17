package com.bbt.lawyerclientservice.repository;

import com.bbt.lawyerclientservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAll();
    @Query("SELECT c FROM client c WHERE c.contact.email = :email")
    Optional<Client> findClientByEmail(@Param("email") String email);

}
