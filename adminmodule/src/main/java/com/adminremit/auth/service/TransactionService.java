package com.adminremit.auth.service;

import java.util.List;

import com.adminremit.auth.dto.KYCDTO;
import com.adminremit.auth.dto.RecipientDTO;
import com.adminremit.auth.dto.RemitterEDDDTO;
import com.adminremit.auth.dto.RemitterTransactionDTO;
import com.adminremit.auth.dto.UsersDTO;
import com.adminremit.beneficiary.model.BeneficiaryAccount;
import com.adminremit.beneficiary.model.BeneficiaryUser;
import com.adminremit.operations.model.TransactionWorkflow;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.operations.model.WorkflowStatus;
import com.adminremit.user.model.Users;

public interface TransactionService {

	public List<TransactionWorkflow> getPaginatedTransactionList();
	
	public TransactionWorkflow getPaginatedTransactionSummary(String trns_no);

	public UserCalculatorMapping getUserCalculatorById(String trns_no);
	
	public Users getUserById(Long id);
	
	public BeneficiaryAccount getBeneficiaryById(Long Id);
	
	public BeneficiaryUser getBeneficiartUserById(Long Id);
	
	
	 public List<RemitterTransactionDTO> getAllTransactionByEmailId(String emailid);
	 
	 public List<RemitterEDDDTO> getEddDataByEmailId(String emailid);
	 
	 public  List<UsersDTO> getRemitterByEmail(String email);
	 
	 public  List<KYCDTO> getKYC1ByEmail(String email);
	 
	 public  List<KYCDTO> getKYC2ByEmail(String email);
	 
	// public  List<KYCDTO> getKYCAMLByEmail(String email);
	 
	 public  List<RecipientDTO> getRecipientByEmail(String email);

	 public TransactionWorkflow getTransactionByReferenceIdAndWorkflowStatus(String referenceNo,WorkflowStatus stagingStart);

	 public List<TransactionWorkflow> findByReferenceNo(String refId);
	 
	 public TransactionWorkflow getLatestRecordByRefernceId(String refId);
}
