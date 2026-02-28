package com.adminremit.austrac.report.austracServiceImpl;

import com.adminremit.austrac.report.AustracReportHelper;
import com.adminremit.austrac.report.Maintaining_Sequence;
import com.adminremit.austrac.report.austracRepository.AstracReportRepository;
import com.adminremit.austrac.report.austracService.AustracService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class AustracServiceImpl implements AustracService {

    @Autowired
    AstracReportRepository astracReportRepository;

    @Override
    public Maintaining_Sequence saveReportSeq(String fileName) {


        int fileLength = fileName.length();
       // int fileLength = (fileName.split(".")[0]).length();
            char a,b;

        if(fileLength == 22){
            a = fileName.charAt(16);
            b = fileName.charAt(17);
        }else{
            a = fileName.charAt(17);
            b = fileName.charAt(18);
        }

        Maintaining_Sequence maintaining_sequence = new Maintaining_Sequence();
        maintaining_sequence.setSeqType("AUSTRAC_REPORT");
        String seq = a+""+b;
        maintaining_sequence.setCurrentValue(seq);
        return astracReportRepository.save(maintaining_sequence);
    }

    @Override
    public String getReportSeq() {

        Date date = new Date();
        Date startDate = AustracReportHelper.getStartDate(date);
        Date endDate = AustracReportHelper.getEndDate(date);
        long time = startDate.getTime();
        Timestamp start = new Timestamp(time);
        long time1 = endDate.getTime();
        Timestamp end = new Timestamp(time1);
        String str = astracReportRepository.checkUpdatedSeqNumber(start, end);
        String str1 = "";
        if (str == null) {
            return "01";
        } else if ((Integer.parseInt(str)) < 9) {
            char a = str.charAt(0);
            char b = str.charAt(1);
            if (a == '0') {
                int temp = Character.getNumericValue(b);
                temp++;
                int tt = temp;
                str1 = a + "" + tt;
            }
        }else if ((Integer.parseInt(str)) >=9) {
            int temp = Integer.parseInt(str);
            temp++;
            str1 = Integer.toString(temp);
        }
        return str1;
    }
}
