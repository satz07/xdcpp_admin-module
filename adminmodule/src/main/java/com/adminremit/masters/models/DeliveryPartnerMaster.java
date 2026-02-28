package com.adminremit.masters.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "delivery_partner_master")
@Audited
@Getter
@Setter
public class DeliveryPartnerMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "partner_name")
    @NotEmpty(message = "Partner name is mandatory")
    private String partnerName;
    
    @ManyToOne
    @JoinColumn(name = "operting_coutry")
    private Countries operatingCountry;
    
    @Column(name = "partner_location")
    private String partnerLocation;
    
    @ManyToOne
    @JoinColumn(name = "payment_mode")
    private PaymentReceiveMode paymentMode;
    
    @Column(name = "partner_code")
    private String PartnerCode;
    
    @Column(name = "partner_limits_min")
    private String partnerLimitsMin;
    
    @Column(name = "partner_limits_max")
    private String partnerLimitsMax;
    
    
    
    
    
    
 
    
    
    
    
    

}
