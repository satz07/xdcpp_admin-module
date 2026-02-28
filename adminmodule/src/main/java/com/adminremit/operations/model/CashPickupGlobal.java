package com.adminremit.operations.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;

import com.adminremit.masters.models.Countries;
import com.adminremit.masters.models.DeliveryPartnerMaster;
import com.adminremit.masters.models.PaymentReceiveMode;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cashpickup_global")
@Getter
@Setter
public class CashPickupGlobal {

	
	  @Id
	  @GeneratedValue(strategy=GenerationType.AUTO) 
	  private Long id;
	  
	  @Column(name="agent_name") 
	  private String agentName;
	  
	  
	  @Column(name = "delivery_partner_code")
	  private String deliveryPartnerCode;
	  
	  @Column(name="branch_name") 
	  private String branchName;
	  
	  @Column(name="address") 
	  private String address;
	  
	  @Column(name = "state") 
	  private String state;
	  
	  @Column(name="district") 
	  private String district;
	  
	  @Column(name="status") 
	  private String status="active";
	  
	   @Column(name = "coutry_code")
	   private String countryCode;
	  
	  @Column(name="createdon")
	  @CreationTimestamp 
	  private Date createdon=new Date();
	  
	  
	 
	
}
