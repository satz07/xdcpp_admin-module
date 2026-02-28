package com.adminremit.report.enums.business;

public enum CompanySubCategory {

    IT_Consulting_Services("IT Consulting Services");

    private final String code;

    private CompanySubCategory(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
