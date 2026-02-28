package com.adminremit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.user.model.Users;
import com.adminremit.user.repository.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService {
	
	 @Autowired
	 private UsersRepository usersRepository;

	@Override
    public Users getUserById(Long userId) {
        Users user = null;
        try {
            user = usersRepository.findById(userId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

	@Override
	public Users findByEmail(String email) {
		 Users user = null;
		 user = usersRepository.findByEmail(email);
		return user;
	}

	@Override
	public Users getUserByEmail(String email) {
		Users user = null;
		 user = usersRepository.getUser(email);
		return user;
	}

}
