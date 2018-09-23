package com.brouwershuis.db.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.brouwershuis.db.model.Shift;

@Stateless
public class ShiftDAO {
	@PersistenceContext
	protected EntityManager em;

	public List<Shift> findAll() {
		List<Shift> shifts = new ArrayList<Shift>();
		try {
			TypedQuery<Shift> query = em.createQuery("Select s FROM Shift s", Shift.class);
			shifts.addAll(query.getResultList());

		} catch (Exception ex) {

		}
		return shifts;
	}
}
