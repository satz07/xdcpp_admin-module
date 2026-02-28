package com.adminremit.common.models;

import java.io.Serializable;

public class OrderSet implements Serializable {
	
	private static final long serialVersionUID = 7865945241558076627L;
	private String direction;
	private String property;
	
	public OrderSet() {
		super();
	}
	
	public OrderSet(String direction, String property) {
		this.direction = direction;
		this.property = property;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
}
