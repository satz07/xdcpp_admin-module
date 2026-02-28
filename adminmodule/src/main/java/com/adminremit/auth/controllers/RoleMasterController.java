package com.adminremit.auth.controllers;

import com.adminremit.auth.dto.*;
import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.*;
import com.adminremit.auth.service.*;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.PaymentReceiveMode;

import liquibase.pro.packaged.F;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.*;

@Controller
public class RoleMasterController {

    @Autowired
    private RoleMasterService roleMasterService;

    @Autowired
    private GroupMasterService groupMasterService;

    @Autowired
    private FunctionMasterService functionMasterService;

    @Autowired
    private FeaturesMasterService featuresMasterService;

    @Autowired
    private RoleFeatureMappingService roleFeatureMappingService;

    @Autowired
    private GroupRoleMappingService groupRoleMappingService;

    @Autowired
    private UserRoleMappingService userRoleMappingService;

    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView, RoleMasterDTO roleMaster) {
        List<GroupMaster> groupMasters = groupMasterService.listOfGroups();
        List<FunctionMaster> functionMasterList = functionMasterService.listOfFunctions();
        List<FeaturesMaster> featuresMasters = featuresMasterService.listOfFeatures();

        List<String> featureMasterDTOS = new ArrayList<>();
        List<FeaturesMastersList> featuresMastersLists = new ArrayList<>();
        FeaturesMastersList featuresMastersList = new FeaturesMastersList();
        featuresMastersList.setFeaturesMasters(featureMasterDTOS);
        featuresMastersLists.add(featuresMastersList);
        roleMaster.setFeaturesMastersLists(featuresMastersLists);

        List<String> functionMasterDTOS = new ArrayList<>();
        List<FunctionMastersList> functionMastersLists = new ArrayList<>();
        FunctionMastersList functionMastersList = new FunctionMastersList();
        functionMastersList.setFunctionMasters(functionMasterDTOS);
        functionMastersLists.add(functionMastersList);
        roleMaster.setFunctionMastersLists(functionMastersLists);


        modelAndView.addObject("role", roleMaster);
        modelAndView.addObject("groups",groupMasters);
        modelAndView.addObject("features",featuresMasters);
        modelAndView.addObject("functions",functionMasterList);
        modelAndView.addObject("featuresMaster",new FeaturesMastersList());
        modelAndView.addObject("functionsMaster", new FunctionMastersList());
        modelAndView.setViewName("auth/role/create");
        return modelAndView;
    }

    @RequestMapping(value = "/role/create", method = RequestMethod.POST)
    public String createRole(ModelAndView modelAndView, @Valid RoleMasterDTO roleMaster,BindingResult bindingResult, HttpServletRequest request) throws RecordNotFoundException {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("auth/role/create");
        } else {
            RoleMaster roleMaster1 = new RoleMaster();
            roleMaster1.setPublish(true);
            roleMaster1.setRoleName(roleMaster.getRoleName());
            roleMaster1.setRoleDescription(roleMaster.getRoleDescription());
            roleMasterService.save(roleMaster1);
            int i =0;
            for (FeaturesMastersList featuresMastersList: roleMaster.getFeaturesMastersLists()) {
                int order = 1;
                for(String featureId: featuresMastersList.getFeaturesMasters()) {
                    FeaturesMaster featuresMaster = featuresMasterService.getById(Long.parseLong(featureId));
                    if(featuresMaster != null) {
                        RoleFeatureMapping roleFeatureMapping = new RoleFeatureMapping();
                        roleFeatureMapping.setFeaturesMaster(featuresMaster);
                        roleFeatureMapping.setFunctionId(featuresMaster.getFunctionMaster().getId());
                        roleFeatureMapping.setRoleMaster(roleMaster1);
                        roleFeatureMapping.setPublish(true);
                        roleFeatureMapping.setFunctionId(Long.parseLong(roleMaster.getFunctionMastersLists().get(i).getFunctionMasters().get(0)));
                        roleFeatureMapping.setItemOrder(order);
                        roleFeatureMappingService.save(roleFeatureMapping);
                        order++;
                    }
                }
                i++;
            }
            for (String group: roleMaster.getGroupRoleMappings()) {
                GroupRoleMapping groupRoleMapping = new GroupRoleMapping();
                GroupMaster groupMaster = groupMasterService.getById(Long.parseLong(group));
                groupRoleMapping.setGroupMaster(groupMaster);
                groupRoleMapping.setRoleMaster(roleMaster1);
                groupRoleMappingService.save(groupRoleMapping);
            }
            modelAndView.addObject("confirmationMessage", "Role has been created");
            modelAndView.setViewName("auth/role/list");
        }
        return "redirect:/role/list";
    }

    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    @Transactional
    public String updateRole(ModelAndView modelAndView, @Valid RoleMasterDTO roleMaster,BindingResult bindingResult, HttpServletRequest request) throws RecordNotFoundException {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("auth/role/update");
        } else {
            RoleMaster roleMaster1 = new RoleMaster();
            roleMaster1.setId(roleMaster.getId());
            roleMaster1.setPublish(roleMaster.getPublish());
            roleMaster1.setRoleName(roleMaster.getRoleName());
            roleMaster1.setRoleDescription(roleMaster.getRoleDescription());
            roleMasterService.save(roleMaster1);
            int i = 0;
            roleFeatureMappingService.deleteAllFeatureByRole(roleMaster1.getId());
            if (roleMaster.getFeaturesMastersLists() != null) {
                for (FeaturesMastersList featuresMastersList : roleMaster.getFeaturesMastersLists()) {
                    int order = 1;
                    if (featuresMastersList.getFeaturesMasters() != null) {
                        for (String featureId : featuresMastersList.getFeaturesMasters()) {
                            FeaturesMaster featuresMaster = featuresMasterService.getById(Long.parseLong(featureId));
                            if (featuresMaster != null) {
                                RoleFeatureMapping roleFeatureMapping = new RoleFeatureMapping();
                                roleFeatureMapping.setFeaturesMaster(featuresMaster);
                                roleFeatureMapping.setFunctionId(featuresMaster.getFunctionMaster().getId());
                                roleFeatureMapping.setRoleMaster(roleMaster1);
                                roleFeatureMapping.setPublish(true);
                                roleFeatureMapping.setFunctionId(Long.parseLong(roleMaster.getFunctionMastersLists().get(i).getFunctionMasters().get(0)));
                                roleFeatureMapping.setItemOrder(order);
                                roleFeatureMappingService.save(roleFeatureMapping);
                                order++;
                            }
                        }
                    }
                    i++;
                }
            }

            groupRoleMappingService.deleteAllByRole(roleMaster1.getId());
            for (String group: roleMaster.getGroupRoleMappings()) {
                GroupRoleMapping groupRoleMapping = new GroupRoleMapping();
                GroupMaster groupMaster = groupMasterService.getById(Long.parseLong(group));
                groupRoleMapping.setGroupMaster(groupMaster);
                groupRoleMapping.setRoleMaster(roleMaster1);
                groupRoleMappingService.save(groupRoleMapping);
            }
            modelAndView.addObject("confirmationMessage", "Role has been update successfully!!!");
            modelAndView.setViewName("auth/role/list");
        }
        return "redirect:/role/list";
    }

    @RequestMapping(path = {"/role/edit", "/role/edit/{id}"})
    public ModelAndView editRole(ModelAndView model, @PathVariable("id") Optional<Long> id, BindingResult bindingResult) throws RecordNotFoundException
    {
        if(bindingResult.hasErrors()) {
            model.setViewName("auth/role/edit");
        }
        RoleMasterDTO roleMasterDTO = new RoleMasterDTO();
        if (id.isPresent()) {
            RoleMaster entity = roleMasterService.getById(id.get());
            /*Feature Function DTO*/
            List<FeatureFunctionDTO> featureFunctionDTOS = new ArrayList<>();

            List<FeaturesMastersList> featuresMastersLists = new ArrayList<>();
            List<FunctionMastersList> functionMastersLists = new ArrayList<>();
            Map<Integer,List<FeaturesMaster>> map = new HashMap<>();
            if (entity != null) {
                roleMasterDTO.setId(entity.getId());
                roleMasterDTO.setPublish(entity.getPublish());
                roleMasterDTO.setRoleDescription(entity.getRoleDescription());
                roleMasterDTO.setRoleName(entity.getRoleName());
                List<Long> roleFeatureMappings = roleFeatureMappingService.getAllByRoleAndFunctionGroup(entity.getId());
                Integer count = 0;
                for (Long functionId: roleFeatureMappings) {
                    /*Feature Function DTO*/
                    List<FeaturesMaster> list1= new ArrayList<>();
                    list1 = featuresMasterService.listOfFeaturesByFunction(functionId);
                    //list.addAll(list1);
                    map.put(count,list1);
                    count++;
                    FeatureFunctionDTO featureFunctionDTO = new FeatureFunctionDTO();
                    featureFunctionDTO.setFunctionMaster(functionId.toString());
                    List<String> features = new ArrayList<>();


                    FunctionMastersList functionMastersList = new FunctionMastersList();
                    List<String> functionIds = new ArrayList<>();
                    functionIds.add(functionId.toString());
                    functionMastersList.setFunctionMasters(functionIds);
                    List<RoleFeatureMapping> roleFeatureMappings1 = roleFeatureMappingService.getAllFeatureByFunctionAndRole(functionId,entity.getId());
                    List<String> featureIds = new ArrayList<>();
                    FeaturesMastersList featuresMastersList = new FeaturesMastersList();
                    for (RoleFeatureMapping roleFeatureMapping: roleFeatureMappings1) {
                        featureIds.add(roleFeatureMapping.getFeaturesMaster().getId().toString());
                        features.add(roleFeatureMapping.getFeaturesMaster().getId().toString());
                    }
                    /*Feature Function DTO*/
                    featureFunctionDTO.setFeatureMasters(features);
                    featureFunctionDTOS.add(featureFunctionDTO);


                    featuresMastersList.setFeaturesMasters(featureIds);
                    featuresMastersLists.add(featuresMastersList);
                    functionMastersLists.add(functionMastersList);
                }

                roleMasterDTO.setFunctionMastersLists(functionMastersLists);
                roleMasterDTO.setFeaturesMastersLists(featuresMastersLists);
                List<String> groupList = new ArrayList<>();
                for (GroupRoleMapping groupRoleMapping : entity.getGroupRoleMappings()) {
                    groupList.add(groupRoleMapping.getGroupMaster().getId().toString());
                }
                roleMasterDTO.setGroupRoleMappings(groupList);
            }
            model.addObject("featuresMaster",featuresMastersLists);
            model.addObject("functionsMaster",functionMastersLists);
            model.addObject("featureFunction",featureFunctionDTOS);

            roleMasterDTO.setCount(featureFunctionDTOS.size());
            List<GroupMaster> groupMasters = groupMasterService.listOfGroups();
            List<FunctionMaster> functionMasterList = functionMasterService.listOfFunctions();
            List<FeaturesMaster> featuresMasters = featuresMasterService.listOfFeatures();
            model.addObject("groups",groupMasters);
            model.addObject("features",featuresMasters);
            model.addObject("features1",map);
            model.addObject("functions",functionMasterList);
            model.addObject("role", roleMasterDTO);
            model.setViewName("auth/role/edit");
        } else {
            model.addObject("role", new OwnerDesignation());
            model.setViewName("auth/role/edit");
        }
        return model;
    }

    @RequestMapping(value = "/role/list", method = RequestMethod.GET)
    public ModelAndView listRole(ModelAndView modelAndView) {
        List<RoleMaster> roleMasters = roleMasterService.listOfAllRoles();
        modelAndView.addObject("roles",roleMasters);
        modelAndView.setViewName("auth/role/list");
        return modelAndView;
    }

    @RequestMapping(value = "/role/delete/{id}",method = RequestMethod.GET)
    public ModelAndView deleteRole(ModelAndView modelAndView, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException{
        List<UserRoleMapping> userRoleMappings = userRoleMappingService.findAllByUserRole(id.get());
        if(userRoleMappings.size() == 0) {
            roleMasterService.deleteRole(id.get());
            modelAndView.addObject("confirmationMessage", "Role has been deleted!!");
            List<RoleMaster> roleMasters = roleMasterService.listOfAllRoles();
            modelAndView.addObject("roles", roleMasters);
            modelAndView.setViewName("auth/role/list");
            return modelAndView;
        } else  {
            modelAndView.addObject("errorMessage", "You can't delete this role!!!");
            List<RoleMaster> roleMasters = roleMasterService.listOfAllRoles();
            modelAndView.addObject("roles", roleMasters);
            modelAndView.setViewName("auth/role/list");
            return modelAndView;
        }
    }
    @RequestMapping(value = "/api/role/deleteRole/{id}", method = RequestMethod.GET)
    public @ResponseBody  String deleteRoleById(@PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException {
        List<UserRoleMapping> userRoleMappings = userRoleMappingService.findAllByUserRole(id.get());
        if(userRoleMappings.size() != 0) {
            roleMasterService.deleteRole(id.get());
            List<RoleMaster> roleMasters = roleMasterService.listOfAllRoles();
            return" 0 Role has been deleted!!";
        } else {
            List<RoleMaster> roleMasters = roleMasterService.listOfAllRoles();
            return "1 Role has been deleted!!";
        }

    }
}
