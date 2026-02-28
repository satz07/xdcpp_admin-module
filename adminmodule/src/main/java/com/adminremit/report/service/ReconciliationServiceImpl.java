package com.adminremit.report.service;

import com.adminremit.report.vo.ReconciliationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReconciliationServiceImpl implements ReconciliationService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ReconciliationVO> getReconciliationReport() {
        return jdbcTemplate.query(getQueryString(), (rs, rowNum) -> {
            ReconciliationVO reconciliationVO = new ReconciliationVO();
            reconciliationVO.setValueDate(rs.getDate("value_date"));
            reconciliationVO.setCurrency(rs.getString("from_currency_code"));
            reconciliationVO.setTransactionType(rs.getString("user_account_type"));
            reconciliationVO.setAmount(rs.getDouble("amt_received"));
            reconciliationVO.setTransactionRef(rs.getString("reference_id"));
            reconciliationVO.setBankRef(rs.getString("bank_ref"));
            reconciliationVO.setAccountName(rs.getString("sender_name"));
            reconciliationVO.setUpdatedBy(rs.getString("updated_by"));
            reconciliationVO.setUpdatedOn(rs.getDate("update_at"));
            reconciliationVO.setBsbNumber(rs.getString("bsb_number"));
            reconciliationVO.setAccountNumber(rs.getString("account_number"));
            reconciliationVO.setEntryDate(rs.getDate("entry_date"));
            return reconciliationVO;
        });
    }

    private String getQueryString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select tw.create_at as value_date, ");
        stringBuilder.append("       ucm.from_currency_code, ");
        stringBuilder.append("       ucm.user_account_type, ");
        stringBuilder.append("       fi.create_at as entry_date, ");
        stringBuilder.append("       tad.amt_received, ");
        stringBuilder.append("       tw.reference_id, ");
        stringBuilder.append("       tad.bank_ref, ");
        stringBuilder.append("       tad.sender_name, ");
        stringBuilder.append("       tw.update_at, ");
        stringBuilder.append("       tw.updated_by, ");
        stringBuilder.append("       tad.bsb_number, ");
        stringBuilder.append("       tad.account_number ");
        stringBuilder.append("from transaction_worflow tw ");
        stringBuilder.append("         INNER JOIN user_calc_mapping ucm on tw.reference_id = ucm.reference_id ");
        stringBuilder.append("         INNER JOIN transfered_acc_details tad on tad.transaction_ref = ucm.reference_id ");
        stringBuilder.append("         INNER JOIN files_info fi on fi.id = tad.fileinfo_id");
        stringBuilder.append("			where tw.workflow_state = 'STAGING_START' and tw.is_completed = true limit 20");
        return stringBuilder.toString();
    }
}
