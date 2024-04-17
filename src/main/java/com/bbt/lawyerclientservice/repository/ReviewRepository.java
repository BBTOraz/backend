package com.bbt.lawyerclientservice.repository;

import com.bbt.lawyerclientservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository  extends JpaRepository<Review, UUID> {
    List<Review> findAllByClientContactEmail(String email);
    List<Review> findAllByLawyerContactEmail(String email);

    List<Review> findAllByLawyerId(Long id);
}
