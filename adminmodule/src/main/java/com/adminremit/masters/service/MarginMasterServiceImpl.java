package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.enums.MakerCheckerFunction;
import com.adminremit.masters.models.*;
import com.adminremit.masters.repository.MarginMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MarginMasterServiceImpl implements MarginMasterService {

	@Autowired
	private MarginMasterRepository marginMasterRepository;

	@Autowired
	private MakerAndCheckerService makerAndCheckerService;

	@Autowired
	private PaymentReceiveModeService paymentReceiveModeService;

	@Autowired
	private CountriesService countriesService;

	@Autowired
	private CurrenciesService currenciesService;

	@Autowired
	private FeeMasterService feeMasterService;

	public MarginMaster save(MarginMaster marginMaster) throws NoSuchFieldException {
		marginMaster.setPublish(false);
		if (marginMaster.getId() == null) {
			marginMaster = marginMasterRepository.save(marginMaster);
			return marginMaster;
		} else {
			Optional<MarginMaster> marginMaster1 = marginMasterRepository.findById(marginMaster.getId());
			if (marginMaster1.isPresent()) {
				MakerAndChecker makerAndChecker = new MakerAndChecker();
				List<MakerAndCheckerValues> makerAndCheckerValues = new ArrayList<>();
				boolean isValueChanged = false;
				if (marginMaster1.get().getRangeFrom() != marginMaster.getRangeFrom()) {
					isValueChanged = true;
					MakerAndCheckerValues makerAndCheckerValue = new MakerAndCheckerValues();
					makerAndCheckerValue.setFieldName(marginMaster.getClass().getDeclaredField("rangeFrom").getName());
					makerAndCheckerValue
							.setFieldType(marginMaster.getClass().getDeclaredField("rangeFrom").getType().getName());
					makerAndCheckerValue.setFromValue(marginMaster1.get().getRangeFrom().toString());
					makerAndCheckerValue.setToValue(marginMaster.getRangeFrom().toString());
					makerAndCheckerValues.add(makerAndCheckerValue);
				}
				if (marginMaster1.get().getRangeTo() != marginMaster.getRangeTo()) {
					isValueChanged = true;
					MakerAndCheckerValues makerAndCheckerValue = new MakerAndCheckerValues();
					makerAndCheckerValue.setFieldName(marginMaster.getClass().getDeclaredField("rangeTo").getName());
					makerAndCheckerValue
							.setFieldType(marginMaster.getClass().getDeclaredField("rangeTo").getType().getName());
					makerAndCheckerValue.setFromValue(marginMaster1.get().getRangeFrom().toString());
					makerAndCheckerValue.setToValue(marginMaster.getRangeFrom().toString());
					makerAndCheckerValues.add(makerAndCheckerValue);
				}

				if (isValueChanged) {
					makerAndChecker.setMakerCheckerFunction(MakerCheckerFunction.MARGIN);
					makerAndChecker.setMakerAndCheckerValues(makerAndCheckerValues);
					makerAndChecker.setRecordId(marginMaster.getId());
					makerAndCheckerService.save(makerAndChecker);
				}

				return marginMasterRepository.save(marginMaster);
			}
			{
				marginMaster = marginMasterRepository.save(marginMaster);
				return marginMaster;
			}
		}
	}

	public void deleteMargin(Long id) throws RecordNotFoundException {
		Optional<MarginMaster> marginMaster = marginMasterRepository.findById(id);
		if (marginMaster.isPresent()) {
			MarginMaster marginMaster1 = marginMaster.get();
			marginMaster1.setIsDeleted(true);
			marginMasterRepository.save(marginMaster1);
		}
	}

	public MarginMaster getById(Long id) throws RecordNotFoundException {
		Optional<MarginMaster> marginMaster = marginMasterRepository.findById(id);
		MarginMaster marginMaster1 = null;
		try {
			if (marginMaster.isPresent()) {
				marginMaster1 = marginMaster.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return marginMaster1;
	}

	public List<MarginMaster> listOfMargins() {
		List<MarginMaster> marginMasters = null;
		marginMasters = marginMasterRepository.findAllByIsDeleted(false);
		return marginMasters;
	}

	@Override
	public boolean getExistingFeeByConditions(MarginMaster marginMaster) {
		boolean isExist = false;
		try {
			PaymentReceiveMode receiveMode = paymentReceiveModeService.getById(marginMaster.getReceiveCode().getId());
			PaymentReceiveMode paymentMode = paymentReceiveModeService.getById(marginMaster.getPaymentCode().getId());
			Countries sendCountry = countriesService.getById(marginMaster.getSendCountryCode().getId());
			Countries receiveCountry = countriesService.getById(marginMaster.getReceiveCountryCode().getId());
			Currencies sendCurrency = currenciesService.getById(marginMaster.getSendCurrencyCode().getId());
			Currencies receiveCurrency = currenciesService.getById(marginMaster.getReceiveCurrencyCode().getId());
			List<MarginMaster> marginMasters = marginMasterRepository
					.findAllByIsDeletedAndReceiveCodeAndPaymentCodeAndSendCurrencyCodeAndReceiveCurrencyCodeAndSendCountryCodeAndReceiveCountryCode(
							false, receiveMode, paymentMode, sendCurrency, receiveCurrency, sendCountry,
							receiveCountry);
			for (MarginMaster marginMaster1 : marginMasters) {
				boolean isEx = feeMasterService.isBetween(marginMaster.getRangeFrom(), marginMaster1.getRangeFrom(),
						marginMaster1.getRangeTo());
				if (isEx) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExist;
	}

	@Override
	public boolean getExistingFeeByConditionsById(MarginMaster marginMaster) {
		boolean isExist = false;
		try {
			PaymentReceiveMode receiveMode = paymentReceiveModeService.getById(marginMaster.getReceiveCode().getId());
			PaymentReceiveMode paymentMode = paymentReceiveModeService.getById(marginMaster.getPaymentCode().getId());
			Countries sendCountry = countriesService.getById(marginMaster.getSendCountryCode().getId());
			Countries receiveCountry = countriesService.getById(marginMaster.getReceiveCountryCode().getId());
			Currencies sendCurrency = currenciesService.getById(marginMaster.getSendCurrencyCode().getId());
			Currencies receiveCurrency = currenciesService.getById(marginMaster.getReceiveCurrencyCode().getId());
			List<MarginMaster> marginMasters = marginMasterRepository
					.findAllByIsDeletedAndReceiveCodeAndPaymentCodeAndSendCurrencyCodeAndReceiveCurrencyCodeAndSendCountryCodeAndReceiveCountryCode(
							false, receiveMode, paymentMode, sendCurrency, receiveCurrency, sendCountry,
							receiveCountry);
			for (MarginMaster marginMaster1 : marginMasters) {
				if (marginMaster1.getId().longValue() != marginMaster.getId().longValue()) {
					boolean isEx = feeMasterService.isBetween(marginMaster.getRangeFrom(), marginMaster1.getRangeFrom(),
							marginMaster1.getRangeTo());
					if (isEx) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExist;
	}

	@Override
	public Optional<MarginMaster> getMarginById(Long marginId) {
		return marginMasterRepository.findById(marginId);
	}

	@Override
	public void approveMargin(MarginMaster margin) {
		Optional<MarginMaster> optionalMargin = marginMasterRepository.findById(margin.getId());
		if (optionalMargin.isPresent()) {
			marginMasterRepository.save(margin);
		}
	}

	@Override
	public void rejectMargin(MarginMaster margin) {
		Optional<MarginMaster> optionalFee = marginMasterRepository.findById(margin.getId());
		if (optionalFee.isPresent()) {
			marginMasterRepository.save(margin);
		}
	}

	@Override
	public List<MarginMaster> listAll() {
		return marginMasterRepository.findAllByIsDeletedAndPublishTrue(false);
	}

	@Override
	public List<MarginMaster> listOfMarginMasterByPaymentMode(Long paymentReceiveModeId)
			throws RecordNotFoundException {
		return marginMasterRepository.findAllByIsDeletedAndPaymentCodeAndPublishTrue(false,
				paymentReceiveModeService.getById(paymentReceiveModeId));
	}

	@Override
	public List<MarginMaster> listOfMarginMasterByReceiveMode(Long paymentReceiveModeId)
			throws RecordNotFoundException {
		return marginMasterRepository.findAllByIsDeletedAndReceiveCodeAndPublishTrue(false,
				paymentReceiveModeService.getById(paymentReceiveModeId));
	}

}
