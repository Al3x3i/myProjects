package com.brouwershuis.service;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.brouwershuis.db.dao.UserDAO;
import com.brouwershuis.db.model.Employee;
import com.brouwershuis.db.model.Role;
import com.brouwershuis.db.model.User;

@Service
public class UserService {

	private static final Logger LOGGER = Logger.getLogger(UserService.class);

	@Inject
	UserDAO userDao;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User addUser(Employee emp, String login, String psw, Role role) {
		try {
			User user = new User();
			user.setUsername(login);
			user.setPassword(bCryptPasswordEncoder.encode(psw));
			Set<Role> roles = new HashSet<Role>();
			roles.add(role);
			user.setRoles(roles);
			user.setEmployee(emp);
			User u = userDao.addUser(user);
			if (u != null) {
				return user;
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return null;
	}

	public boolean removeUser(User user) {
		return userDao.removeUser(user);
	}

	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}
}
