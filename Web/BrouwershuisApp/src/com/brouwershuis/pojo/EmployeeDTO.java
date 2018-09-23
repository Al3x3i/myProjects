package com.brouwershuis.pojo;

import java.util.List;

public class EmployeeDTO {
	int sEcho;
	int iTotalRecords;
	int itotalDisplayRecords;

	List<EmloyeePojo> aaData;

	public int getsEcho() {
		return sEcho;
	}

	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getItotalDisplayRecords() {
		return itotalDisplayRecords;
	}

	public void setItotalDisplayRecords(int itotalDisplayRecords) {
		this.itotalDisplayRecords = itotalDisplayRecords;
	}

	public List<EmloyeePojo> getAaData() {
		return aaData;
	}

	public void setAaData(List<EmloyeePojo> aaData) {
		this.aaData = aaData;
	}
}
