package com.brouwershuis.db.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "employee")
@NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")
public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 50)
	private String address;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Column(length = 50)
	private String firstName;

	@Column(length = 50)
	private String lastName;

	@Column(length = 50)
	private String displayName;

	@Column(length = 50)
	private String gender;

	private boolean enabled;

	// bi-directional many-to-one association to Vacation
	@OneToMany(mappedBy = "employee", targetEntity = WorkingHoursRecord.class, orphanRemoval = true)
	@JsonBackReference
	private Set<WorkingHoursRecord> vacations;

	// bi-directional one-to-one association to User
	@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
	private User user;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CONTRACT_FK")
	private Contract contract;

	@OneToMany(mappedBy = "employee", targetEntity = ContractHours.class, orphanRemoval = true)
	@JsonBackReference
	private Set<ContractHours> contractHours;
	
	// bi-directional many-to-one association to Vacation
	@OneToMany(mappedBy = "employee", targetEntity = WorkSchedule.class)
	@JsonBackReference
	private Set<WorkSchedule> workSchedule;

	@Column(name = "TIMESTAMP", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timesTamp;
	
	public Employee() {
	}

	public Employee(int id) {
		this.id = id;
	}

	public synchronized Set<ContractHours> getContractHours() {
		return contractHours;
	}

	public synchronized void setContractHours(Set<ContractHours> contractHours) {
		this.contractHours = contractHours;
	}

	public Set<WorkSchedule> getWorkSchedule() {
		return workSchedule;
	}

	public void setWorkSchedule(Set<WorkSchedule> workSchedule) {
		this.workSchedule = workSchedule;
	}

//	public WorkingHoursRecord addVacation(WorkingHoursRecord vacation) {
//		getVacations().add(vacation);
//		vacation.setEmployee(this);
//
//		return vacation;
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<WorkingHoursRecord> getVacations() {
		return vacations;
	}

	public void setVacations(Set<WorkingHoursRecord> vacations) {
		this.vacations = vacations;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public synchronized Date getTimesTamp() {
		return timesTamp;
	}

	public synchronized void setTimesTamp(Date timesTamp) {
		this.timesTamp = timesTamp;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}