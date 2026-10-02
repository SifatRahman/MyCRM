package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.EntityCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntityCustomerRepository extends JpaRepository<EntityCustomer,String> {
    Optional<EntityCustomer> findByFullName(String customerId);
}
