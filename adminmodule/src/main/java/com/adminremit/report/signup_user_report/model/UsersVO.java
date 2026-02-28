package com.adminremit.report.signup_user_report.model;

import java.util.Date;
import java.util.Set;
import com.adminremit.masters.models.Countries;
import com.adminremit.operations.model.Role;
import com.adminremit.user.model.Users;

public class UsersVO {
	private Long id;
    private  String email;
    private  String password;
    private   boolean active = false;
    private String ipAddress;
    private String type;
    private Set<Role> roles;
    private Countries countries;
    private Boolean publish = true;
    private Date createAt = new Date();
    private Date updateAt = new Date();
    private String createdBy;
    private String updatedBy;
    private boolean isDeleted;
	public UsersVO(Users pd) {
		super();
		this.id = pd.getId();
		this.email = pd.getEmail();
		this.password =pd.getPassword();
		this.active = pd.isActive();
		this.ipAddress = pd.getIpAddress();
		this.type = pd.getType();
		this.roles = pd.getRoles() ;
		this.countries = pd.getCountries();
		this.publish = pd.getPublish();
		this.createAt = pd.getCreateAt();
		this.updateAt = pd.getUpdateAt();
		this.createdBy = pd.getCreatedBy();
		this.updatedBy = pd.getUpdatedBy();
		this.isDeleted = pd.isDeleted();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public Countries getCountries() {
		return countries;
	}
	public void setCountries(Countries countries) {
		this.countries = countries;
	}
	public Boolean getPublish() {
		return publish;
	}
	public void setPublish(Boolean publish) {
		this.publish = publish;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}