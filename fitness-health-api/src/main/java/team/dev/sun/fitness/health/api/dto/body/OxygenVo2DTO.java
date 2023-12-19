package team.dev.sun.fitness.health.api.dto.body;

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
public class OxygenVo2DTO implements Serializable {

  private Long id;

  private Double vo2MaxMlPerMinPerKg;

  private ZonedDateTime timestamp;
}