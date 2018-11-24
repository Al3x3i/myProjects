package com.brouwershuis.db.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import com.brouwershuis.db.model.ContractHours;

@Stateless
public class ContractHoursDAO {

	@PersistenceContext
	protected EntityManager em;

	public boolean addContracHours(int employeeId, Date startDate, Date endDate, int fixedTime) {

		int output = 0;

		StoredProcedureQuery query = this.em.createStoredProcedureQuery("P_ADDCONTRACT_HOURS");
		query.registerStoredProcedureParameter("s_date", Date.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("e_date", Date.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("f_time", Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("e_fk", Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("return_value", Integer.class, ParameterMode.OUT);

		query.setParameter("s_date", startDate, TemporalType.DATE);
		query.setParameter("e_date", endDate, TemporalType.DATE);
		query.setParameter("f_time", fixedTime);
		query.setParameter("e_fk", employeeId);

		query.execute();
		output = (int) query.getOutputParameterValue("return_value");

		if (output > 0) {
			return true;
		}

		return false;
	}

	public boolean updateContracHours(int id, int employeeId, Date startDate, Date endDate, int fixedTime) {

		ContractHours c = em.find(ContractHours.class, id);
		if (c != null && c.getEmployee().getId() == employeeId && c.getStartDate().compareTo(startDate) == 0
				&& c.getEndDate().compareTo(endDate) == 0) {
			c.setFixedTime(fixedTime);
			em.flush();
			return true;
		}
		return false;
	}

	public boolean deleteSlaapDienstData(int id, int employeeId, Date startDate, Date endDate) {
		ContractHours c = em.find(ContractHours.class, id);

		if (c != null && c.getEmployee().getId() == employeeId && c.getStartDate().compareTo(startDate) == 0
				&& c.getEndDate().compareTo(endDate) == 0) {
			em.remove(c);
			em.flush();
		}

		if (em.contains(c)) {
			return false;
		}
		return true;
	}

	public List<ContractHours> findBetweenDates(int employeeId, Date begin, Date end) {

		String sql = "SELECT c FROM ContractHours c WHERE c.employee.id =:arg1 AND c.startDate >= function ('DATE_FORMAT', :arg2, '%Y-%m-%d') AND c.endDate <=  function ('DATE_FORMAT', :arg3, '%Y-%m-%d')";

		TypedQuery<ContractHours> query = em.createQuery(sql, ContractHours.class);

		query.setParameter("arg1", employeeId);
		query.setParameter("arg2", begin);
		query.setParameter("arg3", end);

		List<ContractHours> items = query.getResultList();

		return items;
	}
}
