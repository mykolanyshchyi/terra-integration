package team.dev.sun.fitness.health.api.dto;

import team.dev.sun.fitness.health.api.dto.activity.ActivityFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.body.BodyFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.daily.DailyFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.nutrition.NutritionFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.sleep.SleepFitnessHealthDataDTO;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class FitnessHealthDataDTO implements Serializable {

  private List<ActivityFitnessHealthDataDTO> activityData;

  private List<BodyFitnessHealthDataDTO> bodyData;

  private List<DailyFitnessHealthDataDTO> dailyData;

  private List<NutritionFitnessHealthDataDTO> nutritionData;

  private List<SleepFitnessHealthDataDTO> sleepData;
}
