package com.adminremit.report.signup_user_report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.user.model.Users;

@Repository
public interface SignupRepository extends JpaRepository<Users,Long> {
	//@Query("SELECT u FROM Users u WHERE u.createAt = ?1 and u.createAt = ?2")
	//Rpublic List<Users> findAllCreateAt(Users from, Users to);
	public List<Users> findAll();

	public List<Users> findAllByOrderByCreateAtAsc();

	public List<Users> findAllByOrderByCreateAtDesc();

	public Users findByEmail(String email);
}
