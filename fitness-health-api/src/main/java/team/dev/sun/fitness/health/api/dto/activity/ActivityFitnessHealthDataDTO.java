package team.dev.sun.fitness.health.api.dto.activity;

import team.dev.sun.fitness.health.api.dto.BaseFitnessHealthDataDTO;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityFitnessHealthDataDTO extends BaseFitnessHealthDataDTO implements Serializable {

  private ActiveDurationDTO activeDuration;

  private CaloriesDTO calories;

  private DistanceDTO distance;
}
