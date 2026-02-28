package com.adminremit.backoffice.dto;

public class RemittResponseDTO {

    private String reqTransferType;

    private String attemptNo;

    private String uniqueResponseNo;

    private String requestReferenceNo;

    private String statusCode;

    private String partnerCode;

    public String getReqTransferType() {
        return reqTransferType;
    }

    public void setReqTransferType(String reqTransferType) {
        this.reqTransferType = reqTransferType;
    }

    public String getAttemptNo() {
        return attemptNo;
    }

    public void setAttemptNo(String attemptNo) {
        this.attemptNo = attemptNo;
    }

    public String getUniqueResponseNo() {
        return uniqueResponseNo;
    }

    public void setUniqueResponseNo(String uniqueResponseNo) {
        this.uniqueResponseNo = uniqueResponseNo;
    }

    public String getRequestReferenceNo() {
        return requestReferenceNo;
    }

    public void setRequestReferenceNo(String requestReferenceNo) {
        this.requestReferenceNo = requestReferenceNo;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }
}
