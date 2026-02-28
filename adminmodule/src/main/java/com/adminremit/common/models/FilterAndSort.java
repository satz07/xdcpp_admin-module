package com.adminremit.common.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class FilterAndSort implements Serializable {
	
	private static final long serialVersionUID = 3044292675014008479L;
	private Set<OrderSet> orders = new HashSet<>();
	private Set<SearchCriteria> criterias = new HashSet<>();
		
	public FilterAndSort() {
		super();
	}

	public FilterAndSort(Set<OrderSet> orders, Set<SearchCriteria> criterias) {
		super();
		this.orders = orders;
		this.criterias = criterias;
	}

	public Set<OrderSet> getOrders() {
		return orders;
	}

	public void setOrders(Set<OrderSet> orders) {
		this.orders = orders;
	}

	public Set<SearchCriteria> getCriterias() {
		return criterias;
	}

	public void setCriterias(Set<SearchCriteria> criterias) {
		this.criterias = criterias;
	}
}
