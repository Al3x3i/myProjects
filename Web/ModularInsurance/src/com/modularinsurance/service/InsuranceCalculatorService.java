package com.modularinsurance.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.modularinsurance.pojo.FormPojo;
import com.modularinsurance.utils.Conditions;
import com.modularinsurance.utils.InsuranceCalculator;
import com.modularinsurance.utils.InsuranceCaseInterface;

/**
 *
 * @author Al3x3i
 */
@Service
public class InsuranceCalculatorService {

	public double calculateBikeInsurance(FormPojo formData) {

		String type = formData.getType();
		int driverAge = Integer.valueOf(formData.getYourAge());
		int price = Integer.valueOf(formData.getPrice());

		InsuranceCalculator calc = new InsuranceCalculator(price);
		calc.add(new Conditions.PercentByAge(driverAge));
		calc.add(new Conditions.PercentByBrand(type));
		return calc.calculate();
	}

	public double calculateJewerlyInsurance(FormPojo formData) {
		int price = Integer.valueOf(formData.getPrice());

		InsuranceCalculator calc = new InsuranceCalculator(price);
		calc.add(new Conditions.PercentByPrice(price, 5));
		return calc.calculate();
	}

	public double calculateElectronicsInsurance(FormPojo formData) {
		int price = Integer.valueOf(formData.getPrice());

		InsuranceCalculator calc = new InsuranceCalculator(price);
		calc.add(new Conditions.PercentByPrice(price, 2));
		return calc.calculate();
	}

	public double calculateSportsEquipmentInsurance(FormPojo formData) {
		int price = Integer.valueOf(formData.getPrice());

		InsuranceCalculator calc = new InsuranceCalculator(price);
		calc.add(new Conditions.PercentByPrice(price, 3));
		return calc.calculate();
	}
}
