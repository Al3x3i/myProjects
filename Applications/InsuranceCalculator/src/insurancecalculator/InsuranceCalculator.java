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
class InsuranceCalculator {

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
        return   (result * originalPrice) - originalPrice  ;
    }
}
