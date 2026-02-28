package com.adminremit.auth.controllers;

import com.adminremit.auth.dto.RoleMasterDTO;
import com.adminremit.auth.models.FeaturesMaster;
import com.adminremit.auth.models.GroupRoleMapping;
import com.adminremit.auth.models.RoleMaster;
import com.adminremit.auth.service.GroupRoleMappingService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GroupRoleMappingController {

    @Autowired
    private GroupRoleMappingService groupRoleMappingService;

    @ResponseBody
    @RequestMapping(value = "load/roles/{id}", method = RequestMethod.GET)
    public String loadFeaturesByFunction(@PathVariable("id") String id) throws Exception {
        Gson gson = new Gson();
        List<RoleMaster> roleMasters = groupRoleMappingService.findAllRolesByGroup(Long.parseLong(id));
        List<RoleMasterDTO> roleMasterDTOS = new ArrayList<>();
        for (RoleMaster roleMaster: roleMasters) {
            RoleMasterDTO roleMasterDTO = new RoleMasterDTO();
            roleMasterDTO.setId(roleMaster.getId());
            roleMasterDTO.setRoleName(roleMaster.getRoleName());
            roleMasterDTOS.add(roleMasterDTO);
        }
        return gson.toJson(roleMasterDTOS);
    }

}
