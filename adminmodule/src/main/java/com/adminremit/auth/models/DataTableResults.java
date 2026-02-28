package com.adminremit.auth.models;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class DataTableResults<T> {
	/** The draw. */
	private int draw;
	
	/** The records filtered. */
	private long recordsFiltered;
	
	/** The records total. */
	private long recordsTotal;

	/** The list of data objects. */
	@SerializedName("data")
	List<T> data;
	
	public String getJson() {
		return new Gson().toJson(this);
	}	

	public int getDraw() {
		return draw;
	}



	public void setDraw(int draw) {
		this.draw = draw;
	}



	public long getRecordsFiltered() {
		return recordsFiltered;
	}



	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}



	public long getRecordsTotal() {
		return recordsTotal;
	}



	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}



	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	
}
