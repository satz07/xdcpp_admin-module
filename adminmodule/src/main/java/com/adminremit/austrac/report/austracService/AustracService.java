package com.adminremit.austrac.report.austracService;

import com.adminremit.austrac.report.Maintaining_Sequence;

public interface AustracService {

    Maintaining_Sequence saveReportSeq(String fileName);

    String getReportSeq();
}
