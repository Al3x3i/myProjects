package com.brouwershuis.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brouwershuis.db.model.ContractHours;
import com.brouwershuis.db.model.Employee;
import com.brouwershuis.db.model.WorkingHoursRecord;
import com.brouwershuis.helper.Helper;
import com.brouwershuis.pojo.EmloyeePojo;
import com.brouwershuis.pojo.WorkingHoursRecordPojo;
import com.brouwershuis.pojo.WorkingHoursRecordPojo.ContractHoursData;
import com.brouwershuis.pojo.WorkingHoursRecordPojo.HoursCellData;
import com.brouwershuis.security.CustomUserDetails;
import com.brouwershuis.service.ContractHoursService;
import com.brouwershuis.service.EmployeeService;
import com.brouwershuis.service.WorkingHoursRecordService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value = "/working_hours")
public class WorkingHoursRecordController {
	
	private static final Logger LOGGER = Logger.getLogger(WorkingHoursRecordController.class);

	@Autowired
	EmployeeService employeeService;

	@Autowired
	private WorkingHoursRecordService workingHoursRecordService;

	@Autowired
	private ContractHoursService contractHoursService;

	@RequestMapping(value = "")
	public String index(@AuthenticationPrincipal CustomUserDetails activeUser, ModelMap model) {
		try {

			List<Employee> employees = employeeService.findAll();

			List<EmloyeePojo> pojoData = new ArrayList();

			if (activeUser.isAdming() == false) {
				Employee emp = null;
				for (Employee temp : employees) {
					if (temp.getUser() != null && temp.getUser().getId() == activeUser.getUserId()) {
						emp = temp;
						break;
					}
				}
				if (emp != null) {
					EmloyeePojo e = new EmloyeePojo();
					e.setDT_RowId(Integer.toString(emp.getId()));
					e.setFirstName(emp.getFirstName());
					e.setLastName(emp.getLastName());
					pojoData.add(e);
				}

			} else {
				for (int i = 0; i < employees.size(); i++) {
					EmloyeePojo e = new EmloyeePojo();
					e.setDT_RowId(Integer.toString(employees.get(i).getId()));
					e.setFirstName(employees.get(i).getFirstName());
					e.setLastName(employees.get(i).getLastName());
					pojoData.add(e);
				}
			}

			JsonObject request = new JsonObject();
			Gson gson = new Gson();

			JsonElement e = gson.toJsonTree(pojoData);
			request.add("Employees", e);

			model.addAttribute("allEmployees", request);

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}

		return "working_hours";
	}

	@RequestMapping(value = "saveTableData", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody boolean saveTableData(@RequestBody String jsonData) {
		boolean result = false;
		WorkingHoursRecordPojo pojoObject;
		try {
			pojoObject = new Gson().fromJson(jsonData, WorkingHoursRecordPojo.class);
			if (pojoObject.getEmployeeId() != null && !pojoObject.getEmployeeId().isEmpty()) {


				if (pojoObject.getContractHoursData() != null && pojoObject.getContractHoursData().length != 0) {
					boolean result_1 = contractHoursService.updateContractHours(pojoObject.getContractHoursData(),
							pojoObject.getEmployeeId());
				}

				if (pojoObject.getHoursCellData() != null && pojoObject.getHoursCellData().length != 0) {
					boolean result_2 = workingHoursRecordService.updateWorkingHours(pojoObject.getHoursCellData(),
							pojoObject.getEmployeeId());
				}
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "getTableData", method = RequestMethod.GET)
	public @ResponseBody String getTableData(String empId, String start, String end) {

		try {

			WorkingHoursRecordPojo returnObject = new WorkingHoursRecordPojo();
			returnObject.setEmployeeId(String.valueOf(empId));

			// Creating contract hours object to transfer
			List<ContractHours> contractHours = contractHoursService.getContractHoursfindBetweenDates(empId, start,
					end);

			List<ContractHoursData> contractHoursCells = new ArrayList<ContractHoursData>();
			for (ContractHours item : contractHours) {
				WorkingHoursRecordPojo.ContractHoursData v = returnObject.new ContractHoursData();
				v.setId(String.valueOf(item.getId()));
				v.setStartDate(Helper.formatDate(item.getStartDate()));
				v.setEndDate(Helper.formatDate(item.getEndDate()));
				v.setFixedTime(Helper.getTimeFromSeconds(item.getFixedTime()));
				contractHoursCells.add(v);
			}

			// Creating working/vacation hours objects to transfer
			List<WorkingHoursRecord> workingHoursRecords = workingHoursRecordService
					.getWorkingHoursRecordByYearEmployee(empId, start, end);

			List<HoursCellData> workingCells = new ArrayList<HoursCellData>();
			List<HoursCellData> vacationCells = new ArrayList<HoursCellData>();

			for (WorkingHoursRecord item : workingHoursRecords) {

				if (item.getWorkedHours() != null) {
					WorkingHoursRecordPojo.HoursCellData v = returnObject.new HoursCellData();
					v.setId(String.valueOf(item.getId()));
					v.setHoursType(String.valueOf(WorkingHoursRecord.WORK_TYPE));
					v.setDate(Helper.formatDate(item.getWeekDate()));
					v.setHours(Helper.formatTime(item.getWorkedHours()));
					workingCells.add(v);
				}

				if (item.getVacationHours() != null) {
					WorkingHoursRecordPojo.HoursCellData v = returnObject.new HoursCellData();
					v.setId(String.valueOf(item.getId()));
					v.setHoursType(String.valueOf(WorkingHoursRecord.VACATION_TYPE));
					v.setDate(Helper.formatDate(item.getWeekDate()));
					v.setHours(Helper.formatTime(item.getVacationHours()));
					vacationCells.add(v);
				}
			}

			Gson gsonPojo = new Gson();

			JsonObject obj = new JsonObject();

			JsonElement jsonElementContractHours = gsonPojo.toJsonTree(contractHoursCells);

			JsonElement jsonElementWorkingCells = gsonPojo.toJsonTree(workingCells);
			JsonElement jsonElementVacationCells = gsonPojo.toJsonTree(vacationCells);

			obj.addProperty("employee", empId);

			obj.add("contractHours", jsonElementContractHours);

			obj.add("workingCells", jsonElementWorkingCells);
			obj.add("vacationCells", jsonElementVacationCells);

			String result = gsonPojo.toJson(obj);

			return result;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}

		return null;
	}
}
