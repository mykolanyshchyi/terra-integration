package team.dev.sun.fitness.health.api.dto;

import team.dev.sun.fitness.health.api.model.DataType;
import team.dev.sun.fitness.health.api.model.Provider;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class BaseFitnessHealthDataDTO implements Serializable {

  private Long id;

  private Long clientId;

  private Long userId;

  private String deviceId;

  private Provider provider;

  private DataType dataType;

  private MetadataDTO metadata;

  private ZonedDateTime createdAt;
}
