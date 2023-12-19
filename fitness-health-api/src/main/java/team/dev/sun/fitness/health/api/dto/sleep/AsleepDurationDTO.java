package team.dev.sun.fitness.health.api.dto.sleep;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsleepDurationDTO implements Serializable {

  private Long id;

  private Double lightSleepStateSeconds;

  private Double asleepStateSeconds;

  private Integer numRemEvents;

  private Double remSleepStateSeconds;

  private Double deepSleepStateSeconds;
}
