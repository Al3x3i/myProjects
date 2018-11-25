package com.brouwershuis.db.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.brouwershuis.db.model.Shift;

@Stateless
public class ShiftDAO {
	
	private static final Logger LOGGER = Logger.getLogger(ShiftDAO.class);
	
	@PersistenceContext
	protected EntityManager em;

	public List<Shift> findAll() {
		List<Shift> shifts = new ArrayList<Shift>();
		try {
			TypedQuery<Shift> query = em.createQuery("Select s FROM Shift s", Shift.class);
			shifts.addAll(query.getResultList());

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return shifts;
	}
}
