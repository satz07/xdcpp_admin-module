package com.adminremit.report.signup_user_report.service;

import java.util.List;

import com.adminremit.user.model.Users;

public interface SignupService {
	 public List<Users> findAll();
	 public List<Users> findAllBySort(String order);
	 public Users findByEmail(String username);
}
