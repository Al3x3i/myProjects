package com.modularinsurance.spring;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.modularinsurance.pojo.FormPojo;
import com.modularinsurance.service.InsuranceCalculatorService;
import com.modularinsurance.utils.Conditions;

@Controller
public class HomeController {

	@Autowired
	private InsuranceCalculatorService calculatorService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		System.out.println("Home Page Requested");
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/subviews", method = RequestMethod.GET)
	public String subviews(String name) {
		System.out.println("Subview Page Requested: " + name);

		String returnUrl = "";
		switch (name) {
		case "Bike":
			returnUrl = WebRestURIConstants.BIKE_SUB_URL;
			break;
		case "Jewerly":
			returnUrl = WebRestURIConstants.JEWERLY_SUB_URL;
			break;
		case "Electronics":
			returnUrl = WebRestURIConstants.ELECTRONICS_SUB_URL;
			break;
		case "SportsEquipment":
			returnUrl = WebRestURIConstants.SPORTSEQUIPMENT_SUB_URL;
			break;
		}
		return returnUrl;
	}

	@RequestMapping(value = "subview/{viewName}", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody String submitBikeForm(@PathVariable("viewName") String viewName, @RequestBody String jsonData) {
		System.out.println("SubmitBikeForm Page Requested, " + viewName + ":" + jsonData);

		int result = getInsurancePrince(jsonData, viewName);
		if (result < 0) {
			return "";
		}

		return String.valueOf(result);
	}

	private int getInsurancePrince(String jsonData, String type) {
		FormPojo formData = parseFormObject(jsonData);
		
		//TODO validation

		double result = -1;

		if (formData == null)
			return (int)result;

		if (type.equals("Bike")) {
			result = calculatorService.calculateBikeInsurance(formData);
		} else if (type.equals("Jewerly")) {
			result = calculatorService.calculateJewerlyInsurance(formData);
		} else if (type.equals("Electronics")) {
			result = calculatorService.calculateElectronicsInsurance(formData);
		} else if (type.equals("SportsEquipment")) {
			result = calculatorService.calculateSportsEquipmentInsurance(formData);
		}

		return (int)result;
	}

	private FormPojo parseFormObject(String jsonData) {
		FormPojo p;
		try {
			p = new Gson().fromJson(jsonData, FormPojo.class);
		} catch (Exception e) {
			return null;
		}
		return p;
	}

}