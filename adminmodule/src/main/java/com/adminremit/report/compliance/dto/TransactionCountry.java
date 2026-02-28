package com.adminremit.report.compliance.dto;

public class TransactionCountry {
	
	private String userTxnCountry;
	private String txnIpBasedCountry;
	public String getUserTxnCountry() {
		return userTxnCountry;
	}
	public void setUserTxnCountry(String userTxnCountry) {
		this.userTxnCountry = userTxnCountry;
	}
	public String getTxnIpBasedCountry() {
		return txnIpBasedCountry;
	}
	public void setTxnIpBasedCountry(String txnIpBasedCountry) {
		this.txnIpBasedCountry = txnIpBasedCountry;
	}
}
