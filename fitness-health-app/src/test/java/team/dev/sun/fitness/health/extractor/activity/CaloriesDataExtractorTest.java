package team.dev.sun.fitness.health.extractor.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import co.tryterra.terraclient.models.v2.common.CaloriesData;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.model.activity.ActivityFitnessHealthData;
import team.dev.sun.fitness.health.model.activity.Calories;
import java.io.InputStream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

class CaloriesDataExtractorTest {

  @Test
  void extractData() {

    Calories expected = getExpectedCalories();
    ActivityFitnessHealthData activityFitnessHealthData = new ActivityFitnessHealthData();
    CaloriesDataExtractor caloriesDataExtractor = new CaloriesDataExtractor();
    caloriesDataExtractor.extract(getCaloriesData(), activityFitnessHealthData);
    Calories actual = activityFitnessHealthData.getCalories();
    assertNotNull(actual);
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getNetIntakeCalories(), actual.getNetIntakeCalories());
    assertEquals(expected.getBmrCalories(), actual.getBmrCalories());
    assertEquals(expected.getTotalBurnedCalories(), actual.getTotalBurnedCalories());
    assertEquals(expected.getNetActivityCalories(), actual.getNetActivityCalories());
  }

  private Calories getExpectedCalories() {

    Calories calories = new Calories();
    calories.setNetIntakeCalories(760.5d);
    calories.setBmrCalories(10.595d);
    calories.setTotalBurnedCalories(1407.947605952413d);
    calories.setNetActivityCalories(307.94760d);
    return calories;
  }

  @SneakyThrows
  private CaloriesData getCaloriesData() {

    InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("unit-test-data/calories_data.json");
    return new ObjectMapper().readValue(resourceAsStream, CaloriesData.class);
  }
}