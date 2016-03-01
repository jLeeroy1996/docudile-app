/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.docudile.app.services.utils;

import java.util.*;

/**
 *
 * @author franc
 */
public class MathCalculations {
    
    public static double getACos(double cosAngle) {
        return Math.acos(cosAngle);
    }

    public static double getDegrees(double acos) {
        return Math.toDegrees(acos);
    }

    public static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    public static int getMax(Map<Integer, Integer> array) {
        return Collections.max(array.values());
    }
    
    public static LinkedHashMap<String, Double> orderByValue(Map<String, Double> map) {
        List<Map.Entry<String, Double>> entries = new ArrayList<Map.Entry<String, Double>>(map.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
                return b.getValue().compareTo(a.getValue());
            }
        });

        LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
    
}
