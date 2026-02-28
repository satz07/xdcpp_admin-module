package com.adminremit.auth.service;

import com.adminremit.auth.models.*;
import com.adminremit.auth.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserService userService;

    @Value("${admin.andremit.username}")
    private String superAdmin;

    @Autowired
    private FunctionMasterService functionMasterService;

    @Autowired
    private FeaturesMasterService featuresMasterService;

    public User getUser() {
        User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            user = userService.findByEmail(username);
        } else {
            String username = principal.toString();
            user = userService.findByEmail(username);
        }
        if(user == null) {
            return null;
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = authRepository.findByEmail(email);
        if (user != null && user.getIsPublish()) {
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.getUserRoleMappings(),user));
            return userDetails;

        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities( List<UserRoleMapping> userRoleMappings, User user) {
        Collection< ? extends GrantedAuthority> grantedAuthorities = null;
        if(user.getEmail().equals(superAdmin)) {
            grantedAuthorities = userRoleMappings.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getRoleName()))
                    .collect(Collectors.toList());
        } else {
            List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
            for(UserRoleMapping userRoleMapping: userRoleMappings) {
                RoleMaster roleMaster = userRoleMapping.getRole();
                for(RoleFeatureMapping roleFeatureMapping: roleMaster.getRoleFeatureMappings()) {
                    list.add(new SimpleGrantedAuthority("ROLE_"+roleFeatureMapping.getFeaturesMaster().getFeatureType().toString()));
                }
            }
            grantedAuthorities = list;
        }
        return grantedAuthorities;
    }
}
