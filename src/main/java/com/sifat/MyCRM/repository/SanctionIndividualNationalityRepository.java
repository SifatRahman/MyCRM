package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.SanctionIndividualNationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionIndividualNationalityRepository extends JpaRepository<SanctionIndividualNationality,String> {
}
