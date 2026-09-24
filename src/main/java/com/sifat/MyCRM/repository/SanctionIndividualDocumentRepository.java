package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.SanctionIndividualDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionIndividualDocumentRepository extends JpaRepository<SanctionIndividualDocument,String> {
}
