package team.dev.sun.fitness.health.mapper.sleep;

import static org.junit.jupiter.api.Assertions.assertEquals;

import team.dev.sun.fitness.health.api.dto.sleep.AsleepDurationDTO;
import team.dev.sun.fitness.health.model.sleep.AsleepDuration;
import org.junit.jupiter.api.Test;

class AsleepDurationMapperTest {

  private static final Long ID = 5L;

  private static final Double LIGHT_SLEEP_STATE_SECONDS = 10d;

  private static final Double ASLEEP_STATE_SECONDS = 15d;

  private static final Integer NUMREM_EVENTS = 20;

  private static final Double REM_SLEEP_STATE_SECONDS = 25d;

  private static final Double DEEP_SLEEP_STATE_SECONDS = 30d;

  @Test
  void mapAsleepDuration() {

    AsleepDurationDTO expected = getDto();
    AsleepDurationMapper asleepDurationMapper = new AsleepDurationMapper();
    AsleepDurationDTO actual = asleepDurationMapper.map(getEntity());
    assertEquals(expected, actual);
  }

  private AsleepDuration getEntity() {

    AsleepDuration asleepDuration = new AsleepDuration();
    asleepDuration.setId(ID);
    asleepDuration.setLightSleepStateSeconds(LIGHT_SLEEP_STATE_SECONDS);
    asleepDuration.setAsleepStateSeconds(ASLEEP_STATE_SECONDS);
    asleepDuration.setNumRemEvents(NUMREM_EVENTS);
    asleepDuration.setRemSleepStateSeconds(REM_SLEEP_STATE_SECONDS);
    asleepDuration.setDeepSleepStateSeconds(DEEP_SLEEP_STATE_SECONDS);
    return asleepDuration;
  }

  private AsleepDurationDTO getDto() {

    return AsleepDurationDTO.builder()
                            .id(ID)
                            .lightSleepStateSeconds(LIGHT_SLEEP_STATE_SECONDS)
                            .asleepStateSeconds(ASLEEP_STATE_SECONDS)
                            .numRemEvents(NUMREM_EVENTS)
                            .remSleepStateSeconds(REM_SLEEP_STATE_SECONDS)
                            .deepSleepStateSeconds(DEEP_SLEEP_STATE_SECONDS)
                            .build();
  }
}