package com.adminremit.backoffice.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.adminremit.backoffice.dto.StagingSearchDTO;
import com.adminremit.backoffice.repository.StagingSearchRepository;
import com.adminremit.operations.model.TransactionWorkflow;
import com.adminremit.operations.model.TransactionWorkflow_;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.operations.model.UserCalculatorMapping_;
import com.adminremit.operations.model.WorkflowStatus;

@Component
public class StagingCustomSearch {

    private static final Logger LOG = LoggerFactory.getLogger(StagingCustomSearch.class);

    @Autowired
    StagingSearchRepository stagingSearchRepository;

    public List<UserCalculatorMapping> getAllStagingMakerList() {

        Specification<UserCalculatorMapping> result = Specification.where(
                (root, query, builder) -> {
                    final Join<UserCalculatorMapping,TransactionWorkflow> addresses = root.join(UserCalculatorMapping_.transactionWorflow, JoinType.LEFT);
                    return builder.and(
                            builder.equal(addresses.get(TransactionWorkflow_.WORKFLOW_STATUS),WorkflowStatus.STAGING_START),
                            builder.isFalse(addresses.get(TransactionWorkflow_.isCompleted))
                    );

                }
        );
         return stagingSearchRepository.findAll(result);
    }

    public List<UserCalculatorMapping> getAllStagingCheckerList() {
        Specification<UserCalculatorMapping> result = Specification.where(
                (root, query, builder) -> {
                    final Join<UserCalculatorMapping,TransactionWorkflow> addresses = root.join(UserCalculatorMapping_.transactionWorflow, JoinType.LEFT);
                    return builder.and(
                    		builder.equal(addresses.get(TransactionWorkflow_.WORKFLOW_STATUS),WorkflowStatus.BACKOFFICE_CANCEL),
                    		builder.isFalse(addresses.get(TransactionWorkflow_.isCompleted))
                    );
                }
        );
        return stagingSearchRepository.findAll(result);
    }

    public List<UserCalculatorMapping> getUserTransactionByrefId(String refNo) {
        Specification<UserCalculatorMapping> result = Specification.where(refNoSearch(refNo.trim()));
                //and(basedOnState(WorkflowStatus.STAGING_START));
        return stagingSearchRepository.findAll(result);
    }

        public List<UserCalculatorMapping> getStagingMakerSearch(StagingSearchDTO stagingSearchDTO) {

        List<Specification<UserCalculatorMapping>> specifications = new ArrayList<>();

        if(stagingSearchDTO.getUserType()!=null && !stagingSearchDTO.getUserType().isEmpty()) {

            specifications.add(userTypeSearch(stagingSearchDTO.getUserType()));
        }
        if(stagingSearchDTO.getEmail()!=null && !stagingSearchDTO.getEmail().isEmpty()) {
            specifications.add(emailSearch(stagingSearchDTO.getEmail()));
        }
        if(stagingSearchDTO.getPaymentMode()!=null && !stagingSearchDTO.getPaymentMode().isEmpty()) {
            specifications.add(paymentModeSearch(stagingSearchDTO.getPaymentMode()));
        }
        if(stagingSearchDTO.getReceiveMode()!=null && !stagingSearchDTO.getReceiveMode().isEmpty()) {
            specifications.add(receiveModeSearch(stagingSearchDTO.getReceiveMode()));
        }
        if(stagingSearchDTO.getCurrency()!=null && !stagingSearchDTO.getCurrency().isEmpty()) {
            specifications.add(currencySearch(stagingSearchDTO.getCurrency()));
        }
        if(stagingSearchDTO.getAmountFrom()!=null && stagingSearchDTO.getAmountFrom().compareTo(BigDecimal.ZERO) >=0) {
            specifications.add(fromAmountSearch(stagingSearchDTO.getAmountFrom()));
        }
        if(stagingSearchDTO.getAmountTo()!=null && stagingSearchDTO.getAmountTo().compareTo(BigDecimal.ZERO) >=0) {
            specifications.add(toAmountSearch(stagingSearchDTO.getAmountTo()));
        }

            Specification<UserCalculatorMapping> result = Specification.where(
                    (root, query, builder) -> {
                        final Join<UserCalculatorMapping,TransactionWorkflow> addresses = root.join(UserCalculatorMapping_.transactionWorflow, JoinType.LEFT);
                        return builder.and(
                                builder.equal(addresses.get(TransactionWorkflow_.WORKFLOW_STATUS),WorkflowStatus.STAGING_START),
                                builder.isFalse(addresses.get(TransactionWorkflow_.isCompleted))
                        );

                    }
            );

            result.and(paymentModeSearch(stagingSearchDTO.getPaymentMode()));

        /*
        if (!specifications.isEmpty()) {
            int index = 0;
            result = specifications.get(index++);
            for (; index < specifications.size(); ++index) {
                result =result.and(specifications.get(index));
            }
        }
        */
        return stagingSearchRepository.findAll(result);
    }

