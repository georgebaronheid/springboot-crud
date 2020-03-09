package com.baronheid.rest.customercrud.repository;

import com.baronheid.rest.customercrud.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
