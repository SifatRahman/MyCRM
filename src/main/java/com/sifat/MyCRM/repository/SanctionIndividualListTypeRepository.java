package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.SanctionIndividualListType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionIndividualListTypeRepository extends JpaRepository<SanctionIndividualListType,String> {
}
