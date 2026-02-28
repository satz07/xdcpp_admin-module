package com.adminremit.masters.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adminremit.masters.models.Currencies;

public interface CurrenciesRepository extends JpaRepository<Currencies, Long> {
	public List<Currencies> findAllByPublish(boolean publish);

	public Optional<Currencies> findById(Long id);

	public List<Currencies> findAll();

	@Query("Select a from Currencies a where a.publish=true order by a.currencyName ASC")
	public List<Currencies> findAllByIsDeletedOrderByCurrencyNameAsc(Boolean isDeleted);

	@Query("Select a from Currencies a where isDeleted = :isDeleted order by a.currencyCode ASC")
	public List<Currencies> findAllByIsDeletedOrderByCurrencyCodeAsc(@Param("isDeleted") Boolean isDeleted);

	@Query("Select a from Currencies a where isDeleted = :isDeleted ")
	public List<Currencies> findAllByIsDeleted(@Param("isDeleted") Boolean isDeleted);

	@Query("Select a from Currencies a where a.currencyCode= :currencyCode AND a.countryCode=:countryCode AND a.publish=true AND isDeleted = :isDeleted")
	public List<Currencies> findAllByCurrencyCodeAndIsDeleted(@Param("currencyCode") String currencyCode,@Param("countryCode") String countryCode, @Param("isDeleted") Boolean isDeleted);

	@Query("Select a from Currencies a where a.id = :id AND a.currencyCode= :currencyCode AND a.countryCode=:countryCode AND a.publish=true AND isDeleted = :isDeleted")
	public List<Currencies> findAllByIdAndCurrencyCodeAndIsDeleted(@Param("id") Long id,@Param("currencyCode") String currencyCode,@Param("countryCode") String countryCode, @Param("isDeleted") Boolean isDeleted);

	@Query("Select a from Currencies a where a.id= :currencyId AND a.publish=true AND isDeleted = :isDeleted")
	public List<Currencies> findAllByIdAndIsDeleted(@Param("currencyId") Long currencyId, @Param("isDeleted") Boolean isDeleted);

	public Optional<Currencies> findByCurrencyCode(String currencyCode);

	public List<Currencies> findByCountryName(Long id);

	//public List<Currencies> findAllById(Long id);

	public List<Currencies> findByCountryCode(String countryCode);

	public List<Currencies> findByCountryCodeAndIsDeleted(String countryCode,Boolean isDeleted);
	
	
	@Query("Select a from Currencies a where a.currencyCode= :currencyCode AND a.countryCode= :countryCode AND a.publish=true AND isDeleted = :isDeleted")
	public Optional<Currencies> findByCurrencyCodeAndCountryCodeIsDeleted(@Param("currencyCode") String currencyCode,@Param("countryCode") String countryCode, @Param("isDeleted") Boolean isDeleted);

	public Currencies findByCurrencyCodeAndCountryCode(String currencyCode, String countryCode);

	

}
