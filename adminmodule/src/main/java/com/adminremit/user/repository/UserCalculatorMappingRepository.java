package com.adminremit.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adminremit.operations.model.RemittUser;
import com.adminremit.operations.model.UserCalculatorMapping;

@Repository
public interface UserCalculatorMappingRepository extends JpaRepository<UserCalculatorMapping, Long> {

	public UserCalculatorMapping findByRefId(String refId);

	public UserCalculatorMapping findByUid(String uid);

	@Query(value = "SELECT * FROM user_calc_mapping WHERE user_id=:id", nativeQuery = true)
	public UserCalculatorMapping getUserCalculatorMapping(@Param("id") Long id);

	@Query(value = "SELECT * FROM user_calc_mapping WHERE user_id=:id and reference_id is not null", nativeQuery = true)
	public List<UserCalculatorMapping> getAllReferenceIdForUser(@Param("id") Long id);

	@Query("SELECT u FROM UserCalculatorMapping u WHERE u.user = :user AND beneficiaryId IS NOT NULL AND reference_id IS NOT NULL ORDER BY createdAt DESC")
    List<UserCalculatorMapping> findAllByUserAndStatusOrderByIdDesc(@Param(value="user") RemittUser user);
}
