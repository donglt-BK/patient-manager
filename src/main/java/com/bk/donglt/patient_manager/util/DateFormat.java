package com.bk.donglt.patient_manager.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    public static final String DATE_FORMAT = "yyyy-MM-dd", DATE_TIME_FORMAT = "hh:mm";
    private static final Logger LOGGER = LoggerFactory.getLogger(DateFormat.class);

    public static Date parse(String text, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(text);
        } catch (ParseException e) {
            LOGGER.error("Error while parsing date, return current time instead");
            return new Date();
        }
    }

    public static Date parseDate(String text) {
        return parse(text, DATE_FORMAT);
    }

    public static String format(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
