package com.adminremit.auth.service;

import com.adminremit.auth.models.RoleMaster;
import com.adminremit.auth.repository.RoleMasterRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import javax.validation.Validator;

@ExtendWith(MockitoExtension.class)
public class RoleMasterServiceImplTest{

    @Mock
    private RoleMasterRepository roleMasterRepository;

    @InjectMocks
    private RoleMasterServiceImpl roleMasterService;

    private static Validator validator;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateRoles() {
        RoleMaster roleMaster = new RoleMaster();
        roleMaster.setRoleName("admin");
        roleMaster.setPublish(true);
        given(roleMasterRepository.save(roleMaster)).willAnswer(invocation -> invocation.getArgument(0));
        RoleMaster roleMaster1 = roleMasterService.save(roleMaster);
        assertThat(roleMaster1).isNotNull();
    }

    @Test
    public void testGetDupliateRole() {
        given(roleMasterRepository.findByRoleName("sathish")).willReturn(new RoleMaster());
        RoleMaster roleMaster1 = roleMasterService.findByRoleName("admin");
        assertThat(roleMaster1).isNull();
    }

}
