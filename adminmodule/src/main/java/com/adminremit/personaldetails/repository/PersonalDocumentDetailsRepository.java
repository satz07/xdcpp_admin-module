package com.adminremit.personaldetails.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.personaldetails.model.PersonalDocumentDetails;

@Repository
public interface PersonalDocumentDetailsRepository extends JpaRepository<PersonalDocumentDetails, Long> {
    public Optional<PersonalDocumentDetails> findById(Long id);
}
