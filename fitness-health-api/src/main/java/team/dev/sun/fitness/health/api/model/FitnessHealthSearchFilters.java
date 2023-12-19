package team.dev.sun.fitness.health.api.model;

import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FitnessHealthSearchFilters extends DateRangeFilters {

  private Long clientId;

  private Long userId;

  private String deviceId;

  private Provider provider;

  private Set<DataType> dataTypes;
}
