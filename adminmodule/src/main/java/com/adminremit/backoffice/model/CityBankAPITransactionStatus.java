package com.adminremit.backoffice.model;

public enum CityBankAPITransactionStatus {
	    INITIATED("0"),
	    UNPAID("UN-PAID"),
	    PAID("PAID"),
	    CANCEL("CANCEL"),
	    HOLD("HOLD"),
	    POST("POST");
		
		
		private String transactionStatus;
		
		private CityBankAPITransactionStatus(String transactionStatus) {
			this.transactionStatus = transactionStatus;
		}

		public String getTransactionStatus() {
			return transactionStatus;
		}

		public void setTransactionStatus(String transactionStatus) {
			this.transactionStatus = transactionStatus;
		}
	}


