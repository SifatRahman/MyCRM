package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,String> {
}
