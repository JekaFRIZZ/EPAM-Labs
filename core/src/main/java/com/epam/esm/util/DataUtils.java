package com.epam.esm.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {

    public static LocalDateTime parseLocalDateType(String date, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(date, dateTimeFormatter);
    }
}
