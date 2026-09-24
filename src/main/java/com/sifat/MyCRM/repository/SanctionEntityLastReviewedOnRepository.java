package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.SanctionEntityLastReviewedOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionEntityLastReviewedOnRepository extends JpaRepository<SanctionEntityLastReviewedOn,String> {
}
