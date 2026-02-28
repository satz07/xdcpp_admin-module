package com.adminremit.auth.dto;

import com.adminremit.auth.models.GroupUserMapping;
import com.adminremit.auth.models.Partner;
import com.adminremit.common.validator.UniqueUsername;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

public class UserDTO {

    private Long id;

    @NotEmpty(message = "First name is mandatory")
    private String firstName;

    @NotEmpty(message = "Last name is mandatory")
    private String lastName;

    //@UniqueUsername
    @NotEmpty(message = "Email is mandatory")
    private String email;

    private String password;

    private List<String> groupUserMappings;

    private List<String> userRoleMappings;

    private String phoneNumber;

    private Boolean publish;

    private Long partner;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    private List<GroupRolesDTO> groupRolesDTOS;

    private String dialingCode;

    private Long locationMaster;

    private Boolean isMobileAccess;

    private int count;

    private String resetToken;

    private String passwordOTP;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public Long getPartner() {
        return partner;
    }

    public void setPartner(Long partner) {
        this.partner = partner;
    }

    public List<String> getGroupUserMappings() {
        return groupUserMappings;
    }

    public void setGroupUserMappings(List<String> groupUserMappings) {
        this.groupUserMappings = groupUserMappings;
    }

    public List<String> getUserRoleMappings() {
        return userRoleMappings;
    }

    public void setUserRoleMappings(List<String> userRoleMappings) {
        this.userRoleMappings = userRoleMappings;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public List<GroupRolesDTO> getGroupRolesDTOS() {
        return groupRolesDTOS;
    }

    public void setGroupRolesDTOS(List<GroupRolesDTO> groupRolesDTOS) {
        this.groupRolesDTOS = groupRolesDTOS;
    }

    public String getDialingCode() {
        return dialingCode;
    }

    public void setDialingCode(String dialingCode) {
        this.dialingCode = dialingCode;
    }

    public Long getLocationMaster() {
        return locationMaster;
    }

    public void setLocationMaster(Long locationMaster) {
        this.locationMaster = locationMaster;
    }

    public Boolean isMobileAccess() {
        return isMobileAccess;
    }

    public void setIsMobileAccess(Boolean mobileAccess) {
        isMobileAccess = mobileAccess;
    }

    public Boolean getIsMobileAccess() {
        return isMobileAccess;
    }

    public Boolean getMobileAccess() {
        return isMobileAccess;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getPasswordOTP() {
        return passwordOTP;
    }

    public void setPasswordOTP(String passwordOTP) {
        this.passwordOTP = passwordOTP;
    }
}
