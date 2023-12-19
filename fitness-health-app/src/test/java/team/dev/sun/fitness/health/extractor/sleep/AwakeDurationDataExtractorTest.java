package team.dev.sun.fitness.health.extractor.sleep;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

import co.tryterra.terraclient.models.v2.sleep.SleepDurationsData.Awake;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.model.sleep.AwakeDuration;
import team.dev.sun.fitness.health.model.sleep.SleepFitnessHealthData;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

class AwakeDurationDataExtractorTest {

  @Test
  void extractData() {

    AwakeDuration expected = getExpected();
    AwakeDurationDataExtractor awakeDurationDataExtractor = new AwakeDurationDataExtractor();
    SleepFitnessHealthData sleepFitnessHealthData = new SleepFitnessHealthData();
    awakeDurationDataExtractor.extractData(getAwakeData(), sleepFitnessHealthData);
    AwakeDuration actual = sleepFitnessHealthData.getAwakeDuration();
    assertAwakeDuration(expected, actual);
  }

  private void assertAwakeDuration(final AwakeDuration expected, final AwakeDuration actual) {

    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getShortInterruptionSeconds(), actual.getShortInterruptionSeconds());
    assertEquals(expected.getAwakeStateSeconds(), actual.getAwakeStateSeconds());
    assertEquals(expected.getLongInterruptionSeconds(), actual.getLongInterruptionSeconds());
    assertEquals(expected.getNumWakeupEvents(), actual.getNumWakeupEvents());
    assertEquals(expected.getWakeUpLatencySeconds(), actual.getWakeUpLatencySeconds());
    assertEquals(expected.getNumOutOfBedEvents(), actual.getNumOutOfBedEvents());
    assertEquals(expected.getSleepLatencySeconds(), actual.getSleepLatencySeconds());
  }

  private AwakeDuration getExpected() {

    AwakeDuration awakeDuration = new AwakeDuration();
    awakeDuration.setShortInterruptionSeconds(2d);
    awakeDuration.setAwakeStateSeconds(7d);
    awakeDuration.setLongInterruptionSeconds(6d);
    awakeDuration.setNumWakeupEvents(4);
    awakeDuration.setWakeUpLatencySeconds(1d);
    awakeDuration.setNumOutOfBedEvents(5);
    awakeDuration.setSleepLatencySeconds(3d);
    return awakeDuration;
  }

  @SneakyThrows
  private Awake getAwakeData() {

    String data = IOUtils.resourceToString(
        "unit-test-data/sleep_durations_awake_data.json", UTF_8, this.getClass().getClassLoader());
    return new ObjectMapper().readValue(data, Awake.class);
  }
}