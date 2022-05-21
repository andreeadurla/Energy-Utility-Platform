package ds.assignment.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class ApplicationUtils {

    public static Timestamp getStartOfDay(LocalDate localDate, TimeZone timeZone) {

        int offset = timeZone.getOffset(new Date().getTime()) / 1000 / 60;

        LocalDateTime startOfDay = localDate.atTime(LocalTime.MIN).minusMinutes(offset);

        return Timestamp.valueOf(startOfDay);
    }

    public static Timestamp getEndOfDay(LocalDate localDate, TimeZone timeZone) {

        int offset = timeZone.getOffset(new Date().getTime()) / 1000 / 60;

        LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX).minusMinutes(offset);

        return Timestamp.valueOf(endOfDay);
    }

    public static String formatLocalDate(LocalDate localDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return formatter.format(localDate);
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return formatter.format(localDateTime);
    }
}
