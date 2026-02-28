package com.adminremit.personaldetails.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.personaldetails.model.PersonalDocuments;
import com.adminremit.personaldetails.repository.PersonalDocumentsRepository;

@Service
public class PersonalDocumentsServiceImpl implements PersonalDocumentsService{
	
	@Autowired
    private PersonalDocumentsRepository personalDocumentsRepository;

	@Override
    public PersonalDocuments fetchOne(PersonalDetails personalDetails) throws RecordNotFoundException {
        return personalDocumentsRepository.findTopByPersonalDetailsOrderByIdDesc(personalDetails);
    }

}
