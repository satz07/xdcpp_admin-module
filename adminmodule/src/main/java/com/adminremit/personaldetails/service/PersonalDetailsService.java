package com.adminremit.personaldetails.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.personaldetails.model.PersonalDetails;

public interface PersonalDetailsService {
	
	public PersonalDetails getLatestByUser(Long id);
	public PersonalDetails getById(Long id) throws RecordNotFoundException;

}
