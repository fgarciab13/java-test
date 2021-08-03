package com.hrs.utils;



import lombok.Data;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Data
public class Utilities {
    public static Map<String, Operator> operatorMap = new HashMap<>();
    public static Set<String> availableExtensions = new HashSet<>();


    static {
        operatorMap.put(">", new Operator() {
            @Override public boolean compare(int a, int b) {
                return a > b;
            }
        });
        operatorMap.put("<", new Operator() {
            @Override public boolean compare(int a, int b) {
                return a < b;
            }
        });
        operatorMap.put("==", new Operator() {
            @Override public boolean compare(int a, int b) {
                return a == b;
            }
        });

        operatorMap.put(">=", new Operator() {
            @Override public boolean compare(int a, int b) {
                return a >= b;
            }
        });

        operatorMap.put("<=", new Operator() {
            @Override public boolean compare(int a, int b) {
                return a <= b;
            }
        });

        operatorMap.put("!=", new Operator() {
            @Override public boolean compare(int a, int b) {
                return a != b;
            }
        });

        availableExtensions.add("json");
        availableExtensions.add("yaml");
        availableExtensions.add("yml");
    }

}
