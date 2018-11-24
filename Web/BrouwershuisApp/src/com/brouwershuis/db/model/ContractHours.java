package com.brouwershuis.db.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "contract_hours")
public class ContractHours implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE")
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE")
	private Date endDate;

	// Store time in seconds
	@Column(name = "FIXED_TIME")
	private int fixedTime;

	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_FK", nullable = false)
	private Employee employee;

	@Column(name = "TIMESTAMP", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timesTamp;

	public synchronized Date getTimesTamp() {
		return timesTamp;
	}

	public synchronized void setTimesTamp(Date timesTamp) {
		this.timesTamp = timesTamp;
	}

	public synchronized int getId() {
		return id;
	}

	public synchronized void setId(int id) {
		this.id = id;
	}

	public synchronized Date getStartDate() {
		return startDate;
	}

	public synchronized void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public synchronized Date getEndDate() {
		return endDate;
	}

	public synchronized void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public synchronized int getFixedTime() {
		return fixedTime;
	}

	public synchronized void setFixedTime(int fixedTime) {
		this.fixedTime = fixedTime;
	}

	public synchronized Employee getEmployee() {
		return employee;
	}

	public synchronized void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
