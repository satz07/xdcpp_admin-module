package com.adminremit.masters.enums;

public enum PurposeType {

    SELF("Self"), FAMILYFRIENDS("Family/Friends"), OTHERS("Others");

    private final String code;

    private PurposeType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
