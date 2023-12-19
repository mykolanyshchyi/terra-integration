package team.dev.sun.fitness.health.extractor.sleep;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

import co.tryterra.terraclient.models.v2.sleep.SleepDurationsData.Asleep;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.model.sleep.AsleepDuration;
import team.dev.sun.fitness.health.model.sleep.SleepFitnessHealthData;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

class AsleepDurationDataExtractorTest {

  @Test
  void extractData() {

    AsleepDuration expected = getExpected();
    AsleepDurationDataExtractor asleepDurationDataExtractor = new AsleepDurationDataExtractor();
    SleepFitnessHealthData sleepFitnessHealthData = new SleepFitnessHealthData();
    asleepDurationDataExtractor.extractData(getAsleepData(), sleepFitnessHealthData);
    AsleepDuration actual = sleepFitnessHealthData.getAsleepDuration();
    assertAsleepDuration(expected, actual);
  }

  private void assertAsleepDuration(final AsleepDuration expected, final AsleepDuration actual) {

    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getLightSleepStateSeconds(), actual.getLightSleepStateSeconds());
    assertEquals(expected.getAsleepStateSeconds(), actual.getAsleepStateSeconds());
    assertEquals(expected.getNumRemEvents(), actual.getNumRemEvents());
    assertEquals(expected.getRemSleepStateSeconds(), actual.getRemSleepStateSeconds());
    assertEquals(expected.getDeepSleepStateSeconds(), actual.getDeepSleepStateSeconds());
  }

  private AsleepDuration getExpected() {

    AsleepDuration asleepDuration = new AsleepDuration();
    asleepDuration.setLightSleepStateSeconds(329d);
    asleepDuration.setAsleepStateSeconds(225d);
    asleepDuration.setNumRemEvents(2345);
    asleepDuration.setRemSleepStateSeconds(103d);
    asleepDuration.setDeepSleepStateSeconds(255d);
    return asleepDuration;
  }

  @SneakyThrows
  private Asleep getAsleepData() {

    String data = IOUtils.resourceToString(
        "unit-test-data/sleep_durations_asleep_data.json", UTF_8, this.getClass().getClassLoader());
    return new ObjectMapper().readValue(data, Asleep.class);
  }
}