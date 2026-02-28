package com.adminremit.report.compliance.dto;

public class RelationShip {
	
	private String relationShip;
	private String remitterName;
	private String receipientName;
	
	public String getRelationShip() {
		return relationShip;
	}
	public void setRelationShip(String relationShip) {
		this.relationShip = relationShip;
	}
	public String getRemitterName() {
		return remitterName;
	}
	public void setRemitterName(String remitterName) {
		this.remitterName = remitterName;
	}
	public String getReceipientName() {
		return receipientName;
	}
	public void setReceipientName(String receipientName) {
		this.receipientName = receipientName;
	}
}
