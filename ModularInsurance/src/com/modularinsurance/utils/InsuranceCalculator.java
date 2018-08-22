package com.modularinsurance.utils;

import java.util.ArrayList;
import java.util.List;

public class InsuranceCalculator {

	List<InsuranceCaseInterface> items = new ArrayList<InsuranceCaseInterface>();

	double originalPrice = 0;

	public InsuranceCalculator(double amount) {
		this.originalPrice = amount;
	}

	public void add(InsuranceCaseInterface item) {
		items.add(item);
	}

	public double calculate() {
		double result = 0;

		for (InsuranceCaseInterface i : items) {
			result += i.calculate();
		}
		return result * originalPrice;
	}
}