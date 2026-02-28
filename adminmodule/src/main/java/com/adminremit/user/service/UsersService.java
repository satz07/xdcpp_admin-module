package com.adminremit.user.service;

import com.adminremit.user.model.Users;

public interface UsersService {
	
	public Users getUserById(Long userId);

	public Users findByEmail(String email);
	
	public Users getUserByEmail(String email);
}
