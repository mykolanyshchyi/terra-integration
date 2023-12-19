package team.dev.sun.fitness.health.extractor.activity;

import co.tryterra.terraclient.models.v2.common.CaloriesData;
import team.dev.sun.fitness.health.extractor.AbstractDataExtractor;
import team.dev.sun.fitness.health.model.activity.ActivityFitnessHealthData;
import team.dev.sun.fitness.health.model.activity.Calories;
import org.springframework.stereotype.Component;

@Component
public class CaloriesDataExtractor extends AbstractDataExtractor<CaloriesData, ActivityFitnessHealthData> {

  @Override
  protected void extractData(final CaloriesData source, final ActivityFitnessHealthData entity) {

    Calories calories = new Calories();
    calories.setNetIntakeCalories(source.getNetIntakeCalories());
    calories.setBmrCalories(source.getBmrCalories());
    calories.setTotalBurnedCalories(source.getTotalBurnedCalories());
    calories.setNetActivityCalories(source.getNetActivityCalories());
    entity.setCalories(calories);
  }
}
