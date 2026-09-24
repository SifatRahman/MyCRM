package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.SanctionIndividualTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionIndividualTitleRepository extends JpaRepository<SanctionIndividualTitle,String> {
}
