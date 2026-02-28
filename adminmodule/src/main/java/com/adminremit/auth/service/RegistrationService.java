package com.adminremit.auth.service;

import java.util.List;


import com.adminremit.personaldetails.model.PersonalDetails;

public interface RegistrationService {
	

	public List<PersonalDetails> findAll();

	public List<PersonalDetails> findAllBySort(String id);
	
	public PersonalDetails findByUserId(Long userId);
}
