package com.adminremit.auth.service;

import com.adminremit.auth.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;

public interface AuthService  extends UserDetailsService {
    public User getUser();
}
