package com.brouwershuis.controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brouwershuis.db.model.Employee;
import com.brouwershuis.db.model.EnumContract;
import com.brouwershuis.db.model.WorkSchedule;
import com.brouwershuis.helper.Helper;
import com.brouwershuis.helper.TimeSeserializer;
import com.brouwershuis.pojo.EmloyeePojo;
import com.brouwershuis.pojo.WorkScheduleTableData;
import com.brouwershuis.service.EmployeeService;
import com.brouwershuis.service.ShiftService;
import com.brouwershuis.service.WorkScheduleService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping(value = "/schedules")
public class WorkScheduleController {

	private static final Logger LOGGER = Logger.getLogger(WorkScheduleController.class);

	@Autowired
	EmployeeService employeeService;

	@Autowired
	WorkScheduleService workScheduleService;

	@Autowired
	ShiftService shiftService;

	/*
	 * @RequestMapping(value = "") public String index(ModelMap model) {
	 * 
	 * try {
	 * 
	 * // JsonObject reuqestEmployees = getEmployees(); //
	 * model.addAttribute("allEmployees", reuqestEmployees); // // JsonObject
	 * reuqestShifts = getShiftTypes(); // model.addAttribute("allShifts",
	 * reuqestShifts); // // Date[] dates = //
	 * Helper.getFirstAndLastDayOfWeek(Calendar.getInstance().getTime()); //
	 * JsonObject reuqestTableData = getTableData(dates[0], dates[1]); // //
	 * model.addAttribute("allTableData", reuqestTableData);
	 * 
	 * } catch (Exception ex) { String g = ex.getMessage(); } return "rooster"; }
	 */

	@RequestMapping(method = RequestMethod.DELETE, value = WebRestURIConstants.WORKSHCEDULE_SUB_DELETE_URL, consumes = "application/json")
	public @ResponseBody Map<String, Boolean> deleteRecord(@PathVariable("viewName") String viewName, ModelMap model,
			@RequestBody String jsonData) {

		WorkScheduleTableData data = new Gson().fromJson(jsonData, WorkScheduleTableData.class);

		boolean result = workScheduleService.deleteDienst(data);
		return Collections.singletonMap("status", result);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = WebRestURIConstants.WORKSHCEDULE_SUB_PATCH_URL, consumes = "application/json")
	public @ResponseBody Map<String, Boolean> updateTableRecord(@PathVariable("viewName") String viewName,
			ModelMap model, @RequestBody String jsonData) {

		WorkScheduleTableData updateTableData = new Gson().fromJson(jsonData, WorkScheduleTableData.class);

		System.out.println("Save update data");
		boolean result = workScheduleService.updateDienst(updateTableData);

		return Collections.singletonMap("status", result);
	}

