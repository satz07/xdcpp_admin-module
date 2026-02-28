package com.adminremit.auth.service;

import com.adminremit.auth.models.Partner;
import com.adminremit.auth.models.Product;
import com.adminremit.auth.repository.PartnerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;

import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
public class PartnerServiceTest {

    @Mock
    private PartnerRepository partnerRepository;

    @InjectMocks
    private PartnerServiceImpl partnerService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createPartner_Success() {
        Partner partner = new Partner();
        partner.setProduct(new Product());
        partner.setPartnerName("Andremit");
        partner.setAddress("Andremit, Singapore");
        assertThat(partner.getProduct()).isNotNull();
        given(partnerRepository.save(partner)).willReturn(new Partner());
        Partner partner1 = partnerRepository.save(partner);
        assertThat(partner1).isNotNull();
    }

    @Test
    public void createPartner_Fail() {
        Partner partner = new Partner();
        partner.setPartnerName("Andremit");
        partner.setAddress("Andremit, Singapore");
        assertThat(partner.getProduct()).isNull();
        given(partnerRepository.save(partner)).willReturn(new Partner());
        Partner partner1 = partnerRepository.save(partner);
        assertThat(partner1).isNotNull();
    }

}
