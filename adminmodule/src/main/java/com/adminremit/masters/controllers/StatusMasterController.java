package com.adminremit.masters.controllers;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.models.StatusMaster;
import com.adminremit.masters.service.StatusMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class StatusMasterController {


    @Autowired
    private StatusMasterService statusMasterService;

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView, StatusMaster statusMaster) {
        modelAndView.addObject("statusMaster",statusMaster);
        modelAndView.setViewName("masters/status/create");
        return modelAndView;
    }

    @RequestMapping(value = "/status/create", method = RequestMethod.POST)
    public String createStatus(@Valid StatusMaster statusMaster, BindingResult bindingResult, Model modelAndView) throws RecordNotFoundException, NoSuchFieldException {
        if (bindingResult.hasErrors()) {
            modelAndView.addAttribute("statusMaster",statusMaster);
            return "masters/status/create";
        } else {
            statusMasterService.save(statusMaster);
            modelAndView.addAttribute("confirmationMessage", "Global Limit has been created");
            List<StatusMaster> statusMasters = statusMasterService.listOfStatus();
            modelAndView.addAttribute("statusMasters", statusMasters);
        }
        return "redirect:/status/list";
    }

    @RequestMapping(path = {"/status/edit", "/status/edit/{id}"})
    public ModelAndView editStatus(ModelAndView model, @PathVariable("id") Optional<Long> id, BindingResult bindingResult) throws RecordNotFoundException {
        if (bindingResult.hasErrors()) {
            model.setViewName("masters/status/create");
        }
        if (id.isPresent()) {
            StatusMaster statusMaster = statusMasterService.getById(id.get());
            model.addObject("statusMaster",statusMaster);
            model.setViewName("masters/status/create");
        } else {
            model.addObject("statusMaster", new StatusMaster());
            model.setViewName("masters/status/create");
        }
        return model;
    }

    @RequestMapping(value = "/status/list", method = RequestMethod.GET)
    public ModelAndView listStatus(ModelAndView modelAndView,@PageableDefault(size = 10) Pageable pageable) {
        List<StatusMaster> statusMasters = statusMasterService.listOfStatus();
        modelAndView.addObject("statusMasters", statusMasters);
        modelAndView.setViewName("masters/status/list");
        return modelAndView;
    }

    @RequestMapping(value = "/status/delete/{id}", method = RequestMethod.GET)
    public String deleteStatus(ModelAndView modelAndView, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException {
        statusMasterService.deleteStatus(id.get());
        modelAndView.addObject("confirmationMessage", "Country has been deleted!!");
        return "redirect:/status/list";
    }
}
