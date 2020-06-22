package com.dertsizvebugsiz.news.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Formatter {

    public static String formatCurrencyValue(double price){
        String strFormat;
        if(price >= 100)
            strFormat = String.valueOf(round(price, 2));
        else if(price >= 10)
            strFormat = String.valueOf(round(price, 3));
        else if(price >= 1)
            strFormat = String.valueOf(round(price, 4));
        else if(price >= 0.1)
            strFormat = String.valueOf(round(price, 5));
        else if(price >= 0.01)
            strFormat = String.valueOf(round(price, 6));
        else if(price >= 0.001)
            strFormat = String.valueOf(round(price, 7));
        else
            strFormat = String.valueOf(round(price, 8));
        return "$" + strFormat.replace('.',',');
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
