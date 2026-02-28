package com.adminremit.auth.controllers;

import com.adminremit.auth.dto.GroupMasterDTO;
import com.adminremit.auth.dto.GroupRolesDTO;
import com.adminremit.auth.dto.PasswordDTO;
import com.adminremit.auth.dto.UserDTO;
import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.*;
import com.adminremit.auth.service.*;
import com.adminremit.common.service.EmailService;
import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.LocationMaster;
import com.adminremit.masters.service.CountriesService;
import com.adminremit.masters.service.LocationMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class UserController implements WebMvcConfigurer {

    @Autowired
    private UserService userService;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private RoleMasterService roleMasterService;

    @Autowired
    private GroupMasterService groupMasterService;

    @Autowired
    private CountriesService countriesService;

    @Autowired
    private LocationMasterService locationMasterService;

    @Autowired
    private GroupUserMappingService groupUserMappingService;

    @Autowired
    private UserRoleMappingService userRoleMappingService;

    @Autowired
    private EmailService emailService;

    @Value("${reset.password.url}")
    private String resetPasswordURL;

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView, UserDTO userDTO) {
        List<GroupMasterDTO> groupMasterDTOS = new ArrayList<>();
        List<Partner> partners = partnerService.listOfPartners();
        List<GroupMaster> groupMasters = groupMasterService.listOfGroups();
        for (GroupMaster groupMaster: groupMasters) {
            GroupMasterDTO groupMasterDTO = new GroupMasterDTO();
            groupMasterDTO.setId(groupMaster.getId());
            groupMasterDTO.setGroupName(groupMaster.getGroupName());
            groupMasterDTOS.add(groupMasterDTO);
        }
        List<RoleMaster> roleMasters = roleMasterService.listOfRoles();
        List<Countries> countries = countriesService.listOfCountriesDialingCode();
        List<LocationMaster> locationMasters = locationMasterService.listOfLocation();
        modelAndView.addObject("locationMasters", locationMasters);
        modelAndView.addObject("countries",countries);
        modelAndView.addObject("partners",partners);
        modelAndView.addObject("user",userDTO);
        modelAndView.addObject("roles",roleMasters);
        modelAndView.addObject("groups",groupMasterDTOS);
        modelAndView.addObject("groupRolesDTOS",new GroupRolesDTO());

        modelAndView.setViewName("auth/user/create");
        return modelAndView;
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String createUser(@Valid UserDTO userDTO, BindingResult bindingResult, Model modelAndView) throws RecordNotFoundException {
        if (userDTO.getPartner() == null) {
            modelAndView.addAttribute("errorMessage", "Oops!  Select Partner.");
            List<Partner> partners = partnerService.listOfPartners();
            modelAndView.addAttribute("partners",partners);
            modelAndView.addAttribute("user",userDTO);
            return  "auth/user/create";
        }
        if(userDTO.getEmail() !=null) {
            User user = userService.findUserByEmailIsDeleted(userDTO.getEmail());
            if(user != null && user.getId() != userDTO.getId()) {
                modelAndView.addAttribute("errorMessage", "Oops!  Email already taken.");
                List<Partner> partners = partnerService.listOfPartners();
                modelAndView.addAttribute("partners",partners);
                modelAndView.addAttribute("user",userDTO);
                List<GroupMaster> groupMasters = groupMasterService.listOfGroups();
                List<GroupMasterDTO> groupMasterDTOS = new ArrayList<>();
                for (GroupMaster groupMaster: groupMasters) {
                    GroupMasterDTO groupMasterDTO = new GroupMasterDTO();
                    groupMasterDTO.setId(groupMaster.getId());
                    groupMasterDTO.setGroupName(groupMaster.getGroupName());
                    groupMasterDTOS.add(groupMasterDTO);
                }
                List<RoleMaster> roleMasters = roleMasterService.listOfRoles();
                List<Countries> countries = countriesService.listOfCountriesDialingCode();
                List<LocationMaster> locationMasters = locationMasterService.listOfLocation();
                modelAndView.addAttribute("locationMasters", locationMasters);
                modelAndView.addAttribute("countries",countries);
                modelAndView.addAttribute("user",userDTO);
                modelAndView.addAttribute("roles",roleMasters);
                modelAndView.addAttribute("groups",groupMasterDTOS);
                modelAndView.addAttribute("groupRolesDTOS",new GroupRolesDTO());
                return  "auth/user/create";
            }
        }
        if (bindingResult.hasErrors()) {
            List<Partner> partners = partnerService.listOfPartners();
            List<GroupMaster> groupMasters = groupMasterService.listOfGroups();
            List<GroupMasterDTO> groupMasterDTOS = new ArrayList<>();
            for (GroupMaster groupMaster: groupMasters) {
                GroupMasterDTO groupMasterDTO = new GroupMasterDTO();
                groupMasterDTO.setId(groupMaster.getId());
                groupMasterDTO.setGroupName(groupMaster.getGroupName());
                groupMasterDTOS.add(groupMasterDTO);
            }
            List<RoleMaster> roleMasters = roleMasterService.listOfRoles();
            List<Countries> countries = countriesService.listOfCountriesDialingCode();
            List<LocationMaster> locationMasters = locationMasterService.listOfLocation();
            modelAndView.addAttribute("locationMasters", locationMasters);
            modelAndView.addAttribute("countries",countries);
            modelAndView.addAttribute("partners",partners);
            modelAndView.addAttribute("user",userDTO);
            modelAndView.addAttribute("roles",roleMasters);
            modelAndView.addAttribute("groups",groupMasterDTOS);
            modelAndView.addAttribute("groupRolesDTOS",new GroupRolesDTO());
            return "auth/user/create";
        } else {
            User user = userService.createUser(userDTO);
            if(userDTO.getId() == null) {
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setTo(user.getEmail());
                simpleMailMessage.setSubject("FYNTE BOLT: Account Created Successfully.");
                StringBuffer link=new StringBuffer( "Your account created successfully click here to set password "+resetPasswordURL+user.getResetToken());
                link.append(" ");
                link.append("Your OTP:"+user.getPasswordOTP());
                simpleMailMessage.setText(link.toString());
                emailService.send(simpleMailMessage);
            }
            modelAndView.addAttribute("confirmationMessage", "Product has been created");
            List<User> userList = userService.listOfAdminUsers();
            modelAndView.addAttribute("users", userList);
        }
        return "redirect:/user/list";
    }

    @RequestMapping(path = {"/user/edit", "/user/edit/{id}"})
    public ModelAndView editDesignation(ModelAndView model, @PathVariable("id") Optional<Long> id, BindingResult bindingResult) throws RecordNotFoundException {
        if (bindingResult.hasErrors()) {
            List<User> userList = userService.listOfAdminUsers();
            model.addObject("users", userList);
            model.setViewName("auth/user/edit");
        }
        if (id.isPresent()) {
            User entity = userService.getByUserId(id.get());
            List<GroupRolesDTO> groupRoleDTOS = new ArrayList<>();
            List<Long> groupIds = userRoleMappingService.getAllByUserId(entity.getId());
            for (Long groupId: groupIds) {
                GroupRolesDTO groupRolesDTO = new GroupRolesDTO();
                groupRolesDTO.setGroupMaster(groupId.toString());
                List<UserRoleMapping> userRoleMappings = userRoleMappingService.findAllByGroupIdAndUserRole(groupId,entity.getId());
                List<String> roleIds = new ArrayList<>();
                for(UserRoleMapping userRoleMapping: userRoleMappings) {
                    roleIds.add(userRoleMapping.getRole().getId().toString());
                }
                groupRolesDTO.setRoleMaster(roleIds);
                groupRoleDTOS.add(groupRolesDTO);
            }

            UserDTO userDTO = userService.createUserDTO(entity);
            userDTO.setGroupRolesDTOS(groupRoleDTOS);
            List<Partner> partners = partnerService.listOfPartners();
            List<GroupMaster> groupMasters = groupMasterService.listOfGroups();
            List<GroupMasterDTO> groupMasterDTOS = new ArrayList<>();
            for (GroupMaster groupMaster: groupMasters) {
                GroupMasterDTO groupMasterDTO = new GroupMasterDTO();
                groupMasterDTO.setId(groupMaster.getId());
                groupMasterDTO.setGroupName(groupMaster.getGroupName());
                groupMasterDTOS.add(groupMasterDTO);
            }
            List<RoleMaster> roleMasters = roleMasterService.listOfRoles();
            List<LocationMaster> locationMasters = locationMasterService.listOfLocation();
            List<Countries> countries = countriesService.listOfCountriesDialingCode();
            model.addObject("countries",countries);
            model.addObject("user", userDTO);
            model.addObject("partners",partners);
            model.addObject("locationMasters", locationMasters);
            model.addObject("roles",roleMasters);
            model.addObject("groups",groupMasterDTOS);
            model.addObject("groupRolesDTOS",groupRoleDTOS);
            model.setViewName("auth/user/edit");
        } else {
            List<Partner> partners = partnerService.listOfPartners();
            model.addObject("partners",partners);
            model.addObject("user", new UserDTO());
            model.setViewName("auth/user/edit");
        }
        return model;
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public ModelAndView listUser(ModelAndView modelAndView) {
        List<User> userList = userService.listOfAdminUsers();
        modelAndView.addObject("users", userList);
        modelAndView.setViewName("auth/user/list");
        return modelAndView;
    }

    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String deleteUser(@PathVariable("id") Optional<Long> id) throws RecordNotFoundException {
        User user = userService.getByUserId(id.get());
        if(user !=null){
            userService.deleteUser(id.get());
            return " 0 user has been deleted!!";
        }else{
            return " 1 user has been deleted!!";
        }
    }

    @RequestMapping(value = "/reset/password/{token}",method = RequestMethod.GET)
    public ModelAndView setPassword(ModelAndView modelAndView, @PathVariable("token") Optional<String> token) throws RecordNotFoundException {
        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setToken(token.get());
        modelAndView.addObject("passwordDTO",passwordDTO);
        modelAndView.setViewName("auth/user/resetpassword");
        return modelAndView;
    }

    @RequestMapping(value = "/create/password", method = RequestMethod.POST)
    private ModelAndView createPassword(@Valid PasswordDTO passwordDTO, BindingResult bindingResult, ModelAndView modelAndView) throws RecordNotFoundException{
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("passwordDTO",passwordDTO);
            modelAndView.addObject("errorMessage", "Password is mandatory");
            modelAndView.setViewName("auth/user/resetpassword");
        } else {
            User user = userService.getUserByToken(passwordDTO.getToken());
            if(user == null) {
                modelAndView.addObject("errorMessage", "Token is not valid!!");
                modelAndView.setViewName("auth/user/resetpassword");
                return modelAndView;
            }
            if(!user.getPasswordOTP().equals(passwordDTO.getOtp().toString())) {
                modelAndView.addObject("errorMessage", "OTP is not matched!!");
                modelAndView.setViewName("auth/user/resetpassword");
                return modelAndView;
            } else {
                UserDTO userDTO = new UserDTO();
                userDTO.setResetToken(passwordDTO.getToken());
                userDTO.setPassword(passwordDTO.getPassword());
                userService.createPassword(userDTO);
                modelAndView.setViewName("auth/user/success");
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "/forgetpassword",method = RequestMethod.GET)
    public ModelAndView forgetPassword(ModelAndView modelAndView, PasswordDTO passwordDTO) throws RecordNotFoundException {
        modelAndView.addObject("passwordDTO",passwordDTO);
        modelAndView.setViewName("auth/user/forgetpassword");
        return modelAndView;
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    private ModelAndView password(@Valid PasswordDTO passwordDTO, BindingResult bindingResult, ModelAndView modelAndView) throws RecordNotFoundException{
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("passwordDTO",passwordDTO);
            modelAndView.addObject("errorMessage", "Password is mandatory");
            modelAndView.setViewName("auth/user/forgetpassword");
            return modelAndView;
        } else {
            User user = null;
            if(passwordDTO.getEmail() != null && passwordDTO.getPhoneNumber() != null) {
                user = userService.getUserByEmailPhone(passwordDTO.getEmail(),passwordDTO.getPhoneNumber());
                if(user == null) {
                    modelAndView.addObject("passwordDTO",passwordDTO);
                    modelAndView.addObject("errorMessage", "Given credentials are not matched!!!");
                    modelAndView.setViewName("auth/user/forgetpassword");
                    return modelAndView;
                } else {
                    user.setResetToken(UUID.randomUUID().toString());
                    user.setPasswordOTP(userService.getOTP().toString());
                    userService.updateToken(user);
                    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                    simpleMailMessage.setTo(user.getEmail());
                    simpleMailMessage.setSubject("Andremitt Reset Password Link");
                    StringBuffer link=new StringBuffer( "Please click the below link to reset password "+resetPasswordURL+user.getResetToken());
                    link.append(" ");
                    link.append("Your OTP:"+user.getPasswordOTP());
                    simpleMailMessage.setText(link.toString());
                    emailService.send(simpleMailMessage);
                    modelAndView.setViewName("auth/user/passwordsuccess");
                }
            }
        }
        return modelAndView;
    }

}
