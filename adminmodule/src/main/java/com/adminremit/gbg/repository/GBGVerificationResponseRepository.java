package com.adminremit.gbg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.personaldetails.enums.GBGApiNames;
import com.adminremit.personaldetails.model.GBGVerificationRegisterVerificationResponse;
import com.adminremit.personaldetails.model.PersonalDetails;

@Repository
public interface GBGVerificationResponseRepository extends JpaRepository<GBGVerificationRegisterVerificationResponse,Long> {
    public List<GBGVerificationRegisterVerificationResponse> findByPersonalDetailsAndGbgApiNames(PersonalDetails personalDetails, GBGApiNames gbgApiNames);
}
