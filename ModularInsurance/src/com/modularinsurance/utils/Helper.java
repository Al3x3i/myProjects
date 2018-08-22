package com.modularinsurance.utils;

import java.util.Map;
import java.util.TreeMap;

public abstract class Helper {
    private static Map<String, Integer> ageMap = createTreeMap();

    private static Map<String, Integer> createTreeMap(){
        Map<String, Integer> temp = new TreeMap<>();
        temp.put("18-25", 10);
        temp.put("25-30", 10);
        temp.put("30-35", 10);
        temp.put("35-40", 10);
        return temp;
    }

    public static double calculateAgePercent(int checkAge) {

        double result = 1.0;
        for (Map.Entry<String, Integer> entry : ageMap.entrySet()) {

            String key = entry.getKey();
            String[] ageSplit = key.split("-");
            int startAge = Integer.valueOf(ageSplit[0]);
            int endAge = Integer.valueOf(ageSplit[1]);

            //check age range
            if (checkAge >= startAge && checkAge <= endAge || checkAge >= startAge && checkAge >= endAge) {
                double value = (double)entry.getValue() / 100;
                result += value * result;
            } else {
                break;
            }
        }
        
        result -= 1;
        double rResult = (int)(result * 1000);
        rResult = rResult / 1000;
        return rResult;
    }
}