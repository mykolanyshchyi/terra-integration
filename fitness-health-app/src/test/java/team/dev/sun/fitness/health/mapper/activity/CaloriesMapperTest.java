package team.dev.sun.fitness.health.mapper.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import team.dev.sun.fitness.health.api.dto.activity.CaloriesDTO;
import team.dev.sun.fitness.health.model.activity.Calories;
import org.junit.jupiter.api.Test;

class CaloriesMapperTest {

  private static final Long ID = 5L;

  private static final Double NET_INTAKE_CALORIES = 10d;

  private static final Double BMR_CALORIES = 15d;

  private static final Double TOTAL_BURNED_CALORIES = 20d;

  private static final Double NET_ACTIVITY_CALORIES = 25d;

  @Test
  void mapCalories() {

    CaloriesMapper caloriesMapper = new CaloriesMapper();
    CaloriesDTO expected = getDto();
    CaloriesDTO actual = caloriesMapper.map(getEntity());
    assertEquals(expected, actual);
  }

  private Calories getEntity() {

    Calories calories = new Calories();
    calories.setId(ID);
    calories.setNetIntakeCalories(NET_INTAKE_CALORIES);
    calories.setBmrCalories(BMR_CALORIES);
    calories.setTotalBurnedCalories(TOTAL_BURNED_CALORIES);
    calories.setNetActivityCalories(NET_ACTIVITY_CALORIES);
    return calories;
  }

  private CaloriesDTO getDto() {

    return CaloriesDTO.builder()
                      .id(ID)
                      .netIntakeCalories(NET_INTAKE_CALORIES)
                      .bmrCalories(BMR_CALORIES)
                      .totalBurnedCalories(TOTAL_BURNED_CALORIES)
                      .netActivityCalories(NET_ACTIVITY_CALORIES)
                      .build();
  }
}