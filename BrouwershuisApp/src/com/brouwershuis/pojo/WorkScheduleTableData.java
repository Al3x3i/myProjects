package com.brouwershuis.pojo;

public class WorkScheduleTableData {

	private String id;
	private String employeeId;
	private String date;
	private String displayName;
	private String startTime;
	private String endTime;
	private String shift;
	private String comments;

	public WorkScheduleTableData() {

	}

	public WorkScheduleTableData(String id, String employeeId, String date, String displayName, String comments, String startTime, String endTime) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.date = date;
		this.displayName = displayName;
		this.comments = comments;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
