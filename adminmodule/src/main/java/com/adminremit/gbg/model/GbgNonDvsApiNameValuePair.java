package com.adminremit.gbg.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="gbg_non_dvs_name_value_pair")
public class GbgNonDvsApiNameValuePair {
	
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
	
	@Column(name = "name", length = 1000)
    private String name;
	
	@Column(name = "value", length = 1000)
    private String value;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gbg_non_dvs_api_id", referencedColumnName = "id")
	private GbgNonDvsApiRequest gbgNonDvsApiRequest;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public GbgNonDvsApiRequest getGbgNonDvsApiRequest() {
		return gbgNonDvsApiRequest;
	}

	public void setGbgNonDvsApiRequest(GbgNonDvsApiRequest gbgNonDvsApiRequest) {
		this.gbgNonDvsApiRequest = gbgNonDvsApiRequest;
	}
}
