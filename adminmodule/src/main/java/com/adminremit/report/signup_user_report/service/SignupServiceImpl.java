package com.adminremit.report.signup_user_report.service;

import java.util.List;

import com.adminremit.report.signup_user_report.repository.SignupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.user.model.Users;
@Service
public class SignupServiceImpl implements SignupService {
	@Autowired
	private SignupRepository signupRepository;

	@Override
	public List<Users> findAll()  {
		List<Users> usersList = null;
		usersList = signupRepository.findAll();
		return usersList;
	}

	@Override
	public List<Users> findAllBySort(String order) {
		List<Users> usersList = null;
		if(order.equals("desc")){
			usersList = signupRepository.findAllByOrderByCreateAtDesc();
		}else{

			usersList = signupRepository.findAllByOrderByCreateAtAsc();
		}
		return usersList;
	}

	@Override
	public Users findByEmail(String email) {
	        Users users = null;
	        try {
	            users = signupRepository.findByEmail(email);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return users;
	    }
	

}
