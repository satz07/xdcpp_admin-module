package com.adminremit.masters.repository;


import com.adminremit.masters.models.CountryHolidays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryHolidaysRepository extends JpaRepository<CountryHolidays,Long> {

    Optional<CountryHolidays> findByHolidayId(@Param("id") Long id);
}
