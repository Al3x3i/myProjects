package com.brouwershuis.db.model;

import java.io.Serializable;
import java.sql.Time;
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
import javax.validation.constraints.NotNull;

import com.brouwershuis.helper.SqlTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "work_schedule")
public class WorkSchedule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date weekDate;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_FK", nullable = false)
	private Employee employee;

	// @JsonFormat(pattern="HH:mm",timezone= "UTC")
	// @Temporal(TemporalType.TIME)
	@JsonSerialize(using = SqlTimeSerializer.class)
	private Time startTime;

	// @JsonFormat(pattern="HH:mm")
	@JsonSerialize(using = SqlTimeSerializer.class)
	private Time endTime;

	@ManyToOne
	@JoinColumn(name = "SHIFT_FK", nullable = false)
	private Shift shift;

	@Column(columnDefinition = "VARCHAR(255)")
	private String comments;

	@Column(name = "TIMESTAMP", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timesTamp;

	public synchronized Shift getShift() {
		return shift;
	}

	public synchronized void setShift(Shift shift) {
		this.shift = shift;
	}

	public synchronized int getId() {
		return id;
	}

	public synchronized void setId(int id) {
		this.id = id;
	}

	public synchronized Date getWeekDate() {
		return weekDate;
	}

	public synchronized void setWeekDate(Date weekDate) {
		this.weekDate = weekDate;
	}

	public synchronized Employee getEmployee() {
		return employee;
	}

	public synchronized void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public synchronized Date getTimesTamp() {
		return timesTamp;
	}

	public synchronized void setTimesTamp(Date timesTamp) {
		this.timesTamp = timesTamp;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
}
