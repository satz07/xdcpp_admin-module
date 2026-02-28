package com.adminremit.report.enums.business;

public enum CompanyCategory {

    Consulting_Services("Consulting Services");

    private final String code;

    private CompanyCategory(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
