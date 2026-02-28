package com.adminremit.report.vo.business;

import java.util.List;

public class Director {
	
	private String registrationDoneByDirector;	
	private List<Directordetails> directordetailsList;	

	public String getRegistrationDoneByDirector() {
		return registrationDoneByDirector;
	}

	public void setRegistrationDoneByDirector(String registrationDoneByDirector) {
		this.registrationDoneByDirector = registrationDoneByDirector;
	}

	public List<Directordetails> getDirectordetailsList() {
		return directordetailsList;
	}

	public void setDirectordetailsList(List<Directordetails> directordetailsList) {
		this.directordetailsList = directordetailsList;
	}	
}
