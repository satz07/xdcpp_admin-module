package com.adminremit.masters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.masters.models.DeliveryPartnerMaster;
@Repository
public interface DeliveryPartnerMasterRepository extends JpaRepository<DeliveryPartnerMaster,Long> {
	
	

}
