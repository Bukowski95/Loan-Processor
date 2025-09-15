package com.berozgar.accounts.repository;

import com.berozgar.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //derived named method
    Optional<Customer> findByMobileNumber(String mobileNumber);
}
