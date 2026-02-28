package com.adminremit.onfido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.onfido.model.OnfidoCheckApiResponse;

@Repository
public interface OnfidoCheckApiResponseRepository extends JpaRepository<OnfidoCheckApiResponse, Long> {
	
	OnfidoCheckApiResponse findByUserIdAndAppIdAndReportName(String userId, String appId, String reportName);

}
