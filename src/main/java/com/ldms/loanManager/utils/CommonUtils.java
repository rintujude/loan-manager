package com.ldms.loanManager.utils;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;

public class CommonUtils {
    public static Timestamp currentTimeStamp() {
        return new Timestamp(new java.util.Date().getTime());
    }
    public static String printObject(Object object) {
        return new Gson().toJson(object);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
