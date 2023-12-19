package team.dev.sun.fitness.health.mapper.sleep;

import static org.junit.jupiter.api.Assertions.assertEquals;

import team.dev.sun.fitness.health.api.dto.sleep.AwakeDurationDTO;
import team.dev.sun.fitness.health.model.sleep.AwakeDuration;
import org.junit.jupiter.api.Test;

class AwakeDurationMapperTest {

  private static final Long ID = 10L;

  private static final Double SHORT_INTERRUPTION_SECONDS = 20d;

  private static final Double AWAKE_STATE_SECONDS = 30d;

  private static final Double LONG_INTERRUPTION_SECONDS = 40d;

  private static final Integer NUM_WAKEUP_EVENTS = 50;

  private static final Double WAKEUP_LATENCY_SECONDS = 60d;

  private static final Integer NUM_OUT_OF_BED_EVENTS = 70;

  private static final Double SLEEP_LATENCY_SECONDS = 80d;

  @Test
  void mapAwakeDuration() {

    AwakeDurationDTO expected = getDto();
    AwakeDurationMapper awakeDurationMapper = new AwakeDurationMapper();
    AwakeDurationDTO actual = awakeDurationMapper.map(getEntity());
    assertEquals(expected, actual);
  }

  private AwakeDuration getEntity() {

    AwakeDuration awakeDuration = new AwakeDuration();
    awakeDuration.setId(ID);
    awakeDuration.setShortInterruptionSeconds(SHORT_INTERRUPTION_SECONDS);
    awakeDuration.setAwakeStateSeconds(AWAKE_STATE_SECONDS);
    awakeDuration.setLongInterruptionSeconds(LONG_INTERRUPTION_SECONDS);
    awakeDuration.setNumWakeupEvents(NUM_WAKEUP_EVENTS);
    awakeDuration.setWakeUpLatencySeconds(WAKEUP_LATENCY_SECONDS);
    awakeDuration.setNumOutOfBedEvents(NUM_OUT_OF_BED_EVENTS);
    awakeDuration.setSleepLatencySeconds(SLEEP_LATENCY_SECONDS);
    return awakeDuration;
  }

  private AwakeDurationDTO getDto() {

    return AwakeDurationDTO.builder()
                           .id(ID)
                           .shortInterruptionSeconds(SHORT_INTERRUPTION_SECONDS)
                           .awakeStateSeconds(AWAKE_STATE_SECONDS)
                           .longInterruptionSeconds(LONG_INTERRUPTION_SECONDS)
                           .numWakeupEvents(NUM_WAKEUP_EVENTS)
                           .wakeUpLatencySeconds(WAKEUP_LATENCY_SECONDS)
                           .numOutOfBedEvents(NUM_OUT_OF_BED_EVENTS)
                           .sleepLatencySeconds(SLEEP_LATENCY_SECONDS)
                           .build();
  }
}