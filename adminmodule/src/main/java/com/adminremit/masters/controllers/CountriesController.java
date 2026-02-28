package com.adminremit.masters.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.PaymentReceiveMode;
import com.adminremit.masters.service.CountriesService;
import com.adminremit.masters.service.CurrenciesService;
import com.adminremit.masters.service.PaymentReceiveModeService;
import com.adminremit.config.Web3ModeContext;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;

/**
 * WEB3 migration changes - Restored CountriesController
 * Original logic preserved; controller was previously empty causing 404s for /country and /country/list
 */
@Slf4j
@Controller
public class CountriesController {

    @Autowired
    private CountriesService countriesService;

    @Autowired
    private PaymentReceiveModeService paymentReceiveModeService;

    @Autowired
    private CurrenciesService currService;

    @RequestMapping(value = "/country", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView, Countries countries) {
        modelAndView.addObject("country", countries);
        modelAndView.setViewName("masters/country/create");
        return modelAndView;
    }

    @RequestMapping(value = "/country/create", method = RequestMethod.POST)
    public ModelAndView createCountry(@Valid Countries countries, BindingResult bindingResult,
                                      ModelAndView modelAndView, HttpSession session) throws RecordNotFoundException, NoSuchFieldException {
        // WEB3 migration changes - Ensure Web3 mode is set from session before any database operations
        if (session != null) {
            Object sessionMode = session.getAttribute("web3Mode");
            boolean isWeb3Mode = sessionMode != null && Boolean.parseBoolean(sessionMode.toString());
            Web3ModeContext.setWeb3Mode(isWeb3Mode);
            log.info("WEB3 migration: Setting Web3 mode to {} for country create operation", isWeb3Mode);
        }
        
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("country", countries);
            modelAndView.setViewName("masters/country/create");
            return modelAndView;
        } else {

            if (countries.getId() == null) {
                boolean isCountryDuplicate = countriesService.getByCountryCodeAndCountryName(
                        countries.getCountryCode(), countries.getCountryName());
                if (isCountryDuplicate) {
                    modelAndView.addObject("errorMessage", "Duplicate value!!!");
                    modelAndView.addObject("country", countries);
                    modelAndView.setViewName("masters/country/create");
                    return modelAndView;
                }
                boolean isPresent = countriesService.getCountryByCountryCodeAndCountryName(countries);
                if (isPresent) {
                    modelAndView.addObject("Country is present in backend", "rollback");
                    modelAndView.addObject("country", countries);
                    return new ModelAndView("redirect:/country/list");
                }
            }

            if (countries.getId() != null) {
                boolean countries1 = countriesService.getByIdAndCountryCodeAndCountryName(
                        countries.getId(), countries.getCountryCode(), countries.getCountryName());
                if (countries1) {
                    modelAndView.addObject("errorMessage", "Duplicate value!!!");
                    modelAndView.addObject("country", countries);
                    modelAndView.setViewName("masters/country/create");
                    return modelAndView;
                }
            }

            log.info("WEB3 migration: Saving country with mode: {}", Web3ModeContext.isWeb3Mode() ? "Web3" : "Web2");
            countriesService.save(countries);
            log.info("WEB3 migration: Country saved successfully");
            modelAndView.addObject("confirmationMessage", "Country has been created");
            List<Countries> countriesList = countriesService.listOfCountries();
            modelAndView.addObject("countries", countriesList);
        }
        return new ModelAndView("redirect:/country/list");
    }

    @RequestMapping(path = {"/country/edit", "/country/edit/{id}"})
    public ModelAndView editCountry(ModelAndView model, @PathVariable("id") Optional<Long> id,
                                    BindingResult bindingResult) throws RecordNotFoundException {
        if (bindingResult.hasErrors()) {
            model.setViewName("masters/country/create");
        }
        if (id.isPresent()) {
            Countries countries = countriesService.getById(id.get());
            Countries tempCountry = new Countries();
            tempCountry.setCountryCode(countries.getCountryCode());
            tempCountry.setCountryName(countries.getCountryName());
            tempCountry.setDialingCode(countries.getDialingCode());
            tempCountry.setIsoCode(countries.getIsoCode());
            tempCountry.setPublish(countries.getPublish());
            model.addObject("country", countries);
            model.setViewName("masters/country/create");
        } else {
            model.addObject("countries", new Countries());
            model.setViewName("masters/country/create");
        }
        return model;
    }

    @RequestMapping(value = "/country/list", method = RequestMethod.GET)
    public ModelAndView listCountries(ModelAndView modelAndView, @PageableDefault(size = 10) Pageable pageable, HttpSession session) {
        // WEB3 migration changes - Ensure Web3 mode is set from session before any database operations
        if (session != null) {
            Object sessionMode = session.getAttribute("web3Mode");
            boolean isWeb3Mode = sessionMode != null && Boolean.parseBoolean(sessionMode.toString());
            Web3ModeContext.setWeb3Mode(isWeb3Mode);
            log.info("WEB3 migration: Setting Web3 mode to {} for country list operation", isWeb3Mode);
        }
        
        List<Countries> countries = countriesService.listOfCountriesCountryName();
        log.info("WEB3 migration: Retrieved {} countries with mode: {}", countries.size(), Web3ModeContext.isWeb3Mode() ? "Web3" : "Web2");
        modelAndView.addObject("countries", countries);
        modelAndView.setViewName("masters/country/list");
        return modelAndView;
    }

    @RequestMapping(value = "/country/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteCountry(ModelAndView modelAndView, @PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException {
        List<PaymentReceiveMode> paymentReceiveModes =
                paymentReceiveModeService.findAllPaymentsReceiveByCountry(id.get());
        if (paymentReceiveModes.size() == 0) {
            //countriesService.deleteCountry(id.get());
            modelAndView.addObject("confirmationMessage", "Country has been deleted!!");
            List<Countries> countries = countriesService.listOfCountriesCountryName();
            modelAndView.addObject("countries", countries);
            modelAndView.setViewName("masters/country/list");
            return modelAndView;
        } else {
            modelAndView.addObject("errorMessage", "You can't delete this country!!!");
            List<Countries> countries = countriesService.listOfCountriesCountryName();
            modelAndView.addObject("countries", countries);
            modelAndView.setViewName("masters/country/list");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/api/country/deleteapi/{id}", method = RequestMethod.GET)
    public @ResponseBody String deleteCountryById(@PathVariable("id") Optional<Long> id, HttpSession session)
            throws RecordNotFoundException {
        // WEB3 migration changes - Ensure Web3 mode is set from session before any database operations
        if (session != null) {
            Object sessionMode = session.getAttribute("web3Mode");
            boolean isWeb3Mode = sessionMode != null && Boolean.parseBoolean(sessionMode.toString());
            Web3ModeContext.setWeb3Mode(isWeb3Mode);
            log.debug("WEB3 migration: Setting Web3 mode to {} for country delete operation", isWeb3Mode);
        }
        
        if (!id.isPresent()) {
            return "1 Invalid Country ID";
        }
        
        try {
            // Check all dependencies using comprehensive method
            String dependencyCheck = countriesService.checkCountryDependencies(id.get());
            
            if (dependencyCheck == null) {
                // No dependencies found, safe to delete
                countriesService.deleteCountry(id.get());
                log.info("WEB3 migration: Country {} deleted successfully", id.get());
                return "0 Deleted Successfully";
            } else {
                // Dependencies found, return detailed error message
                log.warn("WEB3 migration: Cannot delete country {} - {}", id.get(), dependencyCheck);
                return "1 " + dependencyCheck;
            }
        } catch (Exception e) {
            log.error("WEB3 migration: Error deleting country {}", id.get(), e);
            return "1 Error deleting country: " + e.getMessage();
        }
    }
}


