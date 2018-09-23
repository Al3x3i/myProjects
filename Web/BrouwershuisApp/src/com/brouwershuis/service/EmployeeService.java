package com.brouwershuis.service;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.brouwershuis.db.dao.EmployeeDAO;
import com.brouwershuis.db.dao.RoleDAO;
import com.brouwershuis.db.dao.UserDAO;
import com.brouwershuis.db.model.Contract;
import com.brouwershuis.db.model.Employee;
import com.brouwershuis.db.model.EnumRoles;
import com.brouwershuis.db.model.Role;
import com.brouwershuis.db.model.User;
import com.brouwershuis.helper.Helper;
import com.brouwershuis.pojo.EmloyeePojo;
import com.brouwershuis.security.SecurityServiceImpl;

@Service
public class EmployeeService {
	
	private static final Logger LOGGER = Logger.getLogger(EmployeeService.class);

	@Autowired
	UserService userService;

	@Inject
	EmployeeDAO employeeDao;

	@Inject
	UserDAO userDao;

	@Inject
	RoleDAO roleDao;

	@Transactional
	public boolean addEmployee(EmloyeePojo employeePojo) {

		Employee employee = employeeDao.addEmployee(employeePojo);

		if (employee != null) {
			EnumRoles selectedRole;
			if (employeePojo.getUsername() == null || employeePojo.getUsername().equals("") || employeePojo.getPassword() == null || employeePojo.getPassword().equals("")) {
				// create employee without making user account
				return true;
			} else if (employeePojo.isAdmin() == true) {
				selectedRole = EnumRoles.ROLE_ADMIN;
			} else {
				selectedRole = EnumRoles.ROLE_USER;
			}

			Role role = roleDao.findRoleByName(selectedRole);

			if (role != null) {
				User u = userService.addUser(employee, employeePojo.getUsername(), employeePojo.getPassword(), role);
				if (u !=null)
					//If I will not set then recent created entity is still detached
					employee.setUser(u);
				else{
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return false;
				}
			}
		}
		return true;
	}

	@Transactional
	public List<Employee> findAll() {
		return employeeDao.findAll();
	}

	@Transactional
	public Employee findEmployee(String id) {
		return employeeDao.findEmployee(Integer.valueOf(id));
	}

	@Transactional
	public boolean deleteEmployee(String id) {
		return employeeDao.deleteEmployee(Integer.valueOf(id));
	}

	@Transactional
	public boolean editEmployee(EmloyeePojo employeePojo) {

		Employee db_emp = employeeDao.findEmployee(Integer.valueOf(employeePojo.getDT_RowId()));
		
		if (db_emp != null) {
			db_emp.setAddress(employeePojo.getAddress());
			db_emp.setGender(employeePojo.getGender());
			db_emp.setEnabled(employeePojo.isEmployeeActive());
			db_emp.setDateOfBirth(Helper.formatDate(employeePojo.getDateOfBirth()));
			db_emp.setFirstName(employeePojo.getFirstName());
			db_emp.setLastName(employeePojo.getLastName());
			String dispName = employeePojo.getAliasName();
			if (dispName == null || dispName.equals("")) 
				dispName = employeePojo.getFirstName().charAt(0) +"."+ employeePojo.getLastName();
			
			db_emp.setDisplayName(dispName);

			Contract contract = new Contract();
			contract.setId(Integer.valueOf(employeePojo.getContract()));
			db_emp.setContract(contract);
			
			EnumRoles selectedRole;
			if (employeePojo.getUsername() == null || employeePojo.getUsername().equals("") || employeePojo.getPassword() == null || employeePojo.getPassword().equals("")) {
				
				if(db_emp.getUser() !=null){
					if(userService.removeUser(db_emp.getUser())){
						db_emp.setUser(null);
					}
				}
				return true;
			} else if (employeePojo.isAdmin() == true) {
				selectedRole = EnumRoles.ROLE_ADMIN;
			} else {
				selectedRole = EnumRoles.ROLE_USER;
			}

			Role role = roleDao.findRoleByName(selectedRole);

			if (role != null) {
				User temp_user = db_emp.getUser();
				
				if (temp_user != null && employeePojo.getUsername().equals(temp_user.getUsername())){
					if(userService.removeUser(db_emp.getUser())){
						db_emp.setUser(null);
					}
				}
				
				User u = userService.addUser(db_emp, employeePojo.getUsername(), employeePojo.getPassword(), role);
//				if (u != null)
//					//If I will not set then recent created entity is still detached
//					db_emp.setUser(u);
//				else{
//					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//					return false;
//				}
			}
			
			
			employeeDao.updateEmployee(db_emp);
			
			return true;
		}
		return false;
		
	}

}
