package com.adminremit.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.personaldetails.model.PersonalDetails;

@Repository
public interface RegistrationRepository extends JpaRepository<PersonalDetails, Long>  {
	
	public PersonalDetails findByUserId(Long userId);
	
	public List<PersonalDetails> findAll();

	public List<PersonalDetails> findAllByOrderByCreateAtAsc();

	public List<PersonalDetails> findAllByOrderByCreateAtDesc();
}
