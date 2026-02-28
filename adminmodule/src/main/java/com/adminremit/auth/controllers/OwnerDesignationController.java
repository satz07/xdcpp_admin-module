package com.adminremit.auth.controllers;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.OwnerDesignation;
import com.adminremit.auth.models.Product;
import com.adminremit.auth.service.OwnerDesignationService;
import com.adminremit.auth.service.ProductService;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.PaymentReceiveMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class OwnerDesignationController {

    @Autowired
    private OwnerDesignationService ownerDesignationService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/designation",method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView, OwnerDesignation designation,BindingResult bindingResult) {
        modelAndView.addObject("designation",designation);
        modelAndView.setViewName("auth/designation/create");
        return modelAndView;
    }

    @RequestMapping(value = "/designation/create", method = RequestMethod.POST)
    public ModelAndView createDesignation(ModelAndView modelAndView, @Valid OwnerDesignation ownerDesignation, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("designation",ownerDesignation);
            modelAndView.addObject("errorMessage", "Designation is mandatory");
            modelAndView.setViewName("auth/designation/create");
            return modelAndView;
        } else {
            OwnerDesignation ownerDesignation2 = ownerDesignationService.getOwnerDesignationByName(ownerDesignation.getDesignation());
            if (ownerDesignation2 != null && ownerDesignation2.getId() != ownerDesignation.getId()) {
                modelAndView.addObject("designation",ownerDesignation);
            	modelAndView.addObject("errorMessage", "Duplicate value!!!");
                modelAndView.setViewName("auth/designation/create");
                return modelAndView;
            }
            if (ownerDesignation2 != null && ownerDesignation.getId() == null) {
                modelAndView.addObject("designation",ownerDesignation);
            	modelAndView.addObject("errorMessage", "Duplicate value!!!");
                modelAndView.setViewName("auth/designation/create");
                return modelAndView;
            }
            ownerDesignationService.save(ownerDesignation);
            modelAndView.addObject("confirmationMessage", "Designation has been created");
            List<OwnerDesignation> ownerDesignations = ownerDesignationService.listOfDesignations();
            modelAndView.addObject("designations",ownerDesignations);
            modelAndView.setViewName("auth/designation/list");
        }
        return modelAndView;
    }

    @RequestMapping(path = {"/designation/edit", "/designation/edit/{id}"})
    public ModelAndView editDesignation(ModelAndView model, @PathVariable("id") Optional<Long> id, BindingResult bindingResult) throws RecordNotFoundException
    {
        if(bindingResult.hasErrors()) {
            model.setViewName("auth/designation/create");
        }
        if (id.isPresent()) {
            OwnerDesignation entity = ownerDesignationService.getById(id.get());
            model.addObject("designation", entity);
            model.setViewName("auth/designation/create");
        } else {
            model.addObject("designation", new OwnerDesignation());
            model.setViewName("auth/designation/create");
        }
        return model;
    }

    @RequestMapping(value = "/designation/list", method = RequestMethod.GET)
    public ModelAndView listDesignation(ModelAndView modelAndView, OwnerDesignation designation,BindingResult bindingResult) {
        List<OwnerDesignation> ownerDesignations = ownerDesignationService.listOfDesignations();
        modelAndView.addObject("designations",ownerDesignations);
        modelAndView.setViewName("auth/designation/list");
        return modelAndView;
    }

    @RequestMapping(value = "/designation/delete/{id}",method = RequestMethod.GET)
    public ModelAndView deleteDesignation(ModelAndView modelAndView, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException{
        List<Product> productList = productService.listOfAllProductsByDesignation(id.get());
        if(productList.size() == 0) {
          //  ownerDesignationService.deleteDesignation(id.get());
            modelAndView.addObject("confirmationMessage", "Designation has been deleted!!");
            List<OwnerDesignation> ownerDesignations = ownerDesignationService.listOfDesignations();
            modelAndView.addObject("designations", ownerDesignations);
            modelAndView.setViewName("auth/designation/list");
            return modelAndView;
        } else  {
            modelAndView.addObject("errorMessage", "You can't delete this designation!!");
            List<OwnerDesignation> ownerDesignations = ownerDesignationService.listOfDesignations();
            modelAndView.addObject("designations", ownerDesignations);
            modelAndView.setViewName("auth/designation/list");
            return modelAndView;
        }
    }
    @RequestMapping(value = "/api/designation/deletedesignationapi/{id}", method = RequestMethod.GET)
	public @ResponseBody  String deleteDesignationById(@PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
    	List<Product> productList = productService.listOfAllProductsByDesignation(id.get());
    	  if(productList.size() == 0) {
    		  ownerDesignationService.deleteDesignation(id.get());
    		  List<OwnerDesignation> ownerDesignations = ownerDesignationService.listOfDesignations();			
    		  return "Designation has been deleted!!";
		} else {
			  List<OwnerDesignation> ownerDesignations = ownerDesignationService.listOfDesignations();
			return "You can't delete this designation!!";
		}

	}

}
