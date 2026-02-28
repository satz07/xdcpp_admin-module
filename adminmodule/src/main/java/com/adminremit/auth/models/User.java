package com.adminremit.auth.models;

import com.adminremit.common.validator.UniqueUsername;
import com.adminremit.masters.models.LocationMaster;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "admin_users")
@Audited
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "First name is mandatory")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Last name is mandatory")
    private String lastName;

    @Column(name = "email",unique = true)
    //@UniqueUsername
    @NotEmpty(message = "Email is mandatory")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "publish",columnDefinition="boolean default false")
    private Boolean publish;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<GroupUserMapping> groupUserMappings;

    @OneToMany(mappedBy = "userRole", fetch = FetchType.LAZY)
    private List<UserRoleMapping> userRoleMappings;

    @Column(name = "dob")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @Column(name = "dialing_cide")
    private String dialingCode;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationMaster locationMaster;

    @Column(name = "mobile_access")
    private Boolean isMobileAccess;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "password_otp")
    private String passwordOTP;

    @Column(name = "is_delete",columnDefinition="boolean default false")
    private Boolean isDeleted;

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

    public Boolean isPublish() {
        return publish;
    }

    public void setIsPublish(Boolean publish) {
        this.publish = publish;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public List<GroupUserMapping> getGroupUserMappings() {
        return groupUserMappings;
    }

    public void setGroupUserMappings(List<GroupUserMapping> groupUserMappings) {
        this.groupUserMappings = groupUserMappings;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<UserRoleMapping> getUserRoleMappings() {
        return userRoleMappings;
    }

    public void setUserRoleMappings(List<UserRoleMapping> userRoleMappings) {
        this.userRoleMappings = userRoleMappings;
    }

    public Boolean getIsPublish() {
        return publish;
    }

    public String getDialingCode() {
        return dialingCode;
    }

    public void setDialingCode(String dialingCode) {
        this.dialingCode = dialingCode;
    }

    public LocationMaster getLocationMaster() {
        return locationMaster;
    }

    public void setLocationMaster(LocationMaster locationMaster) {
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
