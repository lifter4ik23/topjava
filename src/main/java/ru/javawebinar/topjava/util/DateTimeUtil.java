package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static<T extends Comparable<T>> boolean isBetween(T localVal, T startVal, T endVal) {
        return localVal.compareTo(startVal) >= 0 && localVal.compareTo(endVal) <=0;
    }

    public static LocalDate parseDateFromJsp(String parsed) {
        if (parsed == null || parsed.equals("")) {
            return null;
        }
        return LocalDate.parse(parsed);
    }

    public static LocalTime parseTimeFromJsp(String parsed) {
        if (parsed == null || parsed.equals("")) {
            return null;
        }
        return LocalTime.parse(parsed);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
