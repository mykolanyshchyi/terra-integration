package team.dev.sun.fitness.health.util;

import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class DateUtilTest {

  @Test
  void dateIsNull() {

    ZonedDateTime actual = DateUtil.parseDate(null);
    assertNull(actual);
  }

  @Test
  void parseDate() {

    ZonedDateTime expected = ZonedDateTime.of(2023, 3, 11, 21, 9, 46, 0, UTC);
    ZonedDateTime actual = DateUtil.parseDate("2023-03-11T21:09:46.0+00:00");
    assertEquals(expected, actual);
  }
}