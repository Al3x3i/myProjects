package com.brouwershuis.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.brouwershuis.db.dao.WorkingHoursRecordDAO;
import com.brouwershuis.db.model.Employee;
import com.brouwershuis.db.model.WorkingHoursRecord;
import com.brouwershuis.helper.Helper;
import com.brouwershuis.pojo.WorkingHoursRecordPojo.HoursCellData;

@Service
public class WorkingHoursRecordService {

	@Inject
	private WorkingHoursRecordDAO workingHoursRecordDAO;

	@Transactional
	public List<WorkingHoursRecord> getWorkingHoursRecordByYearEmployee(String empId, String beginDate,
			String endDate) {

		try {

			Date begin = Helper.formatDate(beginDate);
			Date end = Helper.formatDate(endDate);
			int employeeId = Integer.valueOf(empId);

			List<WorkingHoursRecord> contractHours = workingHoursRecordDAO.findBetweenDates(employeeId, begin, end);
			return contractHours;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	@Transactional
	public boolean updateWorkingHours(HoursCellData[] data, String empId) {
		boolean result = true;

		try {
			int employeeId = Integer.valueOf(empId);

			// List<WorkingHoursRecord> groupedRecords = sortWorkingHours(data,
			// employeeId);
			List<HoursCellData> records = Arrays.asList(data);
			for (HoursCellData newRecord : records) {

				boolean isEmptyWorkHours = true;
				boolean isEmptyVacationHours = true;

				Date recordDate = Helper.formatDateCatchExcpetion(newRecord.getDate());
				Date recordTime = Helper.formatTime(newRecord.getHours());
				int hoursType = Integer.valueOf(newRecord.getHoursType());

				int recordId = 0;
				if (newRecord.getId() != null) {
					recordId = Integer.valueOf(newRecord.getId());
				}

				if (recordId != 0) {

					WorkingHoursRecord existedRecord = workingHoursRecordDAO.findWorkingHoursRecord(recordId,
							employeeId, recordDate);

					if (existedRecord != null) {

						if (WorkingHoursRecord.VACATION_TYPE == hoursType)
							existedRecord.setVacationHours(recordTime);
						else {
							existedRecord.setWorkedHours(recordTime);
						}

						if (existedRecord.getVacationHours() != null)
							isEmptyVacationHours = Helper.isEmptyTime(existedRecord.getVacationHours());
						if (existedRecord.getWorkedHours() != null)
							isEmptyWorkHours = Helper.isEmptyTime(existedRecord.getWorkedHours());

						if (isEmptyWorkHours && isEmptyVacationHours) {
							workingHoursRecordDAO.deleteWorkingHoursRecord(existedRecord);
						} else {
							workingHoursRecordDAO.updateWorkingHoursRecord(existedRecord);
						}
					}
				} else {

					WorkingHoursRecord existedRecord = workingHoursRecordDAO.findByDateAndEmployee(employeeId,
							recordDate);

					if (existedRecord == null) {
						WorkingHoursRecord w = null;
						if (WorkingHoursRecord.VACATION_TYPE == hoursType) {
							w = new WorkingHoursRecord(recordId, recordDate, recordTime, null,
									new Employee(employeeId));
						} else {
							w = new WorkingHoursRecord(recordId, recordDate, null, recordTime,
									new Employee(employeeId));
						}
						workingHoursRecordDAO.addWorkingHoursRecord(w);
					} else {
						if (WorkingHoursRecord.VACATION_TYPE == hoursType)
							existedRecord.setVacationHours(recordTime);
						else {
							existedRecord.setWorkedHours(recordTime);
						}

						if (existedRecord.getVacationHours() != null)
							isEmptyVacationHours = Helper.isEmptyTime(existedRecord.getVacationHours());
						if (existedRecord.getWorkedHours() != null)
							isEmptyWorkHours = Helper.isEmptyTime(existedRecord.getWorkedHours());

						if (isEmptyWorkHours && isEmptyVacationHours) {
							workingHoursRecordDAO.deleteWorkingHoursRecord(existedRecord);
						} else {
							workingHoursRecordDAO.updateWorkingHoursRecord(existedRecord);
						}
					}

				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// Group working and vacation hours into one entity
	private List<WorkingHoursRecord> sortWorkingHours(HoursCellData[] dataList, int employeeId) {

		List<WorkingHoursRecord> hoursRecord = new ArrayList<WorkingHoursRecord>();

		for (HoursCellData item : dataList) {

			try {
				Date recordDate = Helper.formatDateCatchExcpetion(item.getDate());
				Date recordTime = Helper.formatTime(item.getHours());
				int hoursType = Integer.valueOf(item.getHoursType());

				int recordId = 0;
				if (item.getId() != null) {
					recordId = Integer.valueOf(item.getId());
				}

				boolean existed = false;

				for (WorkingHoursRecord addedItem : hoursRecord) {
					if (addedItem.getWeekDate() == recordDate) {
						if (WorkingHoursRecord.VACATION_TYPE == hoursType) {
							addedItem.setVacationHours(recordTime);
							existed = true;
							break;
						} else {
							addedItem.setWorkedHours(recordTime);
							existed = true;
							break;
						}
					}
				}

				if (!existed) {
					WorkingHoursRecord w = null;
					if (WorkingHoursRecord.VACATION_TYPE == hoursType) {
						w = new WorkingHoursRecord(recordId, recordDate, recordTime, null, new Employee(employeeId));
					} else {
						w = new WorkingHoursRecord(recordId, recordDate, null, recordTime, new Employee(employeeId));
					}
					hoursRecord.add(w);
				}
			} catch (Exception ex) {

			}
		}
		return hoursRecord;
	}
}
