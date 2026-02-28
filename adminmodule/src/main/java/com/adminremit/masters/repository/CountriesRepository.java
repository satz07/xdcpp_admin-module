package com.adminremit.masters.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.Currencies;

@Repository
public interface CountriesRepository extends JpaRepository<Countries, Long> {
    public List<Countries> findAllByPublish(boolean publish);
    public Optional<Countries> findById(Long id);
    public List<Countries> findAll();
    @Query("Select a from Countries a where a.publish=true AND isDeleted = :isDeleted order by a.dialingCode ASC")
    public List<Countries> findAllByIsDeletedOrderByDialingCodeAsc(@Param("isDeleted") Boolean isDeleted);
    @Query("Select a from Countries a where isDeleted = :isDeleted order by a.countryCode ASC")
    public List<Countries> findAllByIsDeletedOrderByCountryCodeAsc(@Param("isDeleted") Boolean isDeleted);
    @Query("Select a from Countries a where isDeleted = :isDeleted order by a.countryName ASC")
    public List<Countries> findAllByIsDeletedOrderByCountryNameAsc(@Param("isDeleted") Boolean isDeleted);
    @Query("Select a from Countries a where isDeleted = :isDeleted")
    public List<Countries> findAllByIsDeleted(@Param("isDeleted")Boolean isDeleted);
    @Query("Select a from Countries a where a.publish=true AND isDeleted = :isDeleted AND a.countryCode = :countryCode")
    public List<Countries> findAllByCountryCodeAndIsDeleted(@Param("countryCode") String countryCode, @Param("isDeleted") Boolean isDeleted);
    @Query("Select a from Countries a where a.publish=true AND isDeleted = :isDeleted AND (a.countryCode = :countryCode OR lower(a.countryName) like lower(concat('%', :countryName,'%')))")
    public List<Countries> findAllByCountryCodeAndCountryNameAndIsDeleted(@Param("countryCode") String countryCode,@Param("countryName") String countryName, @Param("isDeleted") Boolean isDeleted);
    Countries findByCountryNameAndIsDeletedFalse(String countryName);
    public Countries findByCountryCode(String countryCode);
    //public List<Countries> getCountryCodeByCountryName(String countryName);
	//public List<Countries> getCountryCodeByCountryName(String countryName);
	public List<Currencies> findAllById(Long id);
	@Query("Select a from Countries a where a.publish=true AND isDeleted = :isDeleted AND a.countryCode = :countryCode")
	public Optional<Countries> findByCountryCodeAndIsDeleted(@Param("countryCode") String countryCode, @Param("isDeleted") Boolean isDeleted);
    @Query("Select a from Countries a where a.publish=true AND isDeleted = :isDeleted AND (a.countryCode = :countryCode OR lower(a.countryName) like lower(concat('%', :countryName,'%')))")
    public Optional<Countries> findByCountryCodeAndCountryNameAndIsDeleted(@Param("countryCode") String countryCode,@Param("countryName") String countryName, @Param("isDeleted") Boolean isDeleted);
	public Countries findByIsoCode(String isoCode);
    @Query("Select a from Countries a where a.publish=true AND isDeleted = :isDeleted AND (a.countryCode = :countryCode OR lower(a.countryName) like lower(concat('%', :countryName,'%'))) AND a.id != :id")
    public List<Countries> findByIdAndCountryCodeAndCountryNameAndIsDeleted(@Param("countryCode") String countryCode,@Param("countryName") String countryName,@Param("id") Long id, @Param("isDeleted") Boolean isDeleted);

}
	