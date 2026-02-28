package com.adminremit.masters.repository;

import com.adminremit.masters.models.Holiday;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidaysRepository extends JpaRepository<Holiday,Long> {

    @Query("select ahc \n" +
            "from Holiday ahc\n" +
            "join Countries ac on ahc.country = ac.id\n" +
            "where ac.countryName = :sendingCountry or ac.countryName =:receivingCountry")
    List<Holiday> getHolidaysByCountryNames(@Param("sendingCountry") String sendingCountry,@Param("receivingCountry") String receivingCountry);
    
    @Query("select ahc \n" +
            "from Holiday ahc\n" +
            "join Countries ac on ahc.country = ac.id\n" +
            "where ac.countryName = :sendingCountry")
    List<Holiday> getHolidaysBySendingCountry(@Param("sendingCountry") String sendingCountry);
    
    @Query(value = "select distinct h.date as date from Holiday h where h.country.id= :sendingCountryCode and h.date>= :currentDate and h.publish = true")
	public List<String> getLimitedHolidaysBySendingCountry(@Param("sendingCountryCode") long sendingCountryCode, Pageable pageable, @Param("currentDate")String currentDate);

}
