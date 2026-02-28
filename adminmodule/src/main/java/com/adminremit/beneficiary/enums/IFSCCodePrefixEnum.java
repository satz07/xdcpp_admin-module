package com.adminremit.beneficiary.enums;

public enum IFSCCodePrefixEnum {
	
	YES_BANK("YESB");
	
	private String ifscPrefix;
	
	private IFSCCodePrefixEnum(String ifscPrefix) {
		this.ifscPrefix = ifscPrefix;
	}

	public String getIfscPrefix() {
		return ifscPrefix;
	}

	public void setIfscPrefix(String ifscPrefix) {
		this.ifscPrefix = ifscPrefix;
	}	
}
