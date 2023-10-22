package com.customeer.customeer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.customeer.customeer.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

