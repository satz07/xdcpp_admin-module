package com.adminremit.beneficiary.enums;

public enum AccountType {
BAC("Bank Credit"),CAS("Cash"),WAL("Wallet"),UPI("UPI"),BANK_ACCOUNT("Bank Account Deposit");
    
    private final String code;

    private AccountType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
