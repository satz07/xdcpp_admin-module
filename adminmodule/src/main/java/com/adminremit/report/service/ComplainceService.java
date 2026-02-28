package com.adminremit.report.service;

import com.adminremit.report.compliance.dto.ComplianceReportDTO;
import com.adminremit.report.vo.ComplainceVO;

public interface ComplainceService {
    ComplainceVO getComplainceReport(String txnRef);
    ComplianceReportDTO extractReport(String refId);
}
