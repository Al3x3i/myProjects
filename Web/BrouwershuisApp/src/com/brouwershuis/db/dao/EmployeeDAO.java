package com.brouwershuis.db.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.brouwershuis.db.model.Contract;
import com.brouwershuis.db.model.Employee;
import com.brouwershuis.helper.Helper;
import com.brouwershuis.pojo.EmloyeePojo;

@Stateless
public class EmployeeDAO {

	private static final Logger LOGGER = Logger.getLogger(EmployeeDAO.class);

	@PersistenceContext
	protected EntityManager em;

	public EmployeeDAO() {
	}

	public List<Employee> findAll() {
		try {
			TypedQuery<Employee> query = em.createQuery("SELECT g FROM Employee g", Employee.class);
			List<Employee> items = query.getResultList();

			return items;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return null;
	}

	public Employee findEmployee(int id) {
		try {
			TypedQuery<Employee> query = em.createQuery("SELECT g FROM Employee g WHERE g.id =:arg1", Employee.class);
			query.setParameter("arg1", id);
			Employee e = query.getSingleResult();
			Object u = e.getUser();

			return e;

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return null;
	}

	public Employee addEmployee(EmloyeePojo emp) {
		try {
			Employee newEmployee = new Employee();
			newEmployee.setAddress(emp.getAddress());
			newEmployee.setGender(emp.getGender());
			newEmployee.setEnabled(emp.isEmployeeActive());
			newEmployee.setDateOfBirth(Helper.formatDate(emp.getDateOfBirth()));
			newEmployee.setFirstName(emp.getFirstName());
			newEmployee.setLastName(emp.getLastName());

			String dispName = emp.getAliasName();
			if (dispName == null || dispName.equals("")) {
				dispName = emp.getFirstName().charAt(0) + "." + emp.getLastName();
			}

			newEmployee.setDisplayName(dispName);

			Contract contract = new Contract();
			contract.setId(Integer.valueOf(emp.getContract()));
			newEmployee.setContract(contract);

			em.persist(newEmployee);
			em.flush();
			em.clear();

			if (newEmployee.getId() != 0) {
				return newEmployee;
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		LOGGER.warn("New employee was not added");
		return null;
	}

	public boolean deleteEmployee(int id) {
		try {
			Employee emp = em.find(Employee.class, id);

			if (emp != null) {
				em.remove(emp);
				em.flush();

				if (!em.contains(emp)) {
					return true;
				}
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		LOGGER.warn("Employee was not deleted");
		return false;
	}

	public boolean updateEmployee(Employee emp) {
		em.merge(emp);
		em.flush();
		return true;
	}
}
