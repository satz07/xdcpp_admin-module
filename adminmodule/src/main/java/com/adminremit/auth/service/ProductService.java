package com.adminremit.auth.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.Product;
import com.adminremit.common.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService extends BaseService<Product> {
    public List<Product> listOfProducts();
    public Product getProductById(Long id) throws RecordNotFoundException;
    public void deleteProduct(Long id) throws RecordNotFoundException;
    public Page<Product> getAllProducts(Pageable pageable);
    public Integer getProductId();
    public List<Product> listOfAllProducts();
    public List<Product> listOfAllProductsByDesignation(Long designationId) throws RecordNotFoundException;
    public List<Product> listOfAllProductsByLocation(Long locationId) throws RecordNotFoundException;
}
