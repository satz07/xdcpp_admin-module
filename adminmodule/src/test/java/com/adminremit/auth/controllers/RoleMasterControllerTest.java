package com.adminremit.auth.controllers;

import com.adminremit.auth.models.RoleMaster;
import com.adminremit.auth.repository.RoleMasterRepository;
import com.adminremit.auth.service.RoleMasterService;
import com.adminremit.auth.service.RoleMasterServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintViolation;
import java.util.Optional;
import java.util.Set;
import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RoleMasterControllerTest {

    @Mock
    RoleMasterRepository roleMasterRepository;

    @InjectMocks
    private RoleMasterServiceImpl roleMasterService;

    private static Validator validator;

    @Test
    public void testcreateRole() {
        //WEB3 migration changes - Added proper mock setup for repository methods
        RoleMaster roleMaster = new RoleMaster();
        roleMaster.setRoleName("admin");
        roleMaster.setPublish(true);
        
        // Mock findByRoleName to return null (role doesn't exist yet)
        given(roleMasterRepository.findByRoleName(roleMaster.getRoleName())).willReturn(null);
        
        // Mock save to return the saved roleMaster
        given(roleMasterRepository.save(any(RoleMaster.class))).willAnswer(invocation -> invocation.getArgument(0));
        
        RoleMaster roleMaster2 = roleMasterService.findByRoleName(roleMaster.getRoleName());
        assertThat(roleMaster2).isNull();
        if(roleMaster2 == null) {
            RoleMaster roleMaster1 = roleMasterService.save(roleMaster);
            assertThat(roleMaster1).isNotNull();
            verify(roleMasterRepository).save(any(RoleMaster.class));
        }
        //RoleMaster roleMaster1 = roleMasterService.findByRoleName(roleMaster.getRoleName());
    }
    
    /*@Test
    public void testcreateRole() {
        RoleMaster roleMaster = new RoleMaster();
        roleMaster.setRoleName("admin");
        roleMaster.setPublish(true);
        RoleMaster roleMaster2 = roleMasterService.findByRoleName(roleMaster.getRoleName());
        assertThat(roleMaster2).isNull();
        if(roleMaster2 == null) {

            RoleMaster roleMaster1 = roleMasterService.save(roleMaster);
            assertThat(roleMaster1).isNotNull();
        }
        //RoleMaster roleMaster1 = roleMasterService.findByRoleName(roleMaster.getRoleName());
    }*/

    /*@Test
    public void testcreateRoleMaster() {
        RoleMaster roleMaster = new RoleMaster();
        roleMaster.setRoleName("admin");
        roleMaster.setPublish(true);
        Set<ConstraintViolation<RoleMaster>> violations = validator.validate(roleMaster);
        assertThat(violations.size()).isEqualTo(0);
        given(roleMasterRepository.findByRoleName(roleMaster.getRoleName())).willReturn(roleMaster);
        given(roleMasterRepository.save(roleMaster)).willAnswer(invocation -> invocation.getArgument(0));
        RoleMaster roleMaster1 = roleMasterService.save(roleMaster);
        assertThat(roleMaster1).isNotNull();
        verify(roleMasterRepository).save(any(RoleMaster.class));
    }*/
    
}
