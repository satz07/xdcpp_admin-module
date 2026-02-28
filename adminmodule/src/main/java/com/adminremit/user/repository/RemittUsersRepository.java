package com.adminremit.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adminremit.operations.model.RemittUser;

@Repository
public interface RemittUsersRepository extends JpaRepository<RemittUser, Long> {

	public Optional<RemittUser> findById(Long Id);
	
	public RemittUser findByEmail(String email);
	
	@Query(value = "SELECT * FROM RemittUser WHERE email=:emailId", nativeQuery = true)
	public RemittUser getUser(@Param("emailId") String emailId);
	
}
