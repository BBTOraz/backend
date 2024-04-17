package com.bbt.lawyerclientservice.repository;

import com.bbt.lawyerclientservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
