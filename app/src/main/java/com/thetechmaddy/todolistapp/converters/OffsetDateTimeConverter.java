package com.thetechmaddy.todolistapp.converters;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeConverter {

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static OffsetDateTime toOffsetDateTime(String dateTimeString) {
        return OffsetDateTime.parse(dateTimeString, FORMATTER);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static String fromOffsetDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime.format(FORMATTER);
    }

}
