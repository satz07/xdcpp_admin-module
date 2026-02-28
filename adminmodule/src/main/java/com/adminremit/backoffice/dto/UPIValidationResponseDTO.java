package com.adminremit.backoffice.dto;

public class UPIValidationResponseDTO {

    private String yblRefNo;

    private String virtualAddress;

    private String maskName;

    private String status;

    private String statusDesc;

    private String beneficiaryBankIFSC;

    public String getYblRefNo() {
        return yblRefNo;
    }

    public void setYblRefNo(String yblRefNo) {
        this.yblRefNo = yblRefNo;
    }

    public String getVirtualAddress() {
        return virtualAddress;
    }

    public void setVirtualAddress(String virtualAddress) {
        this.virtualAddress = virtualAddress;
    }

    public String getMaskName() {
        return maskName;
    }

    public void setMaskName(String maskName) {
        this.maskName = maskName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getBeneficiaryBankIFSC() {
        return beneficiaryBankIFSC;
    }

    public void setBeneficiaryBankIFSC(String beneficiaryBankIFSC) {
        this.beneficiaryBankIFSC = beneficiaryBankIFSC;
    }
}
