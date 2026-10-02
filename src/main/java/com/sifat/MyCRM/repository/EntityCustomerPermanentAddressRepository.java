package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.EntityCustomerPermanentAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EntityCustomerPermanentAddressRepository extends JpaRepository<EntityCustomerPermanentAddress,String> {
    Optional<EntityCustomerPermanentAddress> findByEntityCustomer_Id(String entityCustomerId);
}
