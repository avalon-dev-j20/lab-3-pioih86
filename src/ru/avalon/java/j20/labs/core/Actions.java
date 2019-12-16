package ru.avalon.java.j20.labs.core;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public enum Actions {
    sum("+", Actions::sum), substruct("-", Actions::subStruct), division("/", Actions::division), multiply("*",
            Actions::multiply), getResult("=", Actions::getResult);
    public static final int SCALE = 12;
    private static final MathContext MATH_CONT = new MathContext(SCALE, RoundingMode.HALF_UP);
    private String actName;
    private Action act;

    Actions(String actName, Action action) {
        this.actName = actName;
        act = action;
    }

    private static String sum(String currentVal, String memoryVal) {
        BigDecimal first = new BigDecimal(currentVal);
        BigDecimal second = new BigDecimal(memoryVal);
        return "" + second.add(first, MATH_CONT).doubleValue();
    }

    private static String subStruct(String currentVal, String memoryVal) {
        BigDecimal first = new BigDecimal(currentVal);
        BigDecimal second = new BigDecimal(memoryVal);
        return "" + second.subtract(first, MATH_CONT).doubleValue();
    }

    private static String division(String currentVal, String memoryVal) {
        BigDecimal first = new BigDecimal(currentVal);
        BigDecimal second = new BigDecimal(memoryVal);
        if (first.compareTo(new BigDecimal("0")) != 0) {
            return "" + (second.divide(first, MATH_CONT).doubleValue());
        } else {
            return "" + second.doubleValue();
        }
    }

    private static String multiply(String currentVal, String memoryVal) {
        BigDecimal first = new BigDecimal(currentVal);
        BigDecimal second = new BigDecimal(memoryVal);
        return "" + first.multiply(second, MATH_CONT).doubleValue();
    }

    private static String getResult(String currentVal, String memoryVal) {
        return currentVal;
    }

    public String getActName() {
        return actName;
    }

    public Action getAction() {
        return act;
    }

    public static Actions getActionByName(String actName) {
        for (Actions act: Actions.values()) {
            if (act.actName.equals(actName)) {
                return act;
            }
        }
        return getResult;
    }

    public interface Action {
        String act(String firstVal, String secondVal);
    }
}
