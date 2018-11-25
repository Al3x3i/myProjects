package com.brouwershuis.db.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.brouwershuis.db.model.EnumRoles;
import com.brouwershuis.db.model.Role;

@Stateless
public class RoleDAO {

	private static final Logger LOGGER = Logger.getLogger(RoleDAO.class);
	
	@PersistenceContext
	protected EntityManager em;

	public Role findRoleByName(EnumRoles enumRole) {

		try {

			TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r WHERE r.name ='" + enumRole.toString() + "'",
					Role.class);
			Role role = query.getSingleResult();
			return role;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return null;

	}

}
