package com.adminremit.personaldetails.enums;

public enum MediCareCardTypes {

    G("Green Cards"),B("Blue Cards"),Y("Yellow Cards");

    private final String code;

    private MediCareCardTypes(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
