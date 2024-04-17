package com.bbt.lawyerclientservice.repository;

import com.bbt.lawyerclientservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryForLogIn extends JpaRepository<User, Long> {

    @Query("SELECT user FROM User user WHERE user.contact.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}
