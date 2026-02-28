package com.adminremit.auth.controllers;

import com.adminremit.auth.dto.GroupMasterDTO;
import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.GroupMaster;
import com.adminremit.auth.models.RoleMaster;
import com.adminremit.auth.service.GroupMasterService;
import com.adminremit.auth.service.GroupRoleMappingService;
import com.adminremit.auth.service.RoleMasterService;
import com.adminremit.masters.models.LocationMaster;
import com.adminremit.masters.service.LocationMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class GroupMasterController {

    @Autowired
    private LocationMasterService locationMasterService;

    @Autowired
    private GroupMasterService groupMasterService;

    @Autowired
    private RoleMasterService roleMasterService;

    @Autowired
    private GroupRoleMappingService groupRoleMappingService;

    private static final Logger LOG = LoggerFactory.getLogger(GroupMasterController.class);

    @RequestMapping(value = "/group",method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView, GroupMasterDTO groupMaster) {
        List<LocationMaster> locationMasters = locationMasterService.listOfLocation();
        List<RoleMaster> roleMasters = roleMasterService.listOfRoles();
        modelAndView.addObject("locationMasters", locationMasters);
        modelAndView.addObject("groups",groupMaster);
        modelAndView.addObject("roles",roleMasters);
        modelAndView.setViewName("auth/group/create");
        return modelAndView;
    }

    @RequestMapping(value = "/group/create", method = RequestMethod.POST)
    public String createGroup(@Valid GroupMasterDTO groupMasterDTO, BindingResult bindingResult, ModelAndView modelAndView) throws RecordNotFoundException {
        LOG.info("Create group!!!"+groupMasterDTO.getGroupName());
        if (groupMasterDTO.getLocationMaster() == null) {
            modelAndView.addObject("errorMessage", "Oops!  Select Location.");
            List<RoleMaster> roleMasters = roleMasterService.listOfRoles();
            List<LocationMaster> locationMasters = locationMasterService.listOfLocation();
            modelAndView.addObject("locationMasters", locationMasters);
            modelAndView.addObject("roles",roleMasters);
            modelAndView.addObject("groups",groupMasterDTO);
            modelAndView.setViewName("auth/group/create");
            return "";
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("errorMessage", "Oops!  Fill all the mandatory fields.");
            modelAndView.addObject(groupMasterDTO);
            List<LocationMaster> locationMasters = locationMasterService.listOfLocation();
            List<RoleMaster> roleMasters = roleMasterService.listOfRoles();
            modelAndView.addObject("locationMaster", locationMasters);
            modelAndView.addObject("groups",groupMasterDTO);
            modelAndView.addObject("roles",roleMasters);
            modelAndView.setViewName("auth/group/create");
            return "";
        } else {
            GroupMaster groupMaster = new GroupMaster();
            groupMaster.setId(groupMasterDTO.getId());
            groupMaster.setLocationMaster(locationMasterService.getLocationById(groupMasterDTO.getLocationMaster()));
            groupMaster.setPublish(groupMasterDTO.getPublish());
            groupMaster.setPublish(groupMasterDTO.getPublish());
            groupMaster.setGroupName(groupMasterDTO.getGroupName());
            groupMaster.setGroupDescription(groupMasterDTO.getGroupDescription());
            groupMasterService.save(groupMaster);
            modelAndView.addObject("confirmationMessage", "Group has been created");
            List<GroupMaster> groupMasters = groupMasterService.listOfGroups();
            modelAndView.addObject("groups", groupMasters);
            modelAndView.setViewName("auth/group/list");
        }
        return "redirect:/group/list";
    }

    @RequestMapping(path = {"/group/edit", "/group/edit/{id}"})
    public ModelAndView editGroup(ModelAndView model, @PathVariable("id") Optional<Long> id, BindingResult bindingResult) throws RecordNotFoundException {
        if (bindingResult.hasErrors()) {
            model.addObject("errorMessage", "Oops!  Fill all the mandatory fields.");
            List<LocationMaster> locationMasters = locationMasterService.listOfLocation();
            List<RoleMaster> roleMasters = roleMasterService.listOfRoles();
            model.addObject("locationMasters", locationMasters);
            model.addObject("roles",roleMasters);
            model.setViewName("auth/group/create");
        }
        if (id.isPresent()) {
            GroupMaster groupMaster = groupMasterService.getById(id.get());
            GroupMasterDTO groupMasterDTO = new GroupMasterDTO();
            groupMasterDTO.setId(groupMaster.getId());
            groupMasterDTO.setLocationMaster(groupMaster.getLocationMaster().getId());
            groupMasterDTO.setGroupName(groupMaster.getGroupName());
            groupMasterDTO.setGroupDescription(groupMaster.getGroupDescription());
            groupMasterDTO.setLocationMaster(groupMaster.getLocationMaster().getId());
            groupMasterDTO.setPublish(groupMaster.getPublish());
            model.addObject("groups", groupMasterDTO);
            List<LocationMaster> locationMasters = locationMasterService.listOfLocation();
            List<RoleMaster> roleMasters = roleMasterService.listOfRoles();
            model.addObject("locationMasters", locationMasters);
            model.addObject("roles",roleMasters);
            model.setViewName("auth/group/create");
        } else {
            model.addObject("groups", new GroupMaster());
            model.setViewName("auth/group/create");
        }
        return model;
    }

    @RequestMapping(value = "/group/list", method = RequestMethod.GET)
    public ModelAndView listGroups(ModelAndView modelAndView) {
        List<GroupMaster> groupMasters = groupMasterService.listOfGroups();
        modelAndView.addObject("groups", groupMasters);
        modelAndView.setViewName("auth/group/list");
        return modelAndView;
    }

    @RequestMapping(value = "/group/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteGroupMaster(ModelAndView modelAndView, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException {
        List<RoleMaster> roleMasters = groupRoleMappingService.findAllRolesByGroup(id.get());
        if(roleMasters.size() == 0) {
            groupMasterService.deleteGroup(id.get());
            modelAndView.addObject("confirmationMessage", "Group has been deleted!!");
            List<GroupMaster> groupMasters = groupMasterService.listOfAllGroups();
            modelAndView.addObject("groups", groupMasters);
            modelAndView.setViewName("auth/group/list");
            return modelAndView;
        } else {
            modelAndView.addObject("errorMessage", "You can't delete this group!!");
            List<GroupMaster> groupMasters = groupMasterService.listOfGroups();
            modelAndView.addObject("groups", groupMasters);
            modelAndView.setViewName("auth/group/list");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/api/group/deleteGroup/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String deleteGroupById(@PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException {
        List<RoleMaster> roleMasters = groupRoleMappingService.findAllRolesByGroup(id.get());
        if(roleMasters.size() == 0) {
            groupMasterService.deleteGroup(id.get());
            List<GroupMaster> groupMasters = groupMasterService.listOfAllGroups();
            return "0 group has been deleted!!";
        }else {
            List<GroupMaster> groupMasters = groupMasterService.listOfAllGroups();
            return  "1 You can't delete this group!!";
        }
    }
}
