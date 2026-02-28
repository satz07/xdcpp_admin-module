package com.adminremit.masters.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adminremit.masters.enums.UserType;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.MarginMaster;
import com.adminremit.masters.models.PaymentReceiveMode;

@Repository
public interface MarginRepository extends JpaRepository<MarginMaster,Long> {

    @Query("SELECT u FROM MarginMaster u WHERE u.sendCurrencyCode = ?1 and u.receiveCurrencyCode = ?2 and u.paymentCode = ?3 and u.publish=true " +
            "and u.rangeFrom<?4 and u.rangeTo>?4 and u.userType= ?5")

    public MarginMaster getMarginMasterByPaymentCode(Currencies from, Currencies to, PaymentReceiveMode paymentCode, BigDecimal amt, UserType userType);



}
