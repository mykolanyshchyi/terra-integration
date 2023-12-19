package team.dev.sun.fitness.health.mapper.activity;

import static team.dev.sun.fitness.health.api.model.ActivityLevelType.MEDIUM_INTENSITY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import team.dev.sun.fitness.health.api.dto.activity.ActivityLevelDTO;
import team.dev.sun.fitness.health.api.model.ActivityLevelType;
import team.dev.sun.fitness.health.model.activity.ActivityLevel;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class ActivityLevelMapperTest {

  private static final Long ID = 45L;

  private static final ActivityLevelType LEVEL = MEDIUM_INTENSITY;

  private static final ZonedDateTime TIMESTAMP = ZonedDateTime.now();

  @Test
  void mapActivityLevel() {

    ActivityLevelDTO expected = getDto();
    ActivityLevelMapper activityLevelMapper = new ActivityLevelMapper();
    ActivityLevelDTO actual = activityLevelMapper.map(getEntity());
    assertEquals(expected, actual);
  }

  private ActivityLevel getEntity() {

    ActivityLevel activityLevel = new ActivityLevel();
    activityLevel.setId(ID);
    activityLevel.setLevel(LEVEL);
    activityLevel.setTimestamp(TIMESTAMP);
    return activityLevel;
  }

  private ActivityLevelDTO getDto() {

    return ActivityLevelDTO.builder()
                           .id(ID)
                           .level(LEVEL)
                           .timestamp(TIMESTAMP)
                           .build();
  }
}