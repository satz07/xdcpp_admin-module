package com.adminremit.auth.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adminremit.auth.dto.GroupRolesDTO;
import com.adminremit.auth.dto.UserDTO;
import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.User;
import com.adminremit.auth.models.UserRoleMapping;
import com.adminremit.auth.repository.UserRepository;
import com.adminremit.masters.service.LocationMasterService;
import com.adminremit.user.model.Users;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private RoleMasterService roleMasterService;

    @Autowired
    private UserRoleMappingService userRoleMappingService;

    @Autowired
    private LocationMasterService locationMasterService;

    public User save(User user) {
        if (user.getId() == null) {
            //user.setPassword(passwordEncryption(user.getPassword()));
            user.setIsPublish(true);
            user = userRepository.save(user);
            return user;
        } else {
            Optional<User> user1 = userRepository.findById(user.getId());
            if (user1.isPresent()) {
                User updateUser = user1.get();
                updateUser.setEmail(user.getEmail());
                updateUser.setFirstName(user.getFirstName());
                updateUser.setLastName(user.getLastName());
                updateUser.setPartner(user.getPartner());
                updateUser.setDob(user.getDob());
                updateUser.setPhoneNumber(user.getPhoneNumber());
                updateUser.setIsPublish(user.getIsPublish());
                //updateUser.setPassword(passwordEncryption(user.getPassword()));
                return userRepository.save(updateUser);
            }
            {
                user = userRepository.save(user);
                return user;
            }
        }
    }

    public User findByEmail(String email) {
        User user = null;
        try {
            user = userRepository.findByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }


    public boolean isUsernameAlreadyInUse(String email) {
        boolean userInDb = true;
        if (userRepository.findByEmail(email) == null) {
            userInDb = false;
        }
        return userInDb;
    }

    public List<User> listOfAdminUsers() {
        List<User> userList = null;
        try {
            userList = userRepository.findAllByIsDeleted(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public User getByUserId(Long id) throws RecordNotFoundException {
        Optional<User> user = userRepository.findById(id);
        User user1 = null;
        if (user.isPresent()) {
            user1 = user.get();
        }
        return user1;
    }

    public void deleteUser(Long id) throws RecordNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setIsDeleted(true);
            userRepository.save(user1);
        }
    }

    public String passwordEncryption(String password) {
        int strength = 12;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength);
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        return encodedPassword;
    }

    public Integer getOTP() {
        Random r = new Random( System.currentTimeMillis() );
        return 10000 + r.nextInt(20000);
    }

    @Transactional
    public User createUser(UserDTO userDTO) throws RecordNotFoundException {
        User user = null;
        if(userDTO.getId() == null) {
            user = new User();
            user.setPasswordOTP(this.getOTP().toString());
            user.setResetToken(UUID.randomUUID().toString());
            user.setIsPublish(false);
        } else  {
            user = userRepository.getOne(userDTO.getId());
            user.setIsPublish(userDTO.getPublish());
        }
        user.setIsDeleted(false);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setDob(userDTO.getDob());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPartner(partnerService.getById(userDTO.getPartner()));
        user.setEmail(userDTO.getEmail());
        user.setIsMobileAccess(userDTO.getIsMobileAccess());
        user.setDialingCode(userDTO.getDialingCode());
        user.setLocationMaster(locationMasterService.getLocationById(userDTO.getLocationMaster()));
        this.save(user);
        userRoleMappingService.deleteRoleMapping(user.getId());
        for(GroupRolesDTO groupMasterDTO: userDTO.getGroupRolesDTOS()) {
            for(String roleId: groupMasterDTO.getRoleMaster()) {
                UserRoleMapping userRoleMapping = new UserRoleMapping();
                userRoleMapping.setUserRole(user);
                userRoleMapping.setGroupId(Long.parseLong(groupMasterDTO.getGroupMaster()));
                userRoleMapping.setRole(roleMasterService.getById(Long.parseLong(roleId)));
                userRoleMappingService.save(userRoleMapping);
            }
        }
        return user;
    }

    public UserDTO createUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setPartner(user.getPartner().getId());
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setDob(user.getDob());
        userDTO.setPublish(user.isPublish());
        userDTO.setIsMobileAccess(user.getIsMobileAccess());
        userDTO.setCount(user.getUserRoleMappings().size());
        if(user.getDialingCode() != null) {
            userDTO.setDialingCode(user.getDialingCode());
        }
        if(user.getLocationMaster() != null) {
            userDTO.setLocationMaster(user.getLocationMaster().getId());
        }
        return userDTO;
    }

    @Override
    public User createPassword(UserDTO userDTO) throws RecordNotFoundException {
        User user = null;
        try {
            user = userRepository.findByResetToken(userDTO.getResetToken());
            user.setPassword(passwordEncryption(userDTO.getPassword()));
            user.setIsPublish(true);
            user.setPasswordOTP("");
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUserByToken(String token) {
        User user = null;
        try {
            user = userRepository.findByResetToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUserByEmailPhone(String email, String phoneNumber) throws RecordNotFoundException {
        User user = null;
        try {
            user = userRepository.findByEmailAndPhoneNumber(email,phoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    
    @Override
    public Users getUserByEmail(String email) throws RecordNotFoundException {
        Users user = null;
        try {
            //user = userRepository.findByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User updateToken(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUserByPartner(Long partnerId) {
        List<User> userList = null;
        try {
            userList = userRepository.findAllByIsDeletedAndPartner(false,partnerService.getById(partnerId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public User findUserByEmailIsDeleted(String email) {
        User user = null;
        try {
            user = userRepository.findByEmailAndIsDeleted(email,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUserById(Long userId) {
        User user = null;
        try {
            user = userRepository.findById(userId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
