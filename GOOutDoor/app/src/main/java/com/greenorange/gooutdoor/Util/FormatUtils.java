package com.greenorange.gooutdoor.Util;

import android.content.Context;

import com.greenorange.outdoorhelper.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Formatter;
import java.util.Locale;

public class FormatUtils {
    /*
     * NumberFormat isn't synchronized, so a separate instance must be created for each thread
     * http://developer.android.com/reference/java/text/NumberFormat.html
     */
    private static final ThreadLocal<NumberFormat> IntegerInstance = new ThreadLocal<NumberFormat>() {
        @Override
        protected NumberFormat initialValue() {
            return NumberFormat.getIntegerInstance();
        }
    };

    private static final ThreadLocal<DecimalFormat> DecimalInstance = new ThreadLocal<DecimalFormat>() {
        @Override
        protected DecimalFormat initialValue() {
            return (DecimalFormat) DecimalFormat.getInstance();
        }
    };

    /*
     * returns the passed integer formatted with thousands-separators based on the current locale
     */
    public static final String formatInt(int value) {
        return IntegerInstance.get().format(value).toString();
    }

    public static final String formatDecimal(int value) {
        return DecimalInstance.get().format(value).toString();
    }
    public  static String  getMillSecTime(Context context, long ms){
        return getSecTime(context, ms/1000);
    }
    public  static String  getSecTime(Context context, long secs){
        int sec = (int)(secs % 60);
        secs   /= 60;
        int min = (int)(secs % 60);
        secs   /= 60;
        int hour = (int)(secs % 60);//make sure hour is limit 60.
        return context.getString(R.string.sporttime,hour,min,sec);
    }
    public static String getFloat2to1(Context context, float data){
        if(data >= 99.0f){
            data %= 100.0f;
        }
        return context.getString(R.string.float_2_1, data);//is data == 100.it return 00.0
    }
    public static String getFloat3to1(Context context, float data){
        if(data >= 999.0f){
            data %= 1000.0f;
        }
        return context.getString(R.string.float_3_1, data);//is data == 100.it return 00.0
    }
    public static String getDouble3to1(Context context, double data){
        return getFloat3to1(context, (float)data);
    }
}
