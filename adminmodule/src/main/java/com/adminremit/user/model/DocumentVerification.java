package com.adminremit.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="document_details")
public class DocumentVerification {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long id;

    @Column(name = "nationality")
    String nationality;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "issuing_country")
    String issuingCountry;

    @Column(name = "gender")
    String gender;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "middle_name")
    String middleName;

    @Column(name = "document_type")
    String documentType;

    @Column(name = "document_no")
    String documentNumber;

    @Column(name = "date_of_expiry")
    String dateOfExpiry;

    @Column(name = "date_of_birth")
    String dateOfBirth;

    @Column(name = "street_address")
    String streetAddress;

    @Column(name = "state")
    String state;

    @Column(name = "postalcode")
    String postalCode;

    @Column(name = "country")
    String country;

    @Column(name = "city")
    String city;

    @Column(name = "result")
    String 	result;

    @Column(name = "status")
    String  status;

    @Column(name = "sub_result")
    String  sub_result;

    @Column(name = "session_id")
    String sessionid;

    @Column(name = "user_id")
    String  userId;

    @Column(name = "app_id")
    String applicationId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIssuingCountry() {
        return issuingCountry;
    }

    public void setIssuingCountry(String issuingCountry) {
        this.issuingCountry = issuingCountry;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDateOfExpiry() {
        return dateOfExpiry;
    }

    public void setDateOfExpiry(String dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSub_result() {
        return sub_result;
    }

    public void setSub_result(String sub_result) {
        this.sub_result = sub_result;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getSessionid() {
        return sessionid;
    }

    public String getUserId() {
        return userId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
