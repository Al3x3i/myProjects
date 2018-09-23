package com.brouwershuis.db.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.gson.annotations.Expose;

@Entity
@Table(name = "shift")
public class Shift implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private int id;
	@Expose
	private String name;
	
	@Column(name="TIMESTAMP", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timesTamp;
	
	@OneToMany(mappedBy ="shift", targetEntity  = WorkSchedule.class)
	@JsonBackReference
	private Set<WorkSchedule> workSchedule;
	
	public synchronized Set<WorkSchedule> getWorkSchedule() {
		return workSchedule;
	}
	public synchronized void setWorkSchedule(Set<WorkSchedule> workSchedule) {
		this.workSchedule = workSchedule;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public synchronized Date getTimesTamp() {
		return timesTamp;
	}
	public synchronized void setTimesTamp(Date timesTamp) {
		this.timesTamp = timesTamp;
	}
	

	
}
