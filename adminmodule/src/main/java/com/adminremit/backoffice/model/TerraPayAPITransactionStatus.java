package com.adminremit.backoffice.model;

public enum TerraPayAPITransactionStatus {
		INITIATED("I"),
		UNPAID("P"),
	    PAID("S"),
	    CANCEL("F"),
		RETRY("R");
		
	 	private String transactionStatus;
		
		private TerraPayAPITransactionStatus(String transactionStatus) {
			this.transactionStatus = transactionStatus;
		}

		public String getTransactionStatus() {
			return transactionStatus;
		}

		public void setTransactionStatus(String transactionStatus) {
			this.transactionStatus = transactionStatus;
		}
	}


