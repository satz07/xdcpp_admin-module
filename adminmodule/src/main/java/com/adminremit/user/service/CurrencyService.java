package com.adminremit.user.service;

import java.util.Map;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.models.GlobalLimitMaster;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.user.dto.CurrencyConvert;
import com.adminremit.user.model.CurrencyCalculatorModel;


public interface CurrencyService {
	
	public CurrencyCalculatorModel getCurrencyCalculatorDetails(CurrencyConvert currencyConvert) throws RecordNotFoundException;
	public UserCalculatorMapping getUserCurrencyCalcMapping(String uid);
	public Map<String, Float> getCurrency(String currency);
	public UserCalculatorMapping saveUserCurrencyCalcMapping(String uid,CurrencyCalculatorModel currencyCalculatorModel);
	public UserCalculatorMapping updateCurrencyCalcMapping(UserCalculatorMapping userCalculatorMapping);
    public GlobalLimitMaster getLimits();

}
