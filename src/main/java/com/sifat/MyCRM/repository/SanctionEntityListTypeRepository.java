package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.SanctionEntityListType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionEntityListTypeRepository extends JpaRepository<SanctionEntityListType,String> {
}
