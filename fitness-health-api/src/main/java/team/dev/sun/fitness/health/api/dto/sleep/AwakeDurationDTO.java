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
public class AwakeDurationDTO implements Serializable {

  private Long id;

  private Double shortInterruptionSeconds;

  private Double awakeStateSeconds;

  private Double longInterruptionSeconds;

  private Integer numWakeupEvents;

  private Double wakeUpLatencySeconds;

  private Integer numOutOfBedEvents;

  private Double sleepLatencySeconds;
}
