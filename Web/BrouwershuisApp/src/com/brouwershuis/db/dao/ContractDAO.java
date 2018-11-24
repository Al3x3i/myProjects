package com.brouwershuis.db.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.brouwershuis.db.model.Contract;

@Stateless
public class ContractDAO {

	@PersistenceContext
	protected EntityManager em;

	public List<Contract> findAll() {
		List<Contract> contracts = new ArrayList<Contract>();
		try {
			TypedQuery<Contract> query = em.createQuery("Select c FROM Contract c", Contract.class);
			contracts.addAll(query.getResultList());

		} catch (Exception ex) {

		}
		return contracts;
	}

	public Contract finByName(String name) {
		try {

			TypedQuery<Contract> query = em.createQuery("SELECT c FROM Contract c WHERE c.name =:arg1", Contract.class);

			query.setParameter("arg1", name);
			Contract c = query.getSingleResult();
			return c;
		} catch (Exception ex) {
			String g = ex.getMessage();
		}
		return null;
	}

}
