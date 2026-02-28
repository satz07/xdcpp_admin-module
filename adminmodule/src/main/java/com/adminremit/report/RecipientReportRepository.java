package com.adminremit.report;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RecipientReportRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipientReportRepository.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(final DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Map<String, Object>> getFilteredData(final Map<String, String> dataMap){
        final String partnerId = MapUtils.getString(dataMap, "partnerId");
        final String fromDate = MapUtils.getString(dataMap, "fromDate");
        final String toDate = MapUtils.getString(dataMap, "toDate");
        final String email = MapUtils.getString(dataMap, "email");
        final String limit = MapUtils.getString(dataMap, "limit");
        final String offset = MapUtils.getString(dataMap, "offset");

        final String query = "SELECT  " +
            "	bu.email, " +
            "	bu.full_name, " +
            "	bu.nick_name, " +
            "	rt.receiver_desc, " +
            "	plm.purpose, " +
            "	pd.dob, " +
            "	bad.ifsc_code, " +
            "	bad.account_number, " +
            "	'n/a' AS vpa_handle, " +
            "	'n/a' AS date_added, " +
            "	'n/a' AS date_modified, " +
            "	'n/a' AS status  " +
            "FROM user_calc_mapping ucm " +
            "LEFT JOIN receiver_transaction_details rtd ON rtd.transaction_cal_id = ucm.id " +
            "LEFT JOIN purpose_list_master plm ON plm.id = rtd.receiver_purpose_id " +
            "LEFT JOIN personal_details pd ON pd.id = ucm.beneficiary_id " +
            "LEFT JOIN beneficiary_account_details bad ON bad.id = ucm.beneficiary_id " +
            "LEFT JOIN beneficiary_user bu ON bu.id = ucm.beneficiary_id " +
            "LEFT JOIN receiver_type rt ON rt.id = rtd.receiver_type_id " +
            "WHERE  " +
            "   ucm.beneficiary_id IS NOT null " +
            "   AND rtd.create_at BETWEEN '"+fromDate+"' AND '"+toDate+"' " +
            "   AND bu.email = '"+email+"'" +
            " LIMIT "+ limit +
            " OFFSET "+ offset;
        LOGGER.info(query);
        List<Map<String, Object>> data = this.jdbcTemplate.queryForList(query);
        LOGGER.info("Data size: {}", data.size());
        return data;
    }
}
