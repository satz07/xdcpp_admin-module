package com.adminremit.auth.service;

import com.adminremit.auth.models.Product;
import com.adminremit.auth.models.RoleMaster;
import com.adminremit.auth.repository.ProductRepository;
import com.adminremit.auth.repository.RoleMasterRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private static Validator validator;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product();
        product.setProductName("Andremit");
        given(productRepository.save(product)).willReturn(new Product());
        Mockito.when(productRepository.save(product)).thenReturn(new Product());
        Product product1 = productService.save(product);
        assertThat(product1).isNotNull();
        verify(productRepository, times(1)).save(Mockito.any(Product.class));
    }

}
