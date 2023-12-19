package team.dev.sun.fitness.health.mapper.activity;

import team.dev.sun.fitness.health.api.dto.activity.CaloriesDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.activity.Calories;
import org.springframework.stereotype.Component;

@Component
public class CaloriesMapper extends AbstractMapper<Calories, CaloriesDTO> {

  @Override
  public CaloriesDTO map(final Calories source) {

    return CaloriesDTO.builder()
                      .id(source.getId())
                      .netIntakeCalories(source.getNetIntakeCalories())
                      .bmrCalories(source.getBmrCalories())
                      .totalBurnedCalories(source.getTotalBurnedCalories())
                      .netActivityCalories(source.getNetActivityCalories())
                      .build();
  }
}
