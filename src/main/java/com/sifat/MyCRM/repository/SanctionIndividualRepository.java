package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.SanctionIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionIndividualRepository extends JpaRepository<SanctionIndividual,String> {

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END " +
            "FROM SanctionIndividual s")
    boolean hasAnyData();


}
