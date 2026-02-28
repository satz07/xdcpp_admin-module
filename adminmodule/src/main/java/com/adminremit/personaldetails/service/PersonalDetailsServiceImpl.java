package com.adminremit.personaldetails.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.personaldetails.repository.PersonalDetailsRepository;

@Service
public class PersonalDetailsServiceImpl implements PersonalDetailsService {
	
	@Autowired
    private PersonalDetailsRepository personalDetailsRepository;

	@Override
    public PersonalDetails getLatestByUser(Long id) {
        PersonalDetails personalDetails = null;
        try {
            personalDetails = personalDetailsRepository.findTopByUserIdOrderByIdDesc(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return personalDetails;
    }
	
	@Override
    public PersonalDetails getById(Long id) throws RecordNotFoundException {
        PersonalDetails personalDetails = null;
        try {
            personalDetails = personalDetailsRepository.findById(id).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return personalDetails;
    }
}
