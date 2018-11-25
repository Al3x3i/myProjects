package com.brouwershuis.db.dao;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import com.brouwershuis.db.model.Employee;
import com.brouwershuis.db.model.WorkSchedule;

@Stateless
public class WorkScheduleDAO {

	@PersistenceContext
	protected EntityManager em;

	public boolean addTableRecord(Time start, Time end, Date date, Integer employeeId, String shiftName,
			String comments) throws Exception {
		int output = 0;

		StoredProcedureQuery query = em.createStoredProcedureQuery("P_ADDWORK_SCHEDULE");

		query.registerStoredProcedureParameter("arg1", Time.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("arg2", Time.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("arg3", Date.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("arg4", Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("arg5", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("arg6", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("arg7", Integer.class, ParameterMode.OUT);

		query.setParameter("arg1", start);
		query.setParameter("arg2", end);
		query.setParameter("arg3", date);
		query.setParameter("arg4", employeeId);
		query.setParameter("arg5", shiftName);
		query.setParameter("arg6", comments);

		query.execute();
		output = (int) query.getOutputParameterValue("arg7");

		if (output > 0) {
			return true;
		}
		return false;
	}

	public boolean deleteSlaapDienstData(int id, Date date, int employeeId) {

		WorkSchedule w = em.find(WorkSchedule.class, id);

		if (w != null) {
			em.remove(w);
			em.flush();
		}

		if (em.contains(w)) {
			return false;
		}
		return true;
	}

	public boolean updateSlaapDienstData(int id, int employeeId) {

		WorkSchedule w = em.find(WorkSchedule.class, id);
		if (w != null) {
			w.setEmployee(new Employee(employeeId));
			em.flush();
			return true;
		}
		return false;
	}

	public List<WorkSchedule> getTableData(Date start, Date end) {
		TypedQuery<WorkSchedule> query = em.createQuery(
				"SELECT w FROM WorkSchedule w WHERE (w.weekDate BETWEEN :arg1 AND :arg2)", WorkSchedule.class);
		query.setParameter("arg1", start, TemporalType.DATE);
		query.setParameter("arg2", end, TemporalType.DATE);

		List<WorkSchedule> items = query.getResultList();
		return items;
	}

	public WorkSchedule getTableDataById(int id) {
		WorkSchedule result = em.find(WorkSchedule.class, id);
		return result;
	}

	public List<WorkSchedule> getTableDataByDateAndShift(Date start, Date end, String shiftName) {
		TypedQuery<WorkSchedule> query = em.createQuery(
				"SELECT w FROM WorkSchedule w WHERE (w.weekDate BETWEEN :arg1 AND :arg2) AND w.shift.name = :arg3 ORDER BY w.weekDate ASC",
				WorkSchedule.class);
		query.setParameter("arg1", start, TemporalType.DATE);
		query.setParameter("arg2", end, TemporalType.DATE);
		query.setParameter("arg3", shiftName);

		List<WorkSchedule> items = query.getResultList();
		return items;
	}

	public boolean updateTableRecord(int rowId, Date date, Integer employeeId, Time startTime, Time endTime,
			String comments) {

		WorkSchedule record = em.find(WorkSchedule.class, rowId);

		if (record != null) {

			if (employeeId != null)
				record.setEmployee(new Employee(employeeId));
			else
				record.setEmployee(null);
			record.setWeekDate(date);
			record.setStartTime(startTime);
			record.setEndTime(endTime);
			record.setComments(comments);
			em.merge(record);
			em.flush();
			return true;
		}
		return false;
	}

	public boolean deleteTableRecord(int rowId, Date date, Integer employeeId) {
		WorkSchedule record = em.find(WorkSchedule.class, rowId);

		if (record != null) {

			if (record.getWeekDate().compareTo(date) == 0) {

				if ((record.getEmployee() != null && record.getEmployee().getId() == employeeId)
						|| (record.getEmployee() == null && employeeId == null)) {
					em.remove(record);
					em.flush();

					if (!em.contains(record)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void getTotalWorkingHoursByWeek(Date start, Date end) {
		String sql = "SELECT w.EMPLOYEE_FK, e.DISPLAYNAME, TIME_FORMAT(SUM(TIMEDIFF(w.ENDTIME,w.StartTime)), '%H:%i') "
				+ "FROM  work_schedule w " + "JOIN employee " + "e ON e.id = w.EMPLOYEE_FK "
				+ "WHERE w.weekdate >= '?start' AND w.weekdate <= '?end' GROUP BY w.EMPLOYEE_FK";

		Query query = em.createNativeQuery(sql);
		query.setParameter("arg1", start, TemporalType.DATE);
		query.setParameter("arg2", end, TemporalType.DATE);

		query.getResultList();
	}
}
