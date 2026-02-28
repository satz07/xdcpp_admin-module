package com.adminremit.personaldetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.personaldetails.model.PersonalDocuments;

public interface PersonalDocumentsRepository extends JpaRepository<PersonalDocuments, Long> {
	public PersonalDocuments findTopByPersonalDetailsOrderByIdDesc(PersonalDetails personalDetails);
}