	@RequestMapping(method = RequestMethod.PUT, value = WebRestURIConstants.WORKSHCEDULE_SUB_PUT_URL, consumes = "application/json")
	public @ResponseBody Map<String, Boolean> saveTableRecord(@PathVariable("viewName") String viewName, ModelMap model,
			@RequestBody String jsonData) {

		boolean result = false;

		try {
			System.out.println("Save data");
			List<WorkScheduleTableData> updateTableData = new Gson().fromJson(jsonData,
					new TypeToken<List<WorkScheduleTableData>>() {
					}.getType());

			if (viewName.equals(WebRestURIConstants.WORKSHCEDULE_SUB_SLAAPDIENST)) {
				result = workScheduleService.addSlaapDienst(updateTableData);
			} else if (viewName.equals(WebRestURIConstants.WORKSHCEDULE_SUB_SCHAKELDIENST)) {
				result = workScheduleService.addSchakelDienst(updateTableData);
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return Collections.singletonMap("status", result);
	}

	@RequestMapping(method = RequestMethod.GET, value = WebRestURIConstants.WORKSHCEDULE_SUB_GET_URL)
	public @ResponseBody String getTableData(@PathVariable("viewName") String viewName, ModelMap model, String start,
			String end) {
		try {
			Date startDate = Helper.formatDate(start);
			Date endDate = Helper.formatDate(end);

			if (startDate != null && endDate != null) {

				Gson gson = new Gson();
				JsonObject jsonObject = new JsonObject();

				if (viewName.equals(WebRestURIConstants.WORKSHCEDULE_SUB_ROOSTER)) {
					List<WorkScheduleTableData> slaadDiensts = workScheduleService.getSlaapDienstData(startDate,
							endDate);
					List<WorkScheduleTableData> schakeldienst = workScheduleService.getSchakeldienst(startDate,
							endDate);

					JsonElement j_slaapDienst = gson.toJsonTree(slaadDiensts);
					JsonElement j_schakeldienst = gson.toJsonTree(schakeldienst);
					jsonObject.add("slaapDienst", j_slaapDienst);
					jsonObject.add("schakeldienst", j_schakeldienst);
					return gson.toJson(jsonObject);

				} else if (viewName.equals(WebRestURIConstants.WORKSHCEDULE_SUB_SLAAPDIENST)) {

					List<WorkScheduleTableData> slaadDiensts = workScheduleService.getSlaapDienstData(startDate,
							endDate);

					JsonElement j_slaapDienst = gson.toJsonTree(slaadDiensts);
					jsonObject.add("slaapDienst", j_slaapDienst);
					return gson.toJson(jsonObject);
				} else if (viewName.equals(WebRestURIConstants.WORKSHCEDULE_SUB_SCHAKELDIENST)) {

					List<WorkScheduleTableData> schakeldienst = workScheduleService.getSchakeldienst(startDate,
							endDate);

					JsonElement j_schakeldienst = gson.toJsonTree(schakeldienst);
					jsonObject.add("schakeldienst", j_schakeldienst);
					return gson.toJson(jsonObject);
				}

			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return "";
	}

	@RequestMapping(method = RequestMethod.GET, value = WebRestURIConstants.WORKSHCEDULE_SUB_GETDIENST_URL)
	public @ResponseBody WorkSchedule getRecordDetails(@PathVariable("viewName") String viewName, ModelMap model,
			String id) {
		try {
			int rowId = Integer.valueOf(id);
			WorkSchedule result = workScheduleService.getDienstById(rowId);
			return result;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return null;
	}

	/*
	 * DIENST VIEW
	 */
	@RequestMapping(method = RequestMethod.GET, value = WebRestURIConstants.WORKSHCEDULE_SUB_URL)
	public String getSubView(@PathVariable("viewName") String viewName, ModelMap model) {

		String selectedView = "";

		switch (viewName) {
		case WebRestURIConstants.WORKSHCEDULE_SUB_ROOSTER:
			selectedView = "subView\\rooster";
			break;

		case WebRestURIConstants.WORKSHCEDULE_SUB_SLAAPDIENST:
			selectedView = "subView\\slaapdienst";
			break;

		case WebRestURIConstants.WORKSHCEDULE_SUB_SCHAKELDIENST:
			selectedView = "subView\\schakeldienst";
			break;
		case WebRestURIConstants.WORKSCHEDULE_SUB_VVVDIENS:
			selectedView = "subView\\vvvdienst";
		}

		if (!selectedView.equals("")) {

			JsonObject reuqestEmployees = getEmployees();
			model.addAttribute("allEmployees", reuqestEmployees);
			return selectedView;
		}

		return "404";
	}

	private JsonObject getEmployees() {
		List<Employee> employees = employeeService.findAll();
		List<EmloyeePojo> medewerkers = new ArrayList<EmloyeePojo>();
		List<EmloyeePojo> vrijwilligers = new ArrayList<EmloyeePojo>();
		List<EmloyeePojo> clients = new ArrayList<EmloyeePojo>();

		sortEmployees(employees, medewerkers, vrijwilligers, clients);

		Gson gson = new Gson();
		JsonObject reuqestEmployees = new JsonObject();

		JsonElement j_medewerkers = gson.toJsonTree(medewerkers);
		JsonElement j_vrijwilligers = gson.toJsonTree(vrijwilligers);
		JsonElement j_clients = gson.toJsonTree(clients);
		reuqestEmployees.add("medewerkers", j_medewerkers);
		reuqestEmployees.add("vrijwilligers", j_vrijwilligers);
		reuqestEmployees.add("clients", j_clients);
		return reuqestEmployees;
	}

	private void sortEmployees(List<Employee> allEmployees, List<EmloyeePojo> medewerkers,
			List<EmloyeePojo> vrijwilligers, List<EmloyeePojo> clients) {
		try {
			for (Employee emp : allEmployees) {
				if (emp.getContract().getName().equals(EnumContract.CONTRACT_CLIENT.toString())) {
					clients.add(new EmloyeePojo(emp.getId(), emp.getFirstName(), emp.getLastName(), emp.getEnabled(),
							emp.getContract().getName()));
				} else if (emp.getContract().getName().equals(EnumContract.CONTRACT_MEDEWERKER.toString())) {
					medewerkers.add(new EmloyeePojo(emp.getId(), emp.getFirstName(), emp.getLastName(),
							emp.getEnabled(), emp.getContract().getName()));
				} else if (emp.getContract().getName().equals(EnumContract.CONTRACT_VRIJWILLIGER.toString())) {
					vrijwilligers.add(new EmloyeePojo(emp.getId(), emp.getFirstName(), emp.getLastName(),
							emp.getEnabled(), emp.getContract().getName()));
				}
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
	}

	private Gson getGson() {

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyyy-MM-dd");

		gsonBuilder.registerTypeAdapter(Time.class, new TimeSeserializer());

		// prevent bi derectional reference, see annotation @Expose
		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		return gsonBuilder.create();
	}
}
