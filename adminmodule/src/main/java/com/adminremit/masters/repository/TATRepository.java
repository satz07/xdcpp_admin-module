package com.adminremit.masters.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adminremit.masters.models.TAT;

@Repository
public interface TATRepository extends JpaRepository<TAT,Long> {

    List<TAT> findAllByPublish(@Param("publish") boolean publish);

    List<TAT> findAllByPublishAndIsDeleted(@Param("publish") boolean publish,@Param("isDelete") boolean isDelete);
    @Query(value = "SELECT t FROM TAT t WHERE t.amountSlabFrom <= :sendingAmount and t.amountSlabTo >= :sendingAmount and t.disburseCCM.id= :receivingCurrencyCode and t.originCCM.id= :sendingCurrencyCode and t.paymentMode.id= :paymentModeType and t.receiveMode.id= :receivingModeType and t.userType= :userType and t.publish = true")
	public TAT getTatDetailsBySendingAndReceivingCountryAndUserType(@Param("sendingAmount") BigDecimal sendingAmount,
			 @Param("sendingCurrencyCode") long sendingCurrencyCode, @Param("receivingCurrencyCode") long receivingCurrencyCode, @Param("paymentModeType") long paymentModeType, @Param("receivingModeType") long receivingModeType, @Param("userType") String userType);

	List<TAT> findAllByIsDeleted(Boolean isDeleted);

}
