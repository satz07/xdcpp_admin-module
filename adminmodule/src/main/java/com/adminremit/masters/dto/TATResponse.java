package com.adminremit.masters.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TATResponse {
    private BigDecimal amountSlabFrom;
    private BigDecimal amountSlabTo;
    private String originCCM;
    private String paymentMode;
    private int paymentHrs;
    private String  disburseCCM;
    private String receiveMode;
    private int receiveHrs;
    private String expiryTime;
    private String cancelTime;
    
}
