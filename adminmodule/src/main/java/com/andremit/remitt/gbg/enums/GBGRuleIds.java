package com.andremit.remitt.gbg.enums;

public enum GBGRuleIds {
    RuleSetN("Rule N"),RuleSetO("Rule O");
	
	private final String code;
	
	private GBGRuleIds(String code) {
        this.code = code;
    }

	public String getCode() {
		return code;
	}
}
