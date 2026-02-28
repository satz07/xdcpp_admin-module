package com.adminremit.report.enums.business;

public enum CompanyType {

    PRIVATE("Private"),PUBLIC("Public"),INDIVIDUAL("Ind"),OTHER_INCORPORATED_ENTITY("OIE");

    private final String code;

    private CompanyType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}