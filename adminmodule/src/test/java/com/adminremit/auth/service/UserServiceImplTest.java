package com.adminremit.auth.service;

import com.adminremit.auth.models.Partner;
import com.adminremit.auth.models.User;
import com.adminremit.auth.repository.UserRepository;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class UserServiceImplTest {

    @Test
    void save() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void isUsernameAlreadyInUse() {
    }
}