package team.dev.sun.fitness.health.api.dto.body;

import team.dev.sun.fitness.health.api.model.TemperatureType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureDTO implements Serializable {

  private Long id;

  private TemperatureType type;

  private Double temperatureCelsius;

  private ZonedDateTime timestamp;
}
