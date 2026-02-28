package com.adminremit.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adminremit.auth.models.User;
import com.adminremit.user.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

	public Optional<Users> findById(Long Id);
	
	public Users findByEmail(String email);
	
	@Query(value = "SELECT * FROM users WHERE email=:emailId", nativeQuery = true)
	public Users getUser(@Param("emailId") String emailId);
	
}
