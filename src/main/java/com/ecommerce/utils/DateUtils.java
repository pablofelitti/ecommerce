package com.ecommerce.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateUtils {

    private static final String ISO_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * Converts Date to ISO format String
     *
     * @param date Date to format
     * @return ISO format of provided date
     */
    public String convertToISOFormat(final Date date) {
        SimpleDateFormat format = new SimpleDateFormat(ISO_DATETIME_FORMAT);
        return format.format(date);
    }
}
