package com.modularinsurance.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Al3x3i
 */
public class Conditions {

	public static class PercentByAge implements InsuranceCaseInterface {

		int age;

		public PercentByAge(int age) {
			this.age = age;
		}

		@Override
		public double calculate() {
			Double r = Helper.calculateAgePercent(age);
			return r;
		}
	}

	public static class PercentByAccident implements InsuranceCaseInterface {

		int value;

		public PercentByAccident(int value) {
			this.value = value;
		}

		@Override
		public double calculate() {
			return (double) value / 100;
		}
	}

	public static class PercentByBadHabbits implements InsuranceCaseInterface {

		int value;

		List<String> habbits = new ArrayList<String>();

		public PercentByBadHabbits(String[] value) {
			habbits.addAll(Arrays.asList(value));
		}

		@Override
		public double calculate() {
			return (double) habbits.size() / 100;
		}
	}

	public static class PercentByBrand implements InsuranceCaseInterface {

		String brand;

		public PercentByBrand(String brand) {
			this.brand = brand;
		}

		@Override
		public double calculate() {
			if (brand.equals("BMW")) {
				return 0.2;
			} else {
				return 0.1;
			}
		}
	}

	public static class PercentByPrice implements InsuranceCaseInterface {

		double amount;
		double factor;

		public PercentByPrice(double amount, double factor) {
			this.amount = amount;
			this.factor = factor;
		}

		@Override
		public double calculate() {
			double t;
			if (this.amount < 500) {
				t= 0.01;
			} else if (this.amount >= 500 && this.amount < 1000) {
				t= 0.05;
			} else if (this.amount >= 1000 && this.amount <= 2000) {
				t= 0.06;
			}else{
				t = 0.07;
			}
			
			return t * factor;
		}
	}
}
