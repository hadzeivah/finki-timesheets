package com.finki.timesheets.service.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class StringUtils {
    private StringUtils (){}
    public static String formatDateToString_DDMMYYYY(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }
}
