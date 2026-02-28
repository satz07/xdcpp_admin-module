package com.adminremit.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.operations.model.RemittUser;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.user.repository.UserCalculatorMappingRepository;

@Service
public class UserCalculatorMappingServiceImpl implements UserCalculatorMappingService {
	
	@Autowired
	private UserCalculatorMappingRepository userCalculatorMappingRepository;

	@Override
	public UserCalculatorMapping findByRefId(String refId) {
		return userCalculatorMappingRepository.findByRefId(refId);
	}

	@Override
    public UserCalculatorMapping findByUid(String uId) {
        return userCalculatorMappingRepository.findByUid(uId);
    }

	@Override
	public UserCalculatorMapping getUsersCalculator(Long id) {
		UserCalculatorMapping userCalculatorMapping=null;
		userCalculatorMapping=userCalculatorMappingRepository.getUserCalculatorMapping(id);
		return userCalculatorMapping;
	}
	
	@Override
    public List<UserCalculatorMapping> findAllByUser(RemittUser user) {
        List<UserCalculatorMapping> userCalculatorMappings = null;
        try {
            userCalculatorMappings = userCalculatorMappingRepository.findAllByUserAndStatusOrderByIdDesc(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userCalculatorMappings;
    }

	@Override
	public UserCalculatorMapping save(UserCalculatorMapping userCalculatorMapping)
			 {
		return userCalculatorMappingRepository.save(userCalculatorMapping);
	}
}
