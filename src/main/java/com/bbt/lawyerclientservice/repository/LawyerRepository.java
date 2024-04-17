package com.bbt.lawyerclientservice.repository;

import com.bbt.lawyerclientservice.entity.Lawyer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, Long>, JpaSpecificationExecutor<Lawyer> {
    Optional<Lawyer> findById(Long id);
    @Query("SELECT l FROM Lawyer l WHERE l.contact.email = :email")
    Optional<Lawyer> findLawyerByEmail(@Param("email") String email);

    List<Lawyer> findAll(Specification<Lawyer> specification);
    Page<Lawyer> findAll(Pageable pageable);

}
