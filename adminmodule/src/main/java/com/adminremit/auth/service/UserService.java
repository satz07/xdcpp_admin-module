package com.adminremit.auth.service;

import java.util.List;

import com.adminremit.auth.dto.UserDTO;
import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.User;
import com.adminremit.common.service.BaseService;
import com.adminremit.user.model.Users;

public interface UserService extends BaseService<User> {
    public User findByEmail(String username);
    public boolean isUsernameAlreadyInUse(String email);
    public void deleteUser(Long id) throws RecordNotFoundException;
    public User getByUserId(Long id) throws RecordNotFoundException;
    public List<User> listOfAdminUsers();
    public String passwordEncryption(String password);
    public User createUser(UserDTO userDTO) throws RecordNotFoundException;
    public UserDTO createUserDTO(User user);
    public User createPassword(UserDTO userDTO) throws RecordNotFoundException;
    public User getUserByEmailPhone(String email, String  phoneNumber) throws RecordNotFoundException;
    public Users getUserByEmail(String email) throws RecordNotFoundException;
    public User updateToken(User user);
    public User getUserByToken(String token);
    public List<User> getAllUserByPartner(Long partnerId);
    public Integer getOTP();
    public User findUserByEmailIsDeleted(String email);
    public User getUserById(Long userId);
}
