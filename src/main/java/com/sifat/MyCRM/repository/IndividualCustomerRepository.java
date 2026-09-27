package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.IndividualCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndividualCustomerRepository extends JpaRepository<IndividualCustomer,String> {

    Optional<IndividualCustomer> findByNid_no(String s);
    Optional<IndividualCustomer> findByPassport_no(String s);
}
