package com.adminremit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adminremit.user.model.DocumentVerification;

@Repository
public interface UserDocVerificationRepo extends JpaRepository<DocumentVerification, Long> {
	
	@Query("SELECT s FROM DocumentVerification s WHERE s.userId = :id")
    DocumentVerification findByUserId(@Param(value="id") String userId);
	
    @Query("SELECT s FROM DocumentVerification s WHERE s.userId = :id")
    DocumentVerification findTopByUserIdOrderByIdDesc(@Param(value="id") String userId);
}
