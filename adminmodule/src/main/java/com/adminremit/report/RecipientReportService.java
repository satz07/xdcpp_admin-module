package com.adminremit.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.adminremit.beneficiary.enums.AccountType;
import com.adminremit.common.models.Constants;
import com.adminremit.common.models.FilterAndSort;
import com.adminremit.common.models.SearchCriteria;
import com.adminremit.common.service.PredicateService;
import com.adminremit.report.vo.ReceipientVO;

@Service
public class RecipientReportService {

    @Autowired
    private RecipientReportRepository recipientReportRepository;
    
    @Autowired
    private PredicateService<ReceipientVO> predicateService;

    public List<Map<String, Object>> getFilteredData(final Map<String, String> dataMap){
        final String currentDataCount = MapUtils.getString(dataMap, "currentDataCount", "0");
        final int limit = NumberUtils.toInt(currentDataCount, 0) + 10;
        final Map<String, String> criteriaMap = new HashMap<>(dataMap);
        criteriaMap.put("limit", String.valueOf(limit));

        final int offset = NumberUtils.toInt(currentDataCount, 0) + 10;        
        criteriaMap.put("offset", String.valueOf(offset));

        return recipientReportRepository.getFilteredData(criteriaMap);
    }
    
    public Page<ReceipientVO> findAllByPage(final Map<String, String> dataMap){
    	List<Order> orders = createOrders();
    	Pageable page = PageRequest.of(Integer.parseInt(dataMap.get("pageNum")), Constants.PAGE_SIZE, Sort.by(orders));
    	Set<SearchCriteria> criterias = createCriteria(dataMap);
    	
    	FilterAndSort filterAndSort = new FilterAndSort(null, criterias);
    	
    	String selectQuery = "select new com.adminremit.report.vo.ReceipientVO(users.email as email, "+
    						 "beneUser.fullName as receipientName, "+
    						 "beneUser.nickName as nickName, "+
    						 "receiverType.receiverDesc as relationship, "+
    						 "beneUser.dob as dob, "+
    						 "beneAcc.ifscCode as ifscCode, "+
    						 "beneAcc.branchName as bankName, "+
    						 "case when beneAcc.accountType = '" + AccountType.BANK_ACCOUNT.name() + "'"+" then beneAcc.accountNumber else '' end as accountNumber, "+
    						 "beneAcc.accountType as accountTitle, "+
    						 "case when beneAcc.accountType = '" + AccountType.UPI.name() + "'"+" then beneAcc.accountNumber else '' end as vpaHandle, "+
    						 "beneUser.createAt as dateAdded, "+
    						 "beneUser.updateAt as dateUpdated, "+
    						 "beneUser.beneficiaryStatus as accountStatus, "+
    						 "beneAcc.verificationStatus as verificationStatus ) ";
    	
    	String fromWhereQuery = "FROM Users users " +
    					   		"inner join BeneficiaryUser beneUser on users.id = beneUser.user " +
    					   		"inner join BeneficiaryAccount beneAcc on beneUser.id = beneAcc.beneficiaryUser.id " +
    					   		"inner join ReceiverType receiverType on beneUser.beneficiaryRelationship = receiverType.id ";
    	
    	
    	return predicateService.loadDataByPage(page, fromWhereQuery, selectQuery, filterAndSort, null, false);
    }
    
    private Set<SearchCriteria> createCriteria(Map<String, String> dataMap) {
    	Set<SearchCriteria> criterias = new HashSet<>();
    	if(dataMap.get("email")!=null && !dataMap.get("email").isEmpty()) {
    		SearchCriteria c1 = new SearchCriteria("users.email", "equals", dataMap.get("email"));
    		criterias.add(c1);
    	}
    	if(dataMap.get("fromDate")!=null && !dataMap.get("fromDate").isEmpty()) {
    		SearchCriteria c2 = new SearchCriteria("beneUser.createAt", "greaterThanOrEqualTo", dataMap.get("fromDate"));
    		criterias.add(c2);
    	}
    	if(dataMap.get("toDate")!=null && !dataMap.get("toDate").isEmpty()) {
    		SearchCriteria c3 = new SearchCriteria("beneUser.createAt", "lessThanOrEqualTo", dataMap.get("toDate"));
    		criterias.add(c3);
    	}
    	
		return criterias;
	}

	private List<Order> createOrders() {
    	List<Order> listOrders = new ArrayList<>();
    	listOrders.add(new Order(Direction.DESC, "dateAdded"));
    	    	
    	return listOrders;
    }
}

