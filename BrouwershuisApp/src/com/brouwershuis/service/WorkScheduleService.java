package com.brouwershuis.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.eclipse.persistence.oxm.record.FormattedWriterRecord;
import org.springframework.stereotype.Service;

import com.brouwershuis.db.dao.WorkScheduleDAO;
import com.brouwershuis.db.model.EnumShiftType;
import com.brouwershuis.db.model.WorkSchedule;
import com.brouwershuis.helper.Helper;
import com.brouwershuis.pojo.WorkScheduleTableData;

@Service
public class WorkScheduleService {

	private static final Logger LOGGER = Logger.getLogger(WorkScheduleService.class);

	@Inject
	WorkScheduleDAO workScheduleDAO;

	@Transactional
	public boolean addSlaapDienst(List<WorkScheduleTableData> itemsList) {
		boolean result = true;

		Date date;
		try {

			for (WorkScheduleTableData updateData : itemsList) {
				int employeeId = Integer.valueOf(updateData.getEmployeeId());
				date = Helper.formatDateCatchExcpetion(updateData.getDate());
				if (updateData.getId() == null) {
					result = workScheduleDAO.addTableRecord(null, null, date, employeeId, EnumShiftType.SHIFT_SLAAPDIENST.toString(), null);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@Transactional
	public boolean addSchakelDienst(List<WorkScheduleTableData> itemsList) {
		boolean result = true;

		Date date;
		try {

			for (WorkScheduleTableData newRecord : itemsList) {
				Integer employeeId = newRecord.getEmployeeId() != null ? Integer.valueOf(newRecord.getEmployeeId()) : null;

				Time start = Helper.formatTime(newRecord.getStartTime());
				Time end = Helper.formatTime(newRecord.getEndTime());
				date = Helper.formatDateCatchExcpetion(newRecord.getDate());
				String comments = newRecord.getComments();

				if (newRecord.getId() == null) {
					result = workScheduleDAO.addTableRecord(start, end, date, employeeId, EnumShiftType.SHIFT_SCHAKEl.toString(), comments);
				}
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@Transactional
	public List<WorkSchedule> findall(Date start, Date end) {
		List<WorkSchedule> items = new ArrayList<WorkSchedule>();
		try {

			items.addAll(workScheduleDAO.getTableData(start, end));
			return items;

		} catch (Exception ex) {
			String e = ex.getMessage();
		}
		return items;
	}

	@Transactional
	public List<WorkScheduleTableData> getSlaapDienstData(Date start, Date end) {

		List<WorkScheduleTableData> slaadDiensts = new ArrayList<WorkScheduleTableData>();
		try {
			List<WorkSchedule> items = workScheduleDAO.getTableDataByDateAndShift(start, end, EnumShiftType.SHIFT_SLAAPDIENST.toString());
			slaadDiensts.addAll(makeWorkScheduleTableData(items));

			return slaadDiensts;

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return slaadDiensts;
	}

	@Transactional
	public List<WorkScheduleTableData> getSchakeldienst(Date start, Date end) {

		List<WorkScheduleTableData> slaadDiensts = new ArrayList<WorkScheduleTableData>();
		try {
			List<WorkSchedule> items = workScheduleDAO.getTableDataByDateAndShift(start, end, EnumShiftType.SHIFT_SCHAKEl.toString());
			slaadDiensts.addAll(makeWorkScheduleTableData(items));

			return slaadDiensts;

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return slaadDiensts;
	}

	@Transactional
	public WorkSchedule getDienstById(int id) {
		try {
			return workScheduleDAO.getTableDataById(id);

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return null;
	}

	@Transactional
	public boolean updateDienst(WorkScheduleTableData newRecord) {
		try {
			Integer employeeId = newRecord.getEmployeeId() != null ? Integer.valueOf(newRecord.getEmployeeId()) : null;

			Date date = Helper.formatDateCatchExcpetion(newRecord.getDate());

			Time start = Helper.formatTime(newRecord.getStartTime());
			Time end = Helper.formatTime(newRecord.getEndTime());
			String comments = newRecord.getComments() != null ? newRecord.getComments().length() != 0 ? newRecord.getComments() : null : null;

			int rowId = Integer.valueOf(newRecord.getId());

			if (rowId > 0) {
				return workScheduleDAO.updateTableRecord(rowId, date, employeeId, start, end, comments);
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}

		return false;
	}

	@Transactional
	public boolean deleteDienst(WorkScheduleTableData data) {
		try {

			Date date = Helper.formatDateCatchExcpetion(data.getDate());
			Integer employeeId = data.getEmployeeId() != null ? Integer.valueOf(data.getEmployeeId()) : null;
			int rowId = Integer.valueOf(data.getId());

			return workScheduleDAO.deleteTableRecord(rowId, date, employeeId);
		} catch (Exception ex) {
			String e = ex.getMessage();
		}

		return false;
	}

	private List<WorkScheduleTableData> makeWorkScheduleTableData(List<WorkSchedule> items) {
		List<WorkScheduleTableData> records = new ArrayList<WorkScheduleTableData>();
		for (WorkSchedule workSchedule : items) {
			String id = String.valueOf(workSchedule.getId());
			String date = Helper.formatDate(workSchedule.getWeekDate());

			String emlployeeId = null;
			String displayName = null;

			if (workSchedule.getEmployee() != null) {
				emlployeeId = String.valueOf(workSchedule.getEmployee().getId());
				displayName = workSchedule.getEmployee().getDisplayName();
			}

			String start = Helper.formatTime(workSchedule.getStartTime());
			String end = Helper.formatTime(workSchedule.getEndTime());
			String comments = workSchedule.getComments();

			WorkScheduleTableData insertData = new WorkScheduleTableData(id, emlployeeId, date, displayName, comments, start, end);
			records.add(insertData);
		}
		return records;

	}
}
