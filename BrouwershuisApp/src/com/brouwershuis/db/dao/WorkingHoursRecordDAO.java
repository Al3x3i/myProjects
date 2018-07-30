package com.brouwershuis.db.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.brouwershuis.db.model.WorkingHoursRecord;

@Stateless
public class WorkingHoursRecordDAO {

	private static final Logger LOGGER = Logger.getLogger(WorkingHoursRecordDAO.class);

	@PersistenceContext
	private EntityManager em;

	public WorkingHoursRecord findWorkingHoursRecord(int id, int employeeId, Date recordDate) {

		WorkingHoursRecord recordData = em.find(WorkingHoursRecord.class, id);
		if (recordData != null) {
			if (recordData.getEmployee().getId() == employeeId && recordData.getWeekDate().compareTo(recordDate) == 0)
				return recordData;
		}
		return null;
	}

	public WorkingHoursRecord findByDateAndEmployee(int employeeId, Date recordDate) {

		String query_1 = "SELECT w FROM WorkingHoursRecord w WHERE w.weekDate =:arg1 AND w.employee.id =:arg2";

		TypedQuery<WorkingHoursRecord> query = em.createQuery(query_1, WorkingHoursRecord.class);
		query.setParameter("arg1", recordDate);
		query.setParameter("arg2", employeeId);
		query.setMaxResults(1);

		List<WorkingHoursRecord> recordObjects = query.getResultList();

		if (recordObjects.size() > 0)
			return recordObjects.get(0);

		return null;
	}

	public List<WorkingHoursRecord> findBetweenDates(int employeeId, Date begin, Date end) {

		String sql = "SELECT c FROM ContractHours c WHERE c.employee.id =:arg1 AND c.startDate >= function ('DATE_FORMAT', :arg2, '%Y-%m-%d') AND c.endDate <=  function ('DATE_FORMAT', :arg3, '%Y-%m-%d')";

		String ss = "SELECT c FROM WorkingHoursRecord c WHERE c.employee.id =:arg1 AND c.weekDate >= function ('DATE_FORMAT', :arg2, '%Y-%m-%d') AND c.weekDate <= function ('DATE_FORMAT', :arg3, '%Y-%m-%d')";
		TypedQuery<WorkingHoursRecord> query = em.createQuery(ss, WorkingHoursRecord.class);

		query.setParameter("arg1", employeeId);
		query.setParameter("arg2", begin);
		query.setParameter("arg3", end);

		List<WorkingHoursRecord> items = query.getResultList();

		return items;
	}

	public boolean addWorkingHoursRecord(WorkingHoursRecord newRecord) {
		em.persist(newRecord);
		em.flush();

		if (newRecord.getId() == 0) {
			return false;
		}
		return true;
	}

	public boolean updateWorkingHoursRecord(WorkingHoursRecord newRecord) {
		em.persist(newRecord);
		em.flush();

		if (!em.contains(newRecord)) {
			return false;
		}
		return true;
	}

	// public boolean updateVacationHours(int recordId, Date recordDate, Date
	// recordTime,int employeeId) {
	// em.persist(newRecord);
	// em.flush();
	//
	// if (!em.contains(newRecord)) {
	// return false;
	// }
	// return true;
	// }
	//
	// public boolean updateWorkingHours(WorkingHoursRecord newRecord) {
	// em.persist(newRecord);
	// em.flush();
	//
	// if (!em.contains(newRecord)) {
	// return false;
	// }
	// return true;
	// }

	// public boolean addVacationHours(int employeeId, Date recordDate, Time
	// recordTime) {
	//
	// WorkingHoursRecord w = new WorkingHoursRecord();
	// w.setEmployee(new Employee(employeeId));
	// w.setWeekDate(recordDate);
	// w.setVacationHours(recordTime);
	//
	// em.persist(w);
	// em.flush();
	//
	// if (!em.contains(w)) {
	// return false;
	// }
	// return true;
	// }
	//

	// public boolean addWorkingHours(int employeeId, Date recordDate, Time
	// recordTime) {
	//
	// WorkingHoursRecord w = new WorkingHoursRecord();
	// w.setEmployee(new Employee(employeeId));
	// w.setWeekDate(recordDate);
	// w.setWorkedHours(recordTime);
	//
	// em.persist(w);
	// em.flush();
	//
	// if (!em.contains(w)) {
	// return false;
	// }
	// return true;
	// }

	// public void updateVacationHours(recordId, recordDate, recordTime,
	// employeeId, recordTime) {
	// record.setVacationHours(time);
	// em.persist(record);
	// em.flush();
	// }
	//
	// public void updateWorkingHours(WorkingHoursRecord record, Time time) {
	// record.setWorkedHours(time);
	// em.persist(record);
	// em.flush();
	// }

	public boolean deleteWorkingHoursRecord(WorkingHoursRecord recordDate) {
		WorkingHoursRecord temp = em.merge(recordDate);
		em.remove(temp);
		em.flush();

		if (em.contains(recordDate)) {
			return false;
		}
		return true;
	}

}
