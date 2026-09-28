package com.sifat.MyCRM.repository;


import com.sifat.MyCRM.entity.IndividualCustomerPermanentAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndividualCustomerPermanentAddressRepository extends JpaRepository<IndividualCustomerPermanentAddress,String> {

    Optional<IndividualCustomerPermanentAddress> findByIndividualCustomer_Id(String s);
}
