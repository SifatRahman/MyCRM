package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.SanctionIndividualDateOfBirth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionIndividualDateOfBirthRepository extends JpaRepository<SanctionIndividualDateOfBirth,String> {
}
