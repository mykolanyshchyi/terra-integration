package team.dev.sun.fitness.health.api.model;

import static team.dev.sun.fitness.health.api.model.DateRange.CUSTOM;
import static java.time.ZonedDateTime.now;
import static lombok.AccessLevel.NONE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public abstract class DateRangeFilters {

  @Getter(NONE)
  private ZonedDateTime fromDate;

  @Getter(NONE)
  private ZonedDateTime toDate;

  private DateRange dateRange;

  @JsonIgnore
  @AssertTrue(message = "error.invalid.date.range")
  public boolean isDateRangeValid() {

    return fromDate == null || toDate == null || fromDate.isBefore(toDate) || fromDate.equals(toDate);
  }

  public ZonedDateTime getFromDate() {

    return dateRange == null || dateRange == CUSTOM
        ? fromDate
        : dateRange.getStartFunction().apply(now());
  }

  public ZonedDateTime getToDate() {

    return dateRange == null || dateRange == CUSTOM
        ? toDate
        : dateRange.getEndFunction().apply(now());
  }
}