package com.adminremit.report.model.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "business_sub_activity")
public class BusinessSubActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "business_sub_activity")
    private String businessSubActivityName;
    
    @ManyToOne
    @JoinColumn(name = "business_activity_id")
    private BusinessActivityVo businessActivity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBusinessSubActivityName() {
		return businessSubActivityName;
	}

	public void setBusinessSubActivityName(String businessSubActivityName) {
		this.businessSubActivityName = businessSubActivityName;
	}

	public BusinessActivityVo getBusinessActivity() {
		return businessActivity;
	}

	public void setBusinessActivity(BusinessActivityVo businessActivity) {
		this.businessActivity = businessActivity;
	}	
}
