package com.adminremit.masters.repository;

import com.adminremit.masters.models.CountryCurrencyMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryCurrencyMappingRepository extends JpaRepository<CountryCurrencyMapping,Long> {
}
