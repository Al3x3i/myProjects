package com.brouwershuis.db.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.brouwershuis.db.model.User;

@Stateless
public class UserDAO {
	
	private static final Logger LOGGER = Logger.getLogger(UserDAO.class);

	@PersistenceContext // (type = PersistenceContextType.TRANSACTION)
	protected EntityManager em;

	public User findByUsername(String username) {
		try {

			TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username =:arg1", User.class);

			query.setParameter("arg1", username);
			User e = query.getSingleResult();
			return e;

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return null;
	}

	public User addUser(User user) {

		try {
			em.persist(user);
			em.flush();
			if (user.getId() > 0) {
				return user;
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return null;
	}

	public boolean removeUser(User user) {

		try {
			em.remove(user);
			em.flush();

			if (!em.contains(user)) {
				return true;
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return false;
	}
}
