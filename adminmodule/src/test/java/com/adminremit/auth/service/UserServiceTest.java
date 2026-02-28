package com.adminremit.auth.service;

import com.adminremit.auth.models.Partner;
import com.adminremit.auth.models.RoleMaster;
import com.adminremit.auth.models.User;
import com.adminremit.auth.repository.ProductRepository;
import com.adminremit.auth.repository.UserRepository;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.mockito.BDDMockito.given;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void duplicateSuperUser_Success(){
        User adminUser = new User();
        adminUser.setPartner(new Partner());
        adminUser.setPassword("test");
        adminUser.setFirstName("andremit");
        adminUser.setLastName("andremit");
        adminUser.setEmail("admins");
        given(userRepository.findByEmail(adminUser.getEmail())).willReturn(new User());
        User user = userRepository.findByEmail("admin");
        assertThat(user).isNull();

        /*Mockito.when(userRepository.findByEmail("admin")).thenReturn(adminUser);
        assertThrows(DuplicateName.class, () ->  {
            userService.save(adminUser);
        });*/
    }

    @Test
    public void bcryptEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encoded = bCryptPasswordEncoder.encode("superadmin");
        assertThat(encoded);
    }
}
