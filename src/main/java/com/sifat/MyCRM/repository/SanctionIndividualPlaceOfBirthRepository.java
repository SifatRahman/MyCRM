package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.SanctionIndividualPlaceOfBirth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionIndividualPlaceOfBirthRepository extends JpaRepository<SanctionIndividualPlaceOfBirth,String> {
}
