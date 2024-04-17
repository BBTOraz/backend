package com.bbt.lawyerclientservice.repository;

import com.bbt.lawyerclientservice.entity.HistoryOfTrials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LawyersTrialRepository extends JpaRepository<HistoryOfTrials, Long> {

}
