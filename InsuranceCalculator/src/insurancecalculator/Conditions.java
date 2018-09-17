/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insurancecalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Al3x3i
 */
public class Conditions {

    static class PercentByAge implements InsuranceCaseInterface {

        int age;

        PercentByAge(int age) {
            this.age = age;
        }

        @Override
        public double calculate() {
            Double r = Helper.calculateAgePercent(30);
            return r;
        }
    }

    static class PercentByAccident implements InsuranceCaseInterface {

        int value;

        PercentByAccident(int value) {
            this.value = value;
        }

        @Override
        public double calculate() {
            return (double) value / 100;
        }
    }

    static class PercentByBadHabbits implements InsuranceCaseInterface {

        int value;

        List<String> habbits = new ArrayList<String>();

        PercentByBadHabbits(String[] value) {
            habbits.addAll(Arrays.asList(value));
        }

        @Override
        public double calculate() {
            return (double) habbits.size() / 100;
        }
    }

    static class PercentByBrand implements InsuranceCaseInterface {

        String brand;

        PercentByBrand(String brand) {
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

    static class PercentByJewerlyPrice implements InsuranceCaseInterface {

        double amount;

        PercentByJewerlyPrice(double amount) {
            this.amount = amount;
        }

        @Override
        public double calculate() {

            if (this.amount > 100 && this.amount < 500) {
                return 0.01;
            } else if (this.amount > 500 && this.amount < 1000) {
                return 0.05;
            } else if (this.amount > 1000 && this.amount < 1500) {
                return 0.1;
            }

            return 0.2;
        }
    }
}
