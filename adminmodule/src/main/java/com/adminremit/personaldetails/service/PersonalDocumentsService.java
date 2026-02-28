package com.adminremit.personaldetails.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.personaldetails.model.PersonalDocuments;

public interface PersonalDocumentsService {
	public PersonalDocuments fetchOne(PersonalDetails personalDetails) throws RecordNotFoundException;

}
