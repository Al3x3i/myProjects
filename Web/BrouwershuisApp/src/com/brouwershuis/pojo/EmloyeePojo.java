package com.brouwershuis.pojo;

import java.io.Serializable;

public class EmloyeePojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String DT_RowId;
	private String firstName;
	private String lastName;
	private String aliasName;
	private String gender;
	private String address;
	private String dateOfBirth;
	private boolean employeeActive;
	private boolean admin;
	private String username;
	private String password;
	private String confirmPassword;
	private String contract;


	public EmloyeePojo() {

	}
	
	public EmloyeePojo(int dT_RowId, String firstName, String lastName, boolean employeeActive, String contract) {
		DT_RowId = String.valueOf(dT_RowId);
		this.firstName = firstName;
		this.lastName = lastName;
		this.employeeActive = employeeActive;
		this.contract = contract;
	}
	
	public String getDT_RowId() {
		return DT_RowId;
	}


	public void setDT_RowId(String dT_RowId) {
		DT_RowId = dT_RowId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getConfirmPassword() {
		return confirmPassword;
	}


	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}


	public String getContract() {
		return contract;
	}


	public void setContract(String contract) {
		this.contract = contract;
	}
	
	public synchronized boolean isAdmin() {
		return admin;
	}


	public synchronized void setAdmin(boolean admin) {
		this.admin = admin;
	}


	public synchronized boolean isEmployeeActive() {
		return employeeActive;
	}


	public synchronized void setEmployeeActive(boolean employeeActive) {
		this.employeeActive = employeeActive;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
}
