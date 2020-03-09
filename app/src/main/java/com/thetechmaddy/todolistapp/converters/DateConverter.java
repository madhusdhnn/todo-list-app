package com.thetechmaddy.todolistapp.converters;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Long fromDate(Date date) {
        if (date == null) {
            return 0L;
        }
        return date.getTime();
    }

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return new Date(timestamp);
    }

}
