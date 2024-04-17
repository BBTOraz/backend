package com.bbt.lawyerclientservice.repository;

import com.bbt.lawyerclientservice.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findAllByLawyerContactEmail(String email);
    List<Reservation> findAllByClientContactEmail(String email);
}
