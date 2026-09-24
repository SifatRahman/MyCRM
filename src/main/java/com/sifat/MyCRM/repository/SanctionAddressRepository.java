package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.SanctionAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionAddressRepository extends JpaRepository<SanctionAddress,String> {
}
