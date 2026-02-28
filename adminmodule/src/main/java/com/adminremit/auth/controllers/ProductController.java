package com.adminremit.auth.controllers;

import com.adminremit.auth.dto.ProductDTO;
import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.OwnerDesignation;
import com.adminremit.auth.models.Partner;
import com.adminremit.auth.models.Product;
import com.adminremit.auth.service.OwnerDesignationService;
import com.adminremit.auth.service.PartnerService;
import com.adminremit.auth.service.ProductService;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.LocationMaster;
import com.adminremit.masters.models.PaymentReceiveMode;
import com.adminremit.masters.service.CountriesService;
import com.adminremit.masters.service.LocationMasterService;
import com.github.javaparser.utils.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private LocationMasterService locationMasterService;

    @Autowired
    private OwnerDesignationService ownerDesignationService;

    @Autowired
    private CountriesService countriesService;

    @Autowired
    private PartnerService partnerService;

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView, ProductDTO productDTO) {
        List<LocationMaster> locationMasters = locationMasterService.listOfLocation();
        List<OwnerDesignation> ownerDesignations = ownerDesignationService.listOfDesignations();
        Integer productCode = productService.getProductId();
        productDTO.setProductCode(productCode.toString());
        List<Countries> countries = countriesService.listOfCountriesDialingCode();
        modelAndView.addObject("countries",countries);
        modelAndView.addObject("productDTO", productDTO);
        modelAndView.addObject("locationMasters", locationMasters);
        modelAndView.addObject("ownerDesignation", ownerDesignations);
        modelAndView.setViewName("masters/product/create");

        return modelAndView;
    }

    @RequestMapping(value = "/product/create", method = RequestMethod.POST)
    public String createProduct(@Valid ProductDTO productDTO, BindingResult bindingResult, ModelAndView modelAndView) throws RecordNotFoundException {
        if (productDTO.getLocationMaster() == null) {
            modelAndView.addObject("errorMessage", "Oops!  Select Location.");
            modelAndView.setViewName("masters/product/create");
            return "masters/product/create";
        }
        if (productDTO.getOwnerDesignation() == null) {
            modelAndView.addObject("errorMessage", "Oops!  Select owner designation.");
            modelAndView.setViewName("masters/product/create");
            return "masters/product/create";
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject(productDTO);
            List<LocationMaster> locationMasters = locationMasterService.listOfLocation();
            List<OwnerDesignation> ownerDesignations = ownerDesignationService.listOfDesignations();
            modelAndView.addObject("locationMasters", locationMasters);
            modelAndView.addObject("ownerDesignation", ownerDesignations);
            List<Countries> countries = countriesService.listOfCountries();
            modelAndView.addObject("countries",countries);
            modelAndView.addObject("errorMessage", "Oops!  Please fill all the mandatory fields.");
            return "masters/product/create";
        } else {
            Product product = new Product();
            product.setProductCode(productDTO.getProductCode());
            product.setId(productDTO.getId());
            product.setLocationMaster(locationMasterService.getLocationById(productDTO.getLocationMaster()));
            product.setOwnerDesignation(ownerDesignationService.getById(productDTO.getOwnerDesignation()));
            product.setPublish(productDTO.getPublish());
            product.setProductName(productDTO.getProductName());
            product.setOwnerName(productDTO.getOwnerName());
            product.setCountryCode(productDTO.getCountryCode());
            product.setPhoneNumber(productDTO.getPhoneNumber());
            product.setOfficialEmail(productDTO.getOfficialEmail());
            product.setProductDescription(productDTO.getProductDescription());
            productService.save(product);
            modelAndView.addObject("confirmationMessage", "Product has been created");
            List<Product> productList = productService.listOfProducts();
            modelAndView.addObject("products", productList);
            modelAndView.setViewName("masters/product/list");
        }
        return "redirect:/product/list";
    }

    @RequestMapping(path = {"/product/edit", "/product/edit/{id}"})
    public ModelAndView editDesignation(ModelAndView model, @PathVariable("id") Optional<Long> id, BindingResult bindingResult) throws RecordNotFoundException {
        if (bindingResult.hasErrors()) {
            model.setViewName("masters/product/create");
        }
        if (id.isPresent()) {
            Product entity = productService.getProductById(id.get());
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(entity.getId());
            productDTO.setProductCode(entity.getProductCode());
            productDTO.setLocationMaster(entity.getLocationMaster().getId());
            productDTO.setOfficialEmail(entity.getOfficialEmail());
            productDTO.setProductName(entity.getProductName());
            productDTO.setOwnerDesignation(entity.getOwnerDesignation().getId());
            productDTO.setOwnerName(entity.getOwnerName());
            productDTO.setCountryCode(entity.getCountryCode());
            productDTO.setPhoneNumber(entity.getPhoneNumber());
            productDTO.setProductDescription(entity.getProductDescription());
            productDTO.setPublish(entity.getPublish());
            model.addObject("productDTO", productDTO);
            List<LocationMaster> locationMasters = locationMasterService.listOfLocation();
            List<OwnerDesignation> ownerDesignations = ownerDesignationService.listOfDesignations();
            model.addObject("locationMasters", locationMasters);
            model.addObject("ownerDesignation", ownerDesignations);
            List<Countries> countries = countriesService.listOfCountries();
            model.addObject("countries",countries);
            model.setViewName("masters/product/create");
        } else {
            model.addObject("product", new Product());
            model.setViewName("masters/product/create");
        }
        return model;
    }

    @RequestMapping(value = "/product/list", method = RequestMethod.GET)
    public ModelAndView listProducts(ModelAndView modelAndView,@PageableDefault(size = 10) Pageable pageable) {
        List<Product> productList = productService.listOfAllProducts();
        modelAndView.addObject("products", productList);
        modelAndView.setViewName("masters/product/list");
        return modelAndView;
    }

    @RequestMapping(value = "/product/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteProdcut(ModelAndView modelAndView, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException {
        List<Partner> partners = partnerService.listOfPartnersByProduct(id.get());
        Log.info("Sizr "+partners.size());
        if(partners.size() == 0) {
            productService.deleteProduct(id.get());
            modelAndView.addObject("confirmationMessage", "Product has been deleted!!");
            List<Product> productList = productService.listOfAllProducts();
            modelAndView.addObject("products", productList);
            modelAndView.setViewName("masters/product/list");
            return modelAndView;
        } else {
            modelAndView.addObject("errorMessage", "You can't delete this product!!");
            List<Product> productList = productService.listOfAllProducts();
            modelAndView.addObject("products", productList);
            modelAndView.setViewName("masters/product/list");
            return modelAndView;
        }
    }
    @RequestMapping(value = "/api/product/deleteproductapi/{id}", method = RequestMethod.GET)
	public @ResponseBody  String deleteProductById(@PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
    	 List<Partner> partners = partnerService.listOfPartnersByProduct(id.get());
    	  if(partners.size() == 0) {
			 productService.deleteProduct(id.get());
			 List<Product> productList = productService.listOfAllProducts();
			return "0 Product has been deleted!!";
		} else {
			 List<Product> productList = productService.listOfAllProducts();
			return "1 You can't delete this product!!";
		}

	}
    
    @RequestMapping(value= "/product/complaince/complaincemaker", method = RequestMethod.GET)
    public ModelAndView ComplianceMaker(HttpServletRequest httpServletRequest) {
        return new ModelAndView("complaince/complaincemaker");
    }
    
    @RequestMapping(value= "/product/complaince/complaincechecker", method = RequestMethod.GET)
    public ModelAndView ComplianceChecker(HttpServletRequest httpServletRequest) {
        return new ModelAndView("complaince/complaincechecker");
    }
    
    @RequestMapping(value= "/product/Return/return_maker", method = RequestMethod.GET)
    public ModelAndView ReturnMaker(HttpServletRequest httpServletRequest) {
        return new ModelAndView("backoffice/return/returnmaker");
    }
    
    @RequestMapping(value= "/product/Return/return_checker", method = RequestMethod.GET)
    public ModelAndView ReturnChecker(HttpServletRequest httpServletRequest) {
        return new ModelAndView("backoffice/return/returnchecker");
    }
    
    @RequestMapping(value= "/product/Refund/refund_maker", method = RequestMethod.GET)
    public ModelAndView RefundMaker(HttpServletRequest httpServletRequest) {
        return new ModelAndView("backoffice/refund/refundmaker");
    }
    
    @RequestMapping(value= "/product/Refund/refund_checker", method = RequestMethod.GET)
    public ModelAndView RefundChecker(HttpServletRequest httpServletRequest) {
        return new ModelAndView("backoffice/refund/refundchecker");
    }
    
    @RequestMapping(value= "/reconciliation/unmatchedentrymaker", method = RequestMethod.GET)
    public ModelAndView ReconciliationUnmatchedMaker(HttpServletRequest httpServletRequest) {
        return new ModelAndView("reconciliation/unmatchedentry/unmatchedentrymaker");
    }
    
    @RequestMapping(value= "/reconciliation/unmatchedentrychecker", method = RequestMethod.GET)
    public ModelAndView ReconciliationUnmatchedChecker(HttpServletRequest httpServletRequest) {
        return new ModelAndView("reconciliation/unmatchedentry/unmatchedentrychecker");
    }	
}
