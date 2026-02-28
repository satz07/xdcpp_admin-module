package com.adminremit.masters.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.masters.controllers.TATController;
import com.adminremit.masters.dto.TATRequest;
import com.adminremit.masters.dto.TATResponse;
import com.adminremit.masters.exception.ValidationException;
import com.adminremit.masters.models.GlobalLimitMaster;
import com.adminremit.masters.models.TAT;
import com.adminremit.masters.repository.TATRepository;
import com.github.javaparser.utils.Log;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TATServiceImpl implements TATService {

    @Autowired
    TATRepository tatRepository;

    @Override
    public void addTAT(TAT tat) {
        tat.setPublish(false);
        if(tat.getId()==null) {
            tatRepository.save(tat);
        } else {
            Optional<TAT> optionalTAT = tatRepository.findById(tat.getId());
            if (optionalTAT.isPresent()) {
                tatRepository.save(tat);
            }
        }
    }
    @Override
    public void approveTAT(TAT tat) {
        
        Optional<TAT> optionalTAT = tatRepository.findById(tat.getId());
        if (optionalTAT.isPresent()) {
            tatRepository.save(tat);
        }
    }
    @Override
 public void rejectTAT(TAT tat) {
        
        Optional<TAT> optionalTAT = tatRepository.findById(tat.getId());
        if (optionalTAT.isPresent()) {
            tatRepository.save(tat);
        }
    }
    @Override
    public List<TAT> listOfTATs() {
       return tatRepository.findAllByIsDeleted(false);
    }

    @Override
    public List<TAT> listOfCheckerTATs() {

        //return tatRepository.findAllByPublish(false);
        return tatRepository.findAllByPublishAndIsDeleted(false,false);
    }

    public Optional<TAT> getTATById(Long tatId) {
        return tatRepository.findById(tatId);
    }

    @Override
    public void deleteTAT(Long id) {
        Optional<TAT> tatOptional = tatRepository.findById(id);
        TAT tat = tatOptional.get();
        if(!tatOptional.isPresent()) {
            throw new ValidationException("Specifies Request Not Found");
          
        }
        log.info("kgjkg");
        tat.setIsDeleted(true);
        tat.setPublish(false);
        log.info("deleted");
        tatRepository.save(tat);
    }

    private TATResponse mapToTATResponse(TAT tat) {
        TATResponse tatResponse = new TATResponse();
        tatResponse.setAmountSlabFrom(tat.getAmountSlabFrom());
        tatResponse.setAmountSlabTo(tat.getAmountSlabTo());
        tatResponse.setOriginCCM(tat.getOriginCCM().getCountryName());
        tatResponse.setPaymentMode(tat.getPaymentMode().getDescription());
        tatResponse.setPaymentHrs(tat.getPaymentHrs());
        tatResponse.setDisburseCCM(tat.getDisburseCCM().getCountryName());
        tatResponse.setReceiveMode(tat.getReceiveMode().getDescription());
        tatResponse.setReceiveHrs(tat.getReceiveHrs());
        tatResponse.setExpiryTime(tat.getExpiryTime());
        tatResponse.setCancelTime(tat.getCancelTime());
        return tatResponse;
    }

    private TAT mapToTAT(TATRequest tatRequest, TAT tat) {
        tat.setAmountSlabFrom(tatRequest.getAmountSlabFrom());
        tat.setAmountSlabTo(tatRequest.getAmountSlabTo());
        tat.setReceiveHrs(tatRequest.getReceiveHrs());
        tat.setExpiryTime(tatRequest.getExpiryTime());
        tat.setCancelTime(tatRequest.getCancelTime());
        return tat;
    }

	@Override
	public TAT getTatDetailsBySendingAndReceivingCountryAndUserType(BigDecimal sendingAmount, long sendingCurrencyCode,
			long receivingCurrencyCode, long paymentModeType, long receivingModeType, String userType) {
		return tatRepository.getTatDetailsBySendingAndReceivingCountryAndUserType(sendingAmount, sendingCurrencyCode, receivingCurrencyCode, paymentModeType, receivingModeType, userType);
	}
	@Override
	public TAT getById(Long id) {
		// TODO Auto-generated method stub
		 Optional<TAT> tat = tatRepository.findById(id);
	        TAT tat1 = null;
	        try {
	            if(tat.isPresent()) {
	            	tat1 = tat.get();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return tat1;
	    }
	}

