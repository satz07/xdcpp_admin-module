package com.adminremit.masters.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.adminremit.masters.models.TAT;

public interface TATService {

    void addTAT(TAT tat);
    List<TAT> listOfTATs();
    void deleteTAT(Long tatId);
    Optional<TAT> getTATById(Long tatId);
    List<TAT> listOfCheckerTATs();
    public void approveTAT(TAT tat);
    public void rejectTAT(TAT tat);
    public TAT getTatDetailsBySendingAndReceivingCountryAndUserType(BigDecimal sendingAmount, long sendingCurrencyCode, long receivingCurrencyCode, long paymentModeType, long receivingModeType, String userType);
	TAT getById(Long id);
}
