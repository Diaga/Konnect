package com.pika.knct;

import android.text.format.DateFormat;

import java.util.Calendar;

public class Utils {
    static String timestampToDateTime(String timestamp, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(timestamp));
        return DateFormat.format(format, cal).toString();
    }
}
