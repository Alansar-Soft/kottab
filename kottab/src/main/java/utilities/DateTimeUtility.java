package utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DateTimeUtility {
	public static String fetchFormatedCurrentDateTime() {
		return LocalDateTime.now().format(fetchDateTimeFormatter());
	}

	public static DateTimeFormatter fetchDateTimeFormatter() {
		return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
	}

	public static String formatDateTime(LocalDateTime dateTime) {
		if (dateTime == null)
			return "";
		return dateTime.format(fetchDateTimeFormatter());
	}

	public static LocalDateTime parseDateTime(String dateTime) {
		if (ObjectChecker.isEmptyOrZeroOrNull(dateTime))
			return null;
		return LocalDateTime.parse(dateTime, fetchDateTimeFormatter());
	}
}
