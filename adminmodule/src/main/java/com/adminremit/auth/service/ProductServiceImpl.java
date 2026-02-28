package com.adminremit.auth.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.Product;
import com.adminremit.auth.repository.ProductRepository;
import com.adminremit.masters.service.LocationMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OwnerDesignationService ownerDesignationService;

    @Autowired
    private LocationMasterService locationMasterService;

    public Product save(Product product) {
        if(product.getId() == null) {
            product.setPublish(true);
            product =productRepository.save(product);
            return product;
        } else {
            Optional<Product> product1 = productRepository.findById(product.getId());
            if(product1.isPresent()) {
                Product updateProduct = product1.get();
                updateProduct.setPublish(product.getPublish());
                updateProduct.setProductName(product.getProductName());
                updateProduct.setLocationMaster(product.getLocationMaster());
                updateProduct.setOfficialEmail(product.getOfficialEmail());
                updateProduct.setOwnerDesignation(product.getOwnerDesignation());
                updateProduct.setCountryCode(product.getCountryCode());
                updateProduct.setPhoneNumber(product.getPhoneNumber());
                updateProduct.setOwnerName(product.getOwnerName());
                updateProduct.setProductDescription(product.getProductDescription());
                return productRepository.save(updateProduct);
            } {
                product.setPublish(true);
                product =productRepository.save(product);
                return product;
            }
        }
    }

    public List<Product> listOfProducts() {
        List<Product> productList = null;
        try {
           productList = productRepository.findAllByIsDeleted(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> listOfAllProducts() {
        List<Product> productList = null;
        try {
            productList = productRepository.findAllByIsDeleted(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    public Product getProductById(Long id) throws RecordNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        Product product1 = null;
        if(product.isPresent()) {
            product1 = product.get();
        }
        return product1;
    }

    public void deleteProduct(Long id) throws RecordNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            Product product1 = product.get();
            product1.setIsDeleted(true);
            productRepository.save(product1);
        }
    }

    @Override
    public Integer getProductId() {
        Random r = new Random( System.currentTimeMillis() );
        return 10000 + r.nextInt(20000);
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAllByPublishTrue(pageable);
    }

    @Override
    public List<Product> listOfAllProductsByDesignation(Long designationId) throws RecordNotFoundException {
        return productRepository.findAllByIsDeletedAndOwnerDesignationAndPublishTrue(false,ownerDesignationService.getById(designationId));
    }

    @Override
    public List<Product> listOfAllProductsByLocation(Long locationId) throws RecordNotFoundException {
        return productRepository.findAllByIsDeletedAndLocationMasterAndPublishTrue(false,locationMasterService.getLocationById(locationId));
    }
}
