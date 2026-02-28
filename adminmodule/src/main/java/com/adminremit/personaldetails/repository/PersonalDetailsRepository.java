package com.adminremit.personaldetails.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adminremit.personaldetails.model.PersonalDetails;

public interface PersonalDetailsRepository extends JpaRepository<PersonalDetails, Long> {
	public PersonalDetails findTopByUserIdOrderByIdDesc(Long id);
	
	@Query(value = "SELECT * FROM personal_details WHERE full_name=:fullName and middle_name=:middleName and last_name=:lastName and dob=:dob and id not in(:id)", nativeQuery = true)
	public List<PersonalDetails> findByfullNameAndMiddleNameAndLastNameAndDob(String fullName, String middleName, String lastName, Date dob, long id);

}
