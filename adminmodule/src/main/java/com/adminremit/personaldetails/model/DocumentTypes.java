package com.adminremit.personaldetails.model;

public enum DocumentTypes {

    PASSPORT("passportdvs"),MEDICARE("medicaredvs"),DRIVERS_LICENCE("drivers_licence"),VISA("visadvs");

    private final String code;

    private DocumentTypes(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}

