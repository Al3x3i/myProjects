package com.brouwershuis.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brouwershuis.db.model.Contract;
import com.brouwershuis.db.model.Employee;
import com.brouwershuis.helper.Helper;
import com.brouwershuis.pojo.EmloyeePojo;
import com.brouwershuis.pojo.EmployeeDTO;
import com.brouwershuis.service.ContractService;
import com.brouwershuis.service.EmployeeService;
import com.brouwershuis.validator.UserValidator;
import com.brouwershuis.validator.UserValidator.ValidationType;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value = "/employees")
public class EmployeeController {

	private static final Logger LOGGER = Logger.getLogger(EmployeeController.class);

	@Autowired
	EmployeeService employeeService;

	@Autowired
	ContractService contractService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "")
	public String index(ModelMap model) {

		List<Contract> contracts = contractService.findAll();

		JsonObject jsonContract = new JsonObject();

		JsonElement element = new Gson().toJsonTree(contracts);
		model.addAttribute("allContracts", element);

		return "employees";
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody String getAllEmployees(ModelMap modelMap) {

		List<Employee> emp = employeeService.findAll();
		List<EmloyeePojo> pojoData = new ArrayList();

		for (int i = 0; i < emp.size(); i++) {

			EmloyeePojo e = new EmloyeePojo();
			e.setDT_RowId(Integer.toString(emp.get(i).getId()));
			e.setFirstName(emp.get(i).getFirstName());
			e.setLastName(emp.get(i).getLastName());
			e.setGender(emp.get(i).getGender());
			e.setAddress(emp.get(i).getAddress());
			e.setDateOfBirth(Helper.formatDate(emp.get(i).getDateOfBirth()));
			e.setEmployeeActive(emp.get(i).getEnabled());
			pojoData.add(e);
		}

		EmployeeDTO employees = new EmployeeDTO();
		employees.setiTotalRecords(pojoData.size());
		employees.setsEcho(1);
		employees.setItotalDisplayRecords(pojoData.size());
		employees.setAaData(pojoData);

		Gson gson = new Gson();
		JsonElement element = gson.toJsonTree(pojoData);
		JsonArray a = new JsonArray();
		a.add(element);
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("data", element);
		return gson.toJson(jsonObject);
	}

	@RequestMapping(value = "/getEmployee", method = RequestMethod.GET)
	public @ResponseBody Employee getEmployee(String id) {
		try {
			Employee e = employeeService.findEmployee(id);
			return e;
		} catch (Exception ex) {
			
		}
		return null;
	}

	@RequestMapping(value = "/deleteEmployee", method = RequestMethod.POST)
	public @ResponseBody Map<String, Boolean> deleteEmployee(String id) {
		try {
			if (employeeService.deleteEmployee("AA")) {
				return Collections.singletonMap("status", true);
			}
		} catch (Exception ex) {
			LOGGER.error("HELLO WORLD");
		}
		return Collections.singletonMap("status", false);
	}

	@RequestMapping(value = "/editEmployee", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> editEmployee(EmloyeePojo emp, BindingResult bindingResult) {
		try {

			userValidator.setValidationType(ValidationType.EDITUSER);
			userValidator.validate(emp, bindingResult);
			userValidator.setValidationType(ValidationType.ADDUSER);

			if (bindingResult.hasErrors()) {

				Map<String, Object> returnMessage = new HashMap<String, Object>();

				Map<String, String> validation = new HashMap<String, String>();
				List<ObjectError> errors = bindingResult.getAllErrors();

				for (ObjectError objectError : errors) {

					String msg = null;
					if (objectError instanceof FieldError) {
						FieldError fieldError = (FieldError) objectError;
						msg = messageSource.getMessage(fieldError, Locale.ENGLISH);
						validation.put(fieldError.getField(), msg);
					} else {
						msg = messageSource.getMessage(objectError.getDefaultMessage(), null, Locale.ENGLISH);
						validation.put(objectError.getCode(), msg);
					}
				}

				returnMessage.put("inputerror", validation);
				returnMessage.put("status", false);

				return returnMessage;
			}

			if (employeeService.editEmployee(emp))
				return Collections.singletonMap("status", true);

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			System.out.println("Error");
		}
		return Collections.singletonMap("status", false);
	}

	// For request body I need to serialize the object and use the contentType,
	// like :
	// 'application/json; charset=utf-8',
	// @RequestBody EmloyeePojo em

	@RequestMapping(value = "addEmployee", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addEmployee(EmloyeePojo emp, BindingResult bindingResult) {
		boolean result = false;
		try {
			userValidator.validate(emp, bindingResult);

			if (bindingResult.hasErrors()) {

				Map<String, Object> returnMessage = new HashMap<String, Object>();

				Map<String, String> validation = new HashMap<String, String>();
				List<ObjectError> errors = bindingResult.getAllErrors();

				for (ObjectError objectError : errors) {

					String msg = null;
					if (objectError instanceof FieldError) {
						FieldError fieldError = (FieldError) objectError;
						msg = messageSource.getMessage(fieldError, Locale.ENGLISH);
						validation.put(fieldError.getField(), msg);
					} else {
						msg = messageSource.getMessage(objectError.getDefaultMessage(), null, Locale.ENGLISH);
						validation.put(objectError.getCode(), msg);
					}
				}

				returnMessage.put("inputerror", validation);
				returnMessage.put("status", result);

				return returnMessage;
			}

			result = employeeService.addEmployee(emp);

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			System.out.println("");
		}

		return Collections.singletonMap("status", result);
	}
}
