package utilities;

import java.time.*;
import java.time.format.*;
import java.util.Date;

public class DateTimeUtility
{
    public static String fetchFormatedCurrentDateTime()
    {
        return LocalDateTime.now().format(fetchDateTimeFormatter());
    }

    public static DateTimeFormatter fetchDateTimeFormatter()
    {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    }

    public static String formatDateTime(LocalDateTime dateTime)
    {
        if (dateTime == null)
            return "";
        return dateTime.format(fetchDateTimeFormatter());
    }

    public static LocalDateTime parseDateTime(String dateTime)
    {
        if (ObjectChecker.isEmptyOrZeroOrNull(dateTime))
            return null;
        return LocalDateTime.parse(dateTime, fetchDateTimeFormatter());
    }

    public static Date dateFromLocalDate(LocalDate localDate)
    {
        if (ObjectChecker.isEmptyOrZeroOrNull(localDate))
            return null;
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
