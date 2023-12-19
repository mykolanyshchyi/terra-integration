package team.dev.sun.fitness.health.api.model;

import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIDNIGHT;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import java.time.ZonedDateTime;
import java.util.function.UnaryOperator;
import lombok.Getter;

@Getter
public enum DateRange {

  CUSTOM(null, null),
  YESTERDAY(date -> startOfDay(date, 1), date -> endOfDay(date.minusDays(1))),
  LAST_7_DAYS(date -> startOfDay(date, 6), DateRange::endOfDay),
  LAST_14_DAYS(date -> startOfDay(date, 13), DateRange::endOfDay),
  LAST_30_DAYS(date -> startOfDay(date, 29), DateRange::endOfDay),
  LAST_90_DAYS(date -> startOfDay(date, 89), DateRange::endOfDay),
  LAST_MONTH(date -> firstDayOfMonth(date, 1), DateRange::lastDayOfPreviosMonth),
  LAST_12_MONTHS(date -> firstDayOfMonth(date, 12), DateRange::lastDayOfPreviosMonth);

  private final UnaryOperator<ZonedDateTime> startFunction;

  private final UnaryOperator<ZonedDateTime> endFunction;

  DateRange(final UnaryOperator<ZonedDateTime> startFunction,
            final UnaryOperator<ZonedDateTime> endFunction) {

    this.startFunction = startFunction;
    this.endFunction = endFunction;
  }

  private static ZonedDateTime startOfDay(ZonedDateTime dateTime, int days) {

    return dateTime.minusDays(days).with(MIDNIGHT);
  }

  private static ZonedDateTime endOfDay(ZonedDateTime dateTime) {

    return dateTime.with(MAX);
  }

  private static ZonedDateTime firstDayOfMonth(ZonedDateTime dateTime, int months) {

    return dateTime.minusMonths(months).withDayOfMonth(1).with(MIDNIGHT);
  }

  private static ZonedDateTime lastDayOfPreviosMonth(ZonedDateTime dateTime) {

    return dateTime.minusMonths(1).with(lastDayOfMonth()).with(MAX);
  }
}
