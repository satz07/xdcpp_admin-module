package com.adminremit.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.auth.repository.RegistrationRepository;
import com.adminremit.personaldetails.model.PersonalDetails;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private RegistrationRepository repository;

	@Override
	public List<PersonalDetails> findAll() {
		List<PersonalDetails> personalList = null;
		personalList = repository.findAll();
		return personalList;
	}

	@Override
	public List<PersonalDetails> findAllBySort(String id) {
		List<PersonalDetails> personalList = null;
		if(id.equals("desc")){
			personalList = repository.findAllByOrderByCreateAtDesc();
		}else{

			personalList = repository.findAllByOrderByCreateAtAsc();
		}
		return personalList;
	}

	@Override
	public PersonalDetails findByUserId(Long userId) {
		PersonalDetails getPersonal = null;
		getPersonal = repository.findByUserId(userId);
		return getPersonal;
	}

}
