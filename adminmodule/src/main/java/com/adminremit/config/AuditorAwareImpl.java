package com.adminremit.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Optional<String> currentUser = Optional.of("");
        try {        	
        	if(SecurityContextHolder.getContext()!=null && SecurityContextHolder.getContext().getAuthentication()!=null) {
        		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                UserDetails userDetails = (UserDetails) principal;
                currentUser = Optional.of(userDetails.getUsername());
        	}
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentUser;
    }

}