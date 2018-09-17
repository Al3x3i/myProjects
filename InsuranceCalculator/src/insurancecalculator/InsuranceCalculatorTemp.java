/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insurancecalculator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Al3x3i
 */
public class InsuranceCalculatorTemp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        double result = 0;
        String insuranceType = "Bike";

        if (insuranceType.equals("Bike")) {
            InsuranceCalculator calc = new InsuranceCalculator(1000);
            calc.add(new Conditions.PercentByAge(30));
            calc.add(new Conditions.PercentByAccident(1));
            calc.add(new Conditions.PercentByBadHabbits(new String[]{"Casino"}));
            calc.add(new Conditions.PercentByBrand("BMW"));

            result = calc.calculate();
        } else if(insuranceType.equals("Jewerly")){
            InsuranceCalculator calc = new InsuranceCalculator(1000);
            calc.add(new Conditions.PercentByAge(30));
            calc.add(new Conditions.PercentByAccident(1));
            calc.add(new Conditions.PercentByBadHabbits(new String[]{"Casino"}));
            calc.add(new Conditions.PercentByBrand("BMW"));
            result = calc.calculate();
        }

        System.out.println(result);
    }
}
