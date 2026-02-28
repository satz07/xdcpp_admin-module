package com.adminremit.report.model.business;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "business_activity")
public class BusinessActivityVo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "business_activity")
    private String businessActivityName;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<BusinessSubActivity> businessSubActivity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	

	public String getBusinessActivityName() {
		return businessActivityName;
	}

	public void setBusinessActivityName(String businessActivityName) {
		this.businessActivityName = businessActivityName;
	}

	public List<BusinessSubActivity> getBusinessSubActivity() {
		return businessSubActivity;
	}

	public void setBusinessSubActivity(List<BusinessSubActivity> businessSubActivity) {
		this.businessSubActivity = businessSubActivity;
	}    
}
