package com.adminremit.auth.converter;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.Product;
import com.adminremit.auth.service.PartnerService;
import com.adminremit.auth.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class ProductByIdConverter implements Converter<String, Product> {

    private ProductService productService;

    @Autowired
    public ProductByIdConverter(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Product convert(String id) {
        try {
            return productService.getProductById(Long.parseLong(id));
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
