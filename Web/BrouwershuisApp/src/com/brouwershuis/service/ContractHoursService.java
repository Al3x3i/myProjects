package com.brouwershuis.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.brouwershuis.db.dao.ContractHoursDAO;
import com.brouwershuis.db.model.ContractHours;
import com.brouwershuis.helper.Helper;
import com.brouwershuis.pojo.WorkingHoursRecordPojo.ContractHoursData;

@Service
public class ContractHoursService {

	@Inject
	private ContractHoursDAO contractHoursDAO;

	@Transactional
	public boolean updateContractHours(ContractHoursData[] data, String empId) {

		boolean result = true;

		try {

			for (int i = 0; i < data.length; i++) {
				Date startDate = Helper.formatDateCatchExcpetion(data[i].getStartDate());
				Date endDate = Helper.formatDateCatchExcpetion(data[i].getEndDate());

				int fixedTime = Helper.getSecondsFromTime(data[i].getFixedTime());

				int employeeId = Integer.valueOf(empId);

				if (data[i].getId() == null) {
					if (!contractHoursDAO.addContracHours(employeeId, startDate, endDate, fixedTime)) {
						result = false;
					}
				} else {

					int id = Integer.valueOf(data[i].getId());

					boolean isFixedTimeEmpty = fixedTime == 0;

					if (!isFixedTimeEmpty) {
						if (!contractHoursDAO.updateContracHours(id, employeeId, startDate, endDate, fixedTime)) {
							result = false;
						}

					} else if (isFixedTimeEmpty) {
						if (!contractHoursDAO.deleteSlaapDienstData(id, employeeId, startDate, endDate)) {
							result = false;
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

	@Transactional
	public List<ContractHours> getContractHoursfindBetweenDates(String empId, String beginDate, String endDate) {

		try {

			Date begin = Helper.formatDate(beginDate);
			Date end = Helper.formatDate(endDate);
			int employeeId = Integer.valueOf(empId);

			List<ContractHours> contractHours = contractHoursDAO.findBetweenDates(employeeId, begin, end);
			return contractHours;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
