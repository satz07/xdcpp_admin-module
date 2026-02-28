package com.adminremit.auth.controllers;

import com.adminremit.auth.dto.PartnerDTO;
import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.OwnerDesignation;
import com.adminremit.auth.models.Partner;
import com.adminremit.auth.models.Product;
import com.adminremit.auth.models.User;
import com.adminremit.auth.service.OwnerDesignationService;
import com.adminremit.auth.service.PartnerService;
import com.adminremit.auth.service.ProductService;
import com.adminremit.auth.service.UserService;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.LocationMaster;
import com.adminremit.masters.models.PaymentReceiveMode;
import com.adminremit.masters.service.CountriesService;
import com.adminremit.masters.service.LocationMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class PartnerController {

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private LocationMasterService locationMasterService;

    @Autowired
    private OwnerDesignationService ownerDesignationService;

    @Autowired
    private CountriesService countriesService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/partner", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView, PartnerDTO partnerDTO) {
        List<LocationMaster> locationMasters = locationMasterService.listOfLocation();
        List<Product> productList = productService.listOfProducts();
        List<OwnerDesignation> ownerDesignations = ownerDesignationService.listOfDesignations();
        List<Countries> countries = countriesService.listOfCountriesDialingCode();
        modelAndView.addObject("countries",countries);
        modelAndView.addObject("partner", partnerDTO);
        modelAndView.addObject("locationMasters", locationMasters);
        modelAndView.addObject("products", productList);
        modelAndView.addObject("ownerDesignation", ownerDesignations);
        modelAndView.setViewName("auth/partner/create");

        return modelAndView;
    }

    @RequestMapping(value = "/partner/create", method = RequestMethod.POST)
    public ModelAndView createPartner(@Valid PartnerDTO partnerDTO, BindingResult bindingResult, ModelAndView modelAndView) throws RecordNotFoundException {
        if (partnerDTO.getLocationMaster() == null) {
            modelAndView.addObject("errorMessage", "Oops!  Select Location.");
            modelAndView.setViewName("auth/partner/create");
            return modelAndView;
        }
        if (partnerDTO.getProduct() == null) {
            modelAndView.addObject("errorMessage", "Oops!  Select owner Product.");
            modelAndView.setViewName("auth/partner/create");
            return modelAndView;
        }
        if (bindingResult.hasErrors()) {
            List<LocationMaster> locationMasters = locationMasterService.listOfLocation();
            List<Product> productList = productService.listOfProducts();
            List<OwnerDesignation> ownerDesignations = ownerDesignationService.listOfDesignations();
            List<Countries> countries = countriesService.listOfCountries();
            modelAndView.addObject("countries",countries);
            modelAndView.addObject("locationMasters", locationMasters);
            modelAndView.addObject("products", productList);
            modelAndView.addObject("ownerDesignation", ownerDesignations);
            modelAndView.addObject("partner", partnerDTO);
            modelAndView.setViewName("auth/partner/create");
            modelAndView.addObject("errorMessage", "Oops!  Please fill all the mandatory fields.");
            return modelAndView;
        } else {
            Partner partner = new Partner();
            partner.setPartnerId(partnerDTO.getPartnerId());
            partner.setId(partnerDTO.getId());
            partner.setLocationMaster(locationMasterService.getLocationById(partnerDTO.getLocationMaster()));
            partner.setProduct(productService.getProductById(partnerDTO.getProduct()));
            partner.setPublish(partnerDTO.getPublish());
            partner.setPartnerName(partnerDTO.getPartnerName());
            partner.setOfficialEmail(partnerDTO.getOfficialEmail());
            partner.setAddress(partnerDTO.getAddress());
            partner.setDescription(partnerDTO.getDescription());
            partner.setPhoneNumber(partnerDTO.getPhoneNumber());
            partner.setOwnerName(partnerDTO.getOwnerName());
            partner.setPartnerDescription(partnerDTO.getPartnerDescription());
            partner.setDialingCode(partnerDTO.getCountryCode());
            partner.setOwnerDesignation(ownerDesignationService.getById(partnerDTO.getOwnerDesignation()));
            partnerService.save(partner);
            modelAndView.addObject("confirmationMessage", "Partner has been created");
            List<Partner> partners = partnerService.listOfPartners();
            modelAndView.addObject("partners", partners);
            modelAndView.setViewName("auth/partner/list");
        }
        return modelAndView;
    }

    @RequestMapping(path = {"/partner/edit", "/partner/edit/{id}"})
    public ModelAndView editPartner(ModelAndView model, @PathVariable("id") Optional<Long> id, BindingResult bindingResult) throws RecordNotFoundException {
        if (bindingResult.hasErrors()) {
            model.setViewName("auth/partner/create");
        }
        if (id.isPresent()) {
            Partner entity = partnerService.getById(id.get());
            PartnerDTO partnerDTO = new PartnerDTO();
            partnerDTO.setPartnerId(entity.getPartnerId());
            partnerDTO.setId(entity.getId());
            partnerDTO.setPublish(entity.getPublish());
            partnerDTO.setLocationMaster(entity.getLocationMaster().getId());
            partnerDTO.setOfficialEmail(entity.getOfficialEmail());
            partnerDTO.setPartnerName(entity.getPartnerName());
            partnerDTO.setProduct(entity.getProduct().getId());
            partnerDTO.setAddress(entity.getAddress());
            partnerDTO.setPhoneNumber(entity.getPhoneNumber());
            partnerDTO.setDescription(entity.getDescription());
            partnerDTO.setPartnerDescription(entity.getPartnerDescription());
            partnerDTO.setOwnerDesignation(entity.getOwnerDesignation().getId());
            partnerDTO.setOwnerName(entity.getOwnerName());
            partnerDTO.setCountryCode(entity.getDialingCode());
            model.addObject("partner", partnerDTO);
            List<Countries> countries = countriesService.listOfCountries();
            model.addObject("countries",countries);
            List<LocationMaster> locationMasters = locationMasterService.listOfLocation();
            List<Product> productList = productService.listOfProducts();
            List<OwnerDesignation> ownerDesignations = ownerDesignationService.listOfDesignations();
            model.addObject("products", productList);
            model.addObject("locationMasters", locationMasters);
            model.addObject("ownerDesignation", ownerDesignations);
            model.setViewName("auth/partner/create");
        } else {
            model.addObject("partner", new Partner());
            model.setViewName("auth/partner/create");
        }
        return model;
    }

    @RequestMapping(value = "/partner/list", method = RequestMethod.GET)
    public ModelAndView listPartners(ModelAndView modelAndView) {
        List<Partner> partners = partnerService.listOfPartners();
        modelAndView.addObject("partners", partners);
        modelAndView.setViewName("auth/partner/list");
        return modelAndView;
    }

    @RequestMapping(value = "/partner/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deletePartner(ModelAndView modelAndView, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException {
        List<User> userList = userService.getAllUserByPartner(id.get());
        if(userList.size() == 0) {
            partnerService.deletePartner(id.get());
            modelAndView.addObject("confirmationMessage", "Partner has been deleted!!");
            List<Partner> partners = partnerService.listOfPartners();
            modelAndView.addObject("partners", partners);
            modelAndView.setViewName("auth/partner/list");
            return modelAndView;
        } else {
            modelAndView.addObject("errorMessage", "You can't delete this partner!!");
            List<Partner> partners = partnerService.listOfPartners();
            modelAndView.addObject("partners", partners);
            modelAndView.setViewName("auth/partner/list");
            return modelAndView;
        }
    }
    @RequestMapping(value = "/api/partner/deletePartner/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String deletePartnerById(@PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException {
        List<User> userList = userService.getAllUserByPartner(id.get());
        if(userList.size() == 0) {
            partnerService.deletePartner(id.get());
            List<Partner> partners = partnerService.listOfPartners();
            return " 0 Partner has been deleted!!";
        } else {
            List<Partner> partners = partnerService.listOfPartners();
            return " 1 You can't delete this partner!!";
        }

    }
}
