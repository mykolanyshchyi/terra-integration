package team.dev.sun.fitness.health.api.dto.sleep;

import team.dev.sun.fitness.health.api.dto.BaseFitnessHealthDataDTO;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SleepFitnessHealthDataDTO extends BaseFitnessHealthDataDTO implements Serializable {

  private AwakeDurationDTO awakeDuration;

  private AsleepDurationDTO asleepDuration;
}
