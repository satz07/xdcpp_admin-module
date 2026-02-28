package com.adminremit.user.model;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.adminremit.operations.model.AuditDetails;

@Entity
@Table(name="users_ip")
public class IPTagging extends AuditDetails {

	private static final long serialVersionUID = -4733057373188393637L;

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "uid")
    String uid;

    @Column(name = "longitude")
    String longitude;

    @Column(name = "latitude")
    String latitude;

    @Column(name = "city")
    String city;

    @Column(name = "timeZone")
    String timeZone;

    @Column(name = "ipAddress")
    String ipAddress;

    @Column(name = "region")
    String region;

    @Column(name = "region_code")
    String regionCode;

    @Column(name = "country")
    String country;

    @Column(name = "country_code")
    String country_code;

    @Column(name = "country_code3")
    String country_code3;

    @Column(name = "count")
    BigInteger count = new BigInteger("1");
    
    @Column(name = "ip_response", columnDefinition = "text")
    private String ipResponse;
    
    @Column(name = "area_name")
    private String areaName;
    
    @Column(name = "ip_type")
    private String ipType;

    @OneToMany(mappedBy="ipTagging")
    List<ComplianceStateDetails> complianceStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_code3() {
        return country_code3;
    }

    public void setCountry_code3(String country_code3) {
        this.country_code3 = country_code3;
    }

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<ComplianceStateDetails> getComplianceStatus() {
        return complianceStatus;
    }

    public void setComplianceStatus(List<ComplianceStateDetails> complianceStatus) {
        this.complianceStatus = complianceStatus;
    }

	public String getIpResponse() {
		return ipResponse;
	}

	public void setIpResponse(String ipResponse) {
		this.ipResponse = ipResponse;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getIpType() {
		return ipType;
	}

	public void setIpType(String ipType) {
		this.ipType = ipType;
	}	
}
