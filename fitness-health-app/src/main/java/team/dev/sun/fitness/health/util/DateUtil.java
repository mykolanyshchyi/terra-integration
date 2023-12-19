package team.dev.sun.fitness.health.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class DateUtil {

  public static ZonedDateTime parseDate(String timestamp) {

    if (timestamp == null) {
      return null;
    }
    return ZonedDateTime.parse(timestamp, DateTimeFormatter.ISO_DATE_TIME);
  }
}
