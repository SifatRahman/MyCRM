package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.SanctionEntityLastDayUpdated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionEntityLastDayUpdatedRepository extends JpaRepository<SanctionEntityLastDayUpdated,String> {
}
