package team.dev.sun.fitness.health.api.dto.daily;

import team.dev.sun.fitness.health.api.dto.BaseFitnessHealthDataDTO;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DailyFitnessHealthDataDTO extends BaseFitnessHealthDataDTO implements Serializable {

  private HeartRateDTO heartRate;

  private ScoreDTO score;

  private StressDTO stress;
}