    public List<UserCalculatorMapping> getStagingSearchChecker(StagingSearchDTO stagingSearchDTO) {

        List<Specification<UserCalculatorMapping>> specifications = new ArrayList<>();

        if(stagingSearchDTO.getUserType()!=null && !stagingSearchDTO.getUserType().isEmpty()) {

            specifications.add(userTypeSearch(stagingSearchDTO.getUserType()));
        }
        if(stagingSearchDTO.getEmail()!=null && !stagingSearchDTO.getEmail().isEmpty()) {
            specifications.add(emailSearch(stagingSearchDTO.getEmail()));
        }
        if(stagingSearchDTO.getPaymentMode()!=null && !stagingSearchDTO.getPaymentMode().isEmpty()) {
            specifications.add(paymentModeSearch(stagingSearchDTO.getPaymentMode()));
        }
        if(stagingSearchDTO.getReceiveMode()!=null && !stagingSearchDTO.getReceiveMode().isEmpty()) {
            specifications.add(receiveModeSearch(stagingSearchDTO.getReceiveMode()));
        }
        if(stagingSearchDTO.getCurrency()!=null && !stagingSearchDTO.getCurrency().isEmpty()) {
            specifications.add(currencySearch(stagingSearchDTO.getCurrency()));
        }
        if(stagingSearchDTO.getAmountFrom()!=null && stagingSearchDTO.getAmountFrom().compareTo(BigDecimal.ZERO) >=0) {
            specifications.add(fromAmountSearch(stagingSearchDTO.getAmountFrom()));
        }
        if(stagingSearchDTO.getAmountTo()!=null && stagingSearchDTO.getAmountTo().compareTo(BigDecimal.ZERO) >=0) {
            specifications.add(toAmountSearch(stagingSearchDTO.getAmountTo()));
        }

        Specification<UserCalculatorMapping> result = null;
        if (!specifications.isEmpty()) {
            int index = 0;
            result = specifications.get(index++);
            for (; index < specifications.size(); ++index) {
                result = Specification.where(result).and(specifications.get(index));
            }
        }

        return stagingSearchRepository.findAll(result.and(basedOnState(WorkflowStatus.STAGING_CANCELLED,Boolean.TRUE)));
    }

    public static Specification<UserCalculatorMapping> basedOnCompleted() {
        return (root, query, builder) -> {

            return builder.equal(root.join("transactionWorflow").get("isCompleted").as(Boolean.class),true);

        };
    }

    public static Specification<UserCalculatorMapping> basedOnState(WorkflowStatus workflowStatus,Boolean status) {

        return (root, query, builder) -> {
            return builder.equal(root.join("transactionWorflow").get("workflowStatus"), workflowStatus);
        };
    }

    public static Specification<UserCalculatorMapping> basedOnState1(WorkflowStatus workflowStatus,Boolean status) {

        return (root, query, builder) -> {
            return builder.and(builder.equal(root.join("transactionWorflow").get("workflowStatus"), workflowStatus),
                    builder.isFalse(root.join("transactionWorflow").get("isCompleted")));
        };
    }

    public static Specification<UserCalculatorMapping> userTypeSearch(String type) {
        return (root, query, builder) -> {
            return  builder.equal(root.get("userAccountType").as(String.class),type);
        };
    }

    public static Specification<UserCalculatorMapping> emailSearch(String email) {
        return (root, query, builder) -> {
            return builder.equal(root.get("beneficiaryEmail"), email);
        };
    }
    public static Specification<UserCalculatorMapping> paymentModeSearch(String paymentMode) {
        return (root, query, builder) -> {
            return builder.equal(root.get("paymentMode"), paymentMode);
        };
    }

    public static Specification<UserCalculatorMapping> receiveModeSearch(String receiveMode) {
        return (root, query, builder) -> {
            return builder.equal(root.get("receiveMode"), receiveMode);
        };
    }

    public static Specification<UserCalculatorMapping> currencySearch(String currencyCode) {
        return (root, query, builder) -> {
            return builder.equal(root.get("fromCurrencyCode"), currencyCode);
        };
    }


    public static Specification<UserCalculatorMapping> fromAmountSearch(BigDecimal amountFrom) {
        return (root, query, builder) -> {
            return builder.greaterThanOrEqualTo(root.get("transferAmount"), amountFrom);
        };
    }

    public static Specification<UserCalculatorMapping> toAmountSearch(BigDecimal amountTo) {
        return (root, query, builder) -> {
            return builder.lessThanOrEqualTo(root.get("transferAmount"), amountTo);
        };
    }

    public static Specification<UserCalculatorMapping> refNoSearch(String refNo) {
        return (root, query, builder) -> {
            return builder.equal(root.get("refId"), refNo);
        };
    }



}
