package com.adminremit.operations.repository;

import com.adminremit.operations.dto.MatchExceptionDTO;
import com.adminremit.operations.dto.ViewMatchExceptionDTO;
import com.adminremit.operations.model.*;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.personaldetails.service.PersonalDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.adminremit.emails.MailService;
import com.adminremit.operations.dto.MatchExceptionDTO;
import com.adminremit.operations.dto.ViewMatchExceptionDTO;
import com.adminremit.operations.model.ReconStatus;
import com.adminremit.operations.model.TransferAccountDetails;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.personaldetails.model.PersonalDetails;
import com.adminremit.personaldetails.service.PersonalDetailsService;

@Repository
public class MatchedExceptionCustomSearch {

    private static final Logger LOG = LoggerFactory.getLogger(MatchedExceptionCustomSearch.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    UserCalculatorRepository userCalculatorRepository;

    @Autowired
    TransferAccountRepository transferAccountRepository;

    @Autowired
    private PersonalDetailsService personalDetailsService;

    @Autowired
    private MailService mailService;


    private Long dayCalculator(Date date){

        Instant instant = date.toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        LocalDate fromDate = zdt.toLocalDate();
        Date currentDate = new Date();
        Instant instant2 = currentDate.toInstant();
        ZonedDateTime zdt2 = instant2.atZone(ZoneId.systemDefault());
        LocalDate toDate = zdt2.toLocalDate();
        long noOfDaysBetween = ChronoUnit.DAYS.between(fromDate, toDate);
        return noOfDaysBetween;

    }

    public List<MatchExceptionDTO> getAllMatchedExceptionMakerList(String state) {

        String query = "select distinct(reference_id),create_at,status from transaction_worflow where workflow_state='MATCHED_EXCEPTION' and is_completed=false ORDER  BY reference_id, create_at DESC";
        /*
        String query = "select a.beneficiary_email,a.reference_id,a.from_currency_code,a.transfer_amount,a.beneficiary_name,"+
                "c.bank_ref,a.to_currency_code,c.amt_received, extract(day from now() - c.value_date) "+
        "from user_calc_mapping as a,transfered_acc_details as c "+
        "where a.reference_id in"+
        "(select t.reference_id from transaction_worflow t "+
        "inner join(select distinct(reference_id) as refid, create_at as createat from "+
        "transaction_worflow where workflow_state='"+state+"' and is_completed=false "+
        "ORDER  BY reference_id, create_at DESC)tw "+
        "on t.reference_id = tw.refid and  t.create_at=tw.createat) and a.reference_id=c.transaction_ref";
       */
         /*
        String query ="select a.beneficiary_email,a.reference_id,a.from_currency_code,a.transfer_amount,a.beneficiary_name,"+
                      "c.bank_ref,a.to_currency_code,c.amt_received, extract(day from now() - c.value_date)"+
                      " from user_calc_mapping as a,transaction_worflow as b,transfered_acc_details as c"+
                      " where a.reference_id= b.reference_id and b.workflow_state='"+state+"' and b.is_completed=false"+
                      " and a.reference_id=c.transaction_ref"+
                      " and b.create_at = (SELECT MAX(create_at) FROM transaction_worflow)";*/

        List<Object[]> matchedWithExceptionList = entityManager.createNativeQuery(query)
        .getResultList();

        List<String> names = new ArrayList<>();

        for (Object[] matchWithException : matchedWithExceptionList) {

            names.add(matchWithException[0].toString());
        }

        List<UserCalculatorMapping> userCalMappings = userCalculatorRepository.findUserCalculatorMappingByNameRefid(names);

        List<MatchExceptionDTO> matchException = new ArrayList<MatchExceptionDTO>();


        for(UserCalculatorMapping userCalculatorMapping: userCalMappings)
        {
            MatchExceptionDTO matchExceptionDTO = new MatchExceptionDTO();
            matchExceptionDTO.setEmail(userCalculatorMapping.getBeneficiaryEmail());
            matchExceptionDTO.setRefId(userCalculatorMapping.getRefId());
            matchExceptionDTO.setFromCurrencyCode(userCalculatorMapping.getFromCurrencyCode());
            matchExceptionDTO.setTransferAmount(userCalculatorMapping.getTransferAmount());
            matchExceptionDTO.setBeneficiaryName(userCalculatorMapping.getBeneficiaryName());

            TransferAccountDetails transferAccountDetails = transferAccountRepository.findAllBytransactionRefNo(userCalculatorMapping.getRefId()).get(0);

            matchExceptionDTO.setBankRefNo(transferAccountDetails.getBankRef());
            matchExceptionDTO.setToCurrencyCode(userCalculatorMapping.getToCurrencyCode());
            matchExceptionDTO.setAmountReceived(transferAccountDetails.getAmountReceived());
            matchExceptionDTO.setAgeing(dayCalculator(transferAccountDetails.getValueDate()).intValue());
            matchException.add(matchExceptionDTO);
            
            /*final Map<String, Object> messageValueMap = new HashMap<>();
            String subject = "Cancellation of your instruction for Remittance Transaction Reference Number "+ userCalculatorMapping.getRefId();
            String templateName = "cancel_refund_post_mismatch_11";
            messageValueMap.put("remitter_name",getFullName(userCalculatorMapping.getUser().getId()));
            messageValueMap.put("transaction_number",userCalculatorMapping.getRefId());
            try {
                mailService.sendEmailHtml(userCalculatorMapping.getUser().getEmail(),subject,templateName,messageValueMap);
            } catch (Exception e) {
                LOG.error("Exception While sending email on amount mismatch caused by :"+ e.getCause()+ "Description:"+e.getMessage());
            }*/

        }
        return  matchException;

    }

    public List<MatchExceptionDTO> getAllMatchedExceptionCheckerList(String state) {

       /* String query = "select a.beneficiary_email,a.reference_id,a.from_currency_code,a.transfer_amount,a.beneficiary_name,"+
                "c.bank_ref,a.to_currency_code,c.amt_received, extract(day from now() - c.value_date) "+
                "from user_calc_mapping as a,transfered_acc_details as c "+
                "where a.reference_id in"+
                "(select t.reference_id from transaction_worflow t "+
                "inner join(select distinct(reference_id) as refid, create_at as createat from "+
                "transaction_worflow where workflow_state='"+state+"' and is_completed=false "+
                "ORDER  BY reference_id, create_at DESC)tw "+
                "on t.reference_id = tw.refid and  t.create_at=tw.createat) and a.reference_id=c.transaction_ref";
*/

        /*String query ="select a.beneficiary_email,a.reference_id,a.from_currency_code,a.transfer_amount,a.beneficiary_name,"+
                "c.bank_ref,a.to_currency_code,c.amt_received, extract(day from now() - c.value_date),"+
                "b.status from user_calc_mapping as a,transaction_worflow as b,transfered_acc_details as c"+
                " where a.reference_id= b.reference_id and b.workflow_state='"+state+"' and b.is_completed=true"+
                " and a.reference_id=c.transaction_ref"+
                " and b.create_at = (SELECT MAX(create_at) FROM transaction_worflow)";
*/

        String query = "select distinct(reference_id),create_at,status from transaction_worflow where workflow_state='MATCHED_EXCEPTION_CHECKER' and is_completed=false ORDER  BY reference_id, create_at DESC";


        List<Object[]> matchedWithExceptionList = entityManager.createNativeQuery(query)
                .getResultList();

        List<String> names = new ArrayList<>();

        for (Object[] matchWithException : matchedWithExceptionList) {

            names.add(matchWithException[0].toString());
        }

        List<UserCalculatorMapping> userCalMappings = userCalculatorRepository.findUserCalculatorMappingByNameRefid(names);

        List<MatchExceptionDTO> matchException = new ArrayList<MatchExceptionDTO>();


        for(UserCalculatorMapping userCalculatorMapping: userCalMappings)
        {
            MatchExceptionDTO matchExceptionDTO = new MatchExceptionDTO();
            matchExceptionDTO.setEmail(userCalculatorMapping.getBeneficiaryEmail());
            matchExceptionDTO.setRefId(userCalculatorMapping.getRefId());
            matchExceptionDTO.setFromCurrencyCode(userCalculatorMapping.getFromCurrencyCode());
            matchExceptionDTO.setTransferAmount(userCalculatorMapping.getTransferAmount());
            matchExceptionDTO.setBeneficiaryName(userCalculatorMapping.getBeneficiaryName());

            TransferAccountDetails transferAccountDetails = transferAccountRepository.findAllBytransactionRefNo(userCalculatorMapping.getRefId()).get(0);

            matchExceptionDTO.setBankRefNo(transferAccountDetails.getBankRef());
            matchExceptionDTO.setToCurrencyCode(userCalculatorMapping.getToCurrencyCode());
            matchExceptionDTO.setAmountReceived(transferAccountDetails.getAmountReceived());
            matchExceptionDTO.setAgeing(dayCalculator(transferAccountDetails.getValueDate()).intValue());

            for (Object[] matchWithException : matchedWithExceptionList) {
                if(matchWithException[0].toString().equalsIgnoreCase(transferAccountDetails.getTransactionRefNo()))
                {
                    matchExceptionDTO.setStatus(matchWithException[2].toString());
                }
            }
            
            /*final Map<String, Object> messageValueMap = new HashMap<>();
            String subject = "Funds are sent to your Recipient";
            String templateName = "amount_mismatch_10";
            messageValueMap.put("remitter_name",getFullName(userCalculatorMapping.getUser().getId()));
            messageValueMap.put("transaction_number",userCalculatorMapping.getRefId());
            messageValueMap.put("sending_currency",userCalculatorMapping.getFromCurrencyCode());
            messageValueMap.put("booking_amount",userCalculatorMapping.getTransferAmount());
            messageValueMap.put("received_sending_currency",transferAccountDetails.getCurrencyCode());
            messageValueMap.put("received_booking_amount",transferAccountDetails.getAmountReceived());
            try {
                mailService.sendEmailHtml(userCalculatorMapping.getUser().getEmail(),subject,templateName,messageValueMap);
            } catch (Exception e) {
                LOG.error("Exception While sending email on amount mismatch caused by :"+ e.getCause()+ "Description:"+e.getMessage());
            }*/

            matchException.add(matchExceptionDTO);

        }
        return  matchException;


    }

    public ViewMatchExceptionDTO getViewMatchedExceptionView(String state, String refId,boolean isCompleted)  {

        String query ="select " +
                "c.value_date," +
                "c.transaction_type," +
                "c.currency_code," +
                "c.amt_received," +
                "c.transaction_ref," +
                "c.bank_ref," +
                "c.sender_name," +
                "a.user_account_type," +
                "a.user_name," +
                "a.created_at," +
                "a.from_currency_code," +
                "a.transfer_amount, " +
                "a.to_currency_code," +
                "a.beneficiary_email," +
                "a.beneficiary_name,"+
                "a.payment_mode,"+
                "a.receive_mode,"+
                "b.status " +
                "from user_calc_mapping as a,transaction_worflow as b,transfered_acc_details as c"+
                " where a.reference_id='"+refId+"' and b.reference_id='"+refId+"' and b.workflow_state='"+state+"' and b.is_completed=true"+
                " and c.transaction_ref='"+refId+"'";

        List<Object[]> matchedWithExceptionList = entityManager.createNativeQuery(query)
                .getResultList();

        List<ViewMatchExceptionDTO> matchException = new ArrayList<ViewMatchExceptionDTO>();

        for (Object[] matchWithException : matchedWithExceptionList) {
            ViewMatchExceptionDTO viewMatchExceptionDTO = new ViewMatchExceptionDTO();
            try {


                Date bookedDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(matchWithException[0].toString());
                String pattern = "dd-MM-yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String strDate = simpleDateFormat.format(bookedDate);
                viewMatchExceptionDTO.setValueDate(strDate);
                viewMatchExceptionDTO.setTransactionType(matchWithException[1].toString());
                viewMatchExceptionDTO.setFromCurrencyCode(matchWithException[2].toString());
                viewMatchExceptionDTO.setAmountReceived(new BigDecimal(matchWithException[3].toString()));
                viewMatchExceptionDTO.setRefId(matchWithException[4].toString());
                viewMatchExceptionDTO.setBankRefNo(matchWithException[5].toString());
                viewMatchExceptionDTO.setSenderName(matchWithException[6].toString());
                viewMatchExceptionDTO.setUserType(matchWithException[7].toString());
                viewMatchExceptionDTO.setEmail(matchWithException[8].toString());
                bookedDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(matchWithException[9].toString());
                strDate = simpleDateFormat.format(bookedDate);
                viewMatchExceptionDTO.setBookingDate(strDate);
                pattern = "HH:mm";
                simpleDateFormat = new SimpleDateFormat(pattern);
                String strTime = simpleDateFormat.format(bookedDate);
                viewMatchExceptionDTO.setTime(strTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            viewMatchExceptionDTO.setFromCurrencyCode(matchWithException[10].toString());
            viewMatchExceptionDTO.setTransferAmount(new BigDecimal(matchWithException[11].toString()));
            viewMatchExceptionDTO.setToCurrencyCode(matchWithException[12].toString());
            viewMatchExceptionDTO.setEmail(matchWithException[13].toString());
            viewMatchExceptionDTO.setBeneficiaryName(matchWithException[14].toString());
            viewMatchExceptionDTO.setPaymentCode(matchWithException[15].toString());
            viewMatchExceptionDTO.setReceiveCode(matchWithException[16].toString());

            if(matchWithException[17].toString().equalsIgnoreCase(ReconStatus.USER_ACTION.toString()))
              viewMatchExceptionDTO.setStatus("User Action");
            else if(matchWithException[17].toString().equalsIgnoreCase(ReconStatus.BALANCE_AMT.toString()))
                viewMatchExceptionDTO.setStatus("Balance Amount");
            else if(matchWithException[17].toString().equalsIgnoreCase(ReconStatus.REFUND.toString()))
                viewMatchExceptionDTO.setStatus("Refund");
            else if(matchWithException[17].toString().equalsIgnoreCase(ReconStatus.PROCESS.toString()))
                viewMatchExceptionDTO.setStatus("Process");



            matchException.add(viewMatchExceptionDTO);

        }
        return  matchException.get(0);


    }

    public ViewMatchExceptionDTO getMatchedExceptionMakerTransactionView(String state, String refId,boolean isCompleted)  {

        String query ="select " +
                "c.value_date," +
                "c.transaction_type," +
                "c.currency_code," +
                "c.amt_received," +
                "c.transaction_ref," +
                "c.bank_ref," +
                "c.sender_name," +
                "a.user_account_type," +
                "a.user_name," +
                "a.created_at," +
                "a.from_currency_code," +
                "a.transfer_amount, " +
                "a.to_currency_code," +
                "a.beneficiary_email," +
                "a.beneficiary_name,"+
                "a.payment_mode,"+
                "a.receive_mode"+
                " from user_calc_mapping as a,transaction_worflow as b,transfered_acc_details as c"+
                " where a.reference_id='"+refId+"' and b.reference_id='"+refId+"' and b.workflow_state='"+state+"' and b.is_completed=true"+
                " and c.transaction_ref='"+refId+"'";

        List<Object[]> matchedWithExceptionList = entityManager.createNativeQuery(query)
                .getResultList();

        List<ViewMatchExceptionDTO> matchException = new ArrayList<ViewMatchExceptionDTO>();

        for (Object[] matchWithException : matchedWithExceptionList) {
            ViewMatchExceptionDTO viewMatchExceptionDTO = new ViewMatchExceptionDTO();
            try {


                Date bookedDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(matchWithException[0].toString());
                String pattern = "dd-MM-yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String strDate = simpleDateFormat.format(bookedDate);
                viewMatchExceptionDTO.setValueDate(strDate);
                viewMatchExceptionDTO.setTransactionType(matchWithException[1].toString());
                viewMatchExceptionDTO.setFromCurrencyCode(matchWithException[2].toString());
                viewMatchExceptionDTO.setAmountReceived(new BigDecimal(matchWithException[3].toString()));
                viewMatchExceptionDTO.setRefId(matchWithException[4].toString());
                viewMatchExceptionDTO.setBankRefNo(matchWithException[5].toString());
                viewMatchExceptionDTO.setSenderName(matchWithException[6].toString());
                viewMatchExceptionDTO.setUserType(matchWithException[7].toString());
                viewMatchExceptionDTO.setEmail(matchWithException[8].toString());
                bookedDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(matchWithException[9].toString());
                strDate = simpleDateFormat.format(bookedDate);
                viewMatchExceptionDTO.setBookingDate(strDate);
                pattern = "HH:mm";
                simpleDateFormat = new SimpleDateFormat(pattern);
                String strTime = simpleDateFormat.format(bookedDate);
                viewMatchExceptionDTO.setTime(strTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            viewMatchExceptionDTO.setFromCurrencyCode(matchWithException[10].toString());
            viewMatchExceptionDTO.setTransferAmount(new BigDecimal(matchWithException[11].toString()));
            viewMatchExceptionDTO.setToCurrencyCode(matchWithException[12].toString());
            viewMatchExceptionDTO.setEmail(matchWithException[13].toString());
            viewMatchExceptionDTO.setBeneficiaryName(matchWithException[14].toString());
            viewMatchExceptionDTO.setPaymentCode(matchWithException[15].toString());
            viewMatchExceptionDTO.setReceiveCode(matchWithException[16].toString());


            matchException.add(viewMatchExceptionDTO);

        }
        return  matchException.get(0);


    }

    private String getFullName(Long userId) {
        PersonalDetails personalDetails = personalDetailsService.getLatestByUser(userId);
        String fullname = "";

        System.out.println("First Name: "+personalDetails.getFullName());
        System.out.println("Middle Name: "+personalDetails.getMiddleName());
        System.out.println("Last Name: "+personalDetails.getLastName());

        if (!StringUtils.isEmpty(personalDetails.getLastName())) {
            fullname = personalDetails.getFullName()+" "+personalDetails.getMiddleName()+" "+personalDetails.getLastName();
        } else {
            fullname = personalDetails.getFullName()+" "+personalDetails.getLastName();
        }

        return fullname;
    }
}
