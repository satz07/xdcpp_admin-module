package com.adminremit.user.service;

import java.util.List;

import com.adminremit.operations.model.RemittUser;
import com.adminremit.operations.model.UserCalculatorMapping;

public interface UserCalculatorMappingService {
	public UserCalculatorMapping findByRefId(String refId);
	
	public UserCalculatorMapping findByUid(String uId);
	
	public UserCalculatorMapping getUsersCalculator(Long Id);
	
	public List<UserCalculatorMapping> findAllByUser(RemittUser user);
	
	 public UserCalculatorMapping save(UserCalculatorMapping userCalculatorMapping);
	
}
