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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "working_hours_record")
@NamedQuery(name = "Vacation.findAll", query = "SELECT v FROM WorkingHoursRecord v")
public class WorkingHoursRecord implements Serializable {

	public static final int WORK_TYPE = 1;
	public static final int VACATION_TYPE = 2;

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date weekDate;

	@Temporal(TemporalType.TIME)
	private Date vacationHours;

	@Temporal(TemporalType.TIME)
	private Date workedHours;

	// bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name = "EMPLOYEEID", nullable = false)
	@JsonManagedReference
	private Employee employee;
	
	@Column(name="TIMESTAMP", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timesTamp;

	public WorkingHoursRecord() {
	}

	public WorkingHoursRecord(int id, Date weekDate, Date vacationHours, Date workedHours, Employee employee) {
		super();
		this.id = id;
		this.weekDate = weekDate;
		this.vacationHours = vacationHours;
		this.workedHours = workedHours;
		this.employee = employee;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getVacationHours() {
		return vacationHours;
	}

	public void setVacationHours(Date vacationHours) {
		this.vacationHours = vacationHours;
	}

	public Date getWorkedHours() {
		return workedHours;

	}

	public void setWorkedHours(Date workedHours) {
		this.workedHours = workedHours;
	}

	public Date getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(Date weekDate) {
		this.weekDate = weekDate;
	}

	public synchronized Date getTimesTamp() {
		return timesTamp;
	}

	public synchronized void setTimesTamp(Date timesTamp) {
		this.timesTamp = timesTamp;
	}
}
