package com.brouwershuis.pojo;

public class WorkingHoursRecordPojo {

	String employeeId;
	HoursCellData[] hoursCellData;
	ContractHoursData[] contractHoursData;

	public synchronized String getEmployeeId() {
		return employeeId;
	}

	public synchronized void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public synchronized HoursCellData[] getHoursCellData() {
		return hoursCellData;
	}

	public synchronized void setHoursCellData(HoursCellData[] hoursCellData) {
		this.hoursCellData = hoursCellData;
	}

	public synchronized ContractHoursData[] getContractHoursData() {
		return contractHoursData;
	}

	public synchronized void setContractHoursData(ContractHoursData[] contractHoursData) {
		this.contractHoursData = contractHoursData;
	}

	public WorkingHoursRecordPojo() {

	}

	public class HoursCellData {
		String id;
		String date;
		String hours;
		String hoursType;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getHoursType() {
			return hoursType;
		}

		public void setHoursType(String hoursType) {
			this.hoursType = hoursType;
		}

		public String getHours() {
			return hours;
		}

		public void setHours(String hours) {
			this.hours = hours;
		}
	}

	public class ContractHoursData {

		private String id;
		private String startDate;
		private String endDate;
		private String fixedTime;

		public ContractHoursData() {
		}

		public ContractHoursData(String id, String startDate, String endDate, String fixedTime) {
			super();
			this.id = id;
			this.startDate = startDate;
			this.endDate = endDate;
			this.fixedTime = fixedTime;
		}

		public synchronized String getId() {
			return id;
		}

		public synchronized void setId(String id) {
			this.id = id;
		}

		public synchronized String getStartDate() {
			return startDate;
		}

		public synchronized void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public synchronized String getEndDate() {
			return endDate;
		}

		public synchronized void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public synchronized String getFixedTime() {
			return fixedTime;
		}

		public synchronized void setFixedTime(String fixedTime) {
			this.fixedTime = fixedTime;
		}

	}
}
