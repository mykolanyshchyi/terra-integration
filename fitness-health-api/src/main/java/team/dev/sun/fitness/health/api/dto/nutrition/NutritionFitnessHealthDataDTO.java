package team.dev.sun.fitness.health.api.dto.nutrition;

import team.dev.sun.fitness.health.api.dto.BaseFitnessHealthDataDTO;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NutritionFitnessHealthDataDTO extends BaseFitnessHealthDataDTO implements Serializable {

  private Double waterMl;

  private NutritionMacrosDTO macros;

  private NutritionMicrosDTO micros;
}
