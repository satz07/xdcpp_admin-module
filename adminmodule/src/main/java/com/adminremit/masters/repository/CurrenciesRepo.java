package com.adminremit.masters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adminremit.masters.models.Currencies;

public interface CurrenciesRepo extends JpaRepository<Currencies,Long> {
	
	List<Currencies> findAllByCurrencyCode(String currencyCode);

    List<Currencies> findAllByCountryCode(String countryCode);

    List<Currencies> findByOrderByCountryDescriptionAsc();

}
