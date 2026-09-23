package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.SanctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionEntityRepository extends JpaRepository<SanctionEntity,String> {
}
