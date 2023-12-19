package team.dev.sun.fitness.health.extractor.daily;

import static java.time.ZoneOffset.UTC;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import co.tryterra.terraclient.models.v2.daily.StressData;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.model.daily.DailyFitnessHealthData;
import team.dev.sun.fitness.health.model.daily.Stress;
import team.dev.sun.fitness.health.model.daily.StressSample;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StressDataExtractorTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  private StressDataExtractor stressDataExtractor;

  private DailyFitnessHealthData dailyFitnessHealthData;

  @BeforeEach
  void setUp() {

    stressDataExtractor = new StressDataExtractor();
    dailyFitnessHealthData = new DailyFitnessHealthData();
  }

  @Test
  void extractDataWoSamples() {

    Stress expected = getExpectedStress();
    StressData stressData = getStressData(readResource("stress_data_wo_samples.json"));
    stressDataExtractor.extractData(stressData, dailyFitnessHealthData);
    Stress actual = dailyFitnessHealthData.getStress();
    assertStress(expected, actual);
  }

  @Test
  void extractData() {

    Stress expected = getExpectedStress();
    expected.setSamples(List.of(getExpectedStressSample()));
    StressData stressData = getStressData(readResource("stress_data.json"));
    stressDataExtractor.extractData(stressData, dailyFitnessHealthData);
    Stress actual = dailyFitnessHealthData.getStress();
    assertStress(expected, actual);
  }

  private void assertStress(final Stress expected, final Stress actual) {

    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getRestStressDurationSeconds(), actual.getRestStressDurationSeconds());
    assertEquals(expected.getStressDurationSeconds(), actual.getStressDurationSeconds());
    assertEquals(expected.getActivityStressDurationSeconds(), actual.getActivityStressDurationSeconds());
    assertEquals(expected.getAvgStressLevel(), actual.getAvgStressLevel());
    assertEquals(expected.getLowStressDurationSeconds(), actual.getLowStressDurationSeconds());
    assertEquals(expected.getMediumStressDurationSeconds(), actual.getMediumStressDurationSeconds());
    assertEquals(expected.getHighStressDurationSeconds(), actual.getHighStressDurationSeconds());
    assertEquals(expected.getMaxStressLevel(), actual.getMaxStressLevel());

    if (isNotEmpty(expected.getSamples())) {
      assertNotNull(actual.getSamples());
      assertEquals(1, actual.getSamples().size());
      assertStressSample(expected.getSamples().get(0), actual.getSamples().get(0));
    }
  }

  private void assertStressSample(final StressSample expected, final StressSample actual) {

    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getLevel(), actual.getLevel());
    assertEquals(expected.getTimestamp(), actual.getTimestamp());
    assertNotNull(actual.getStress());
  }

  private Stress getExpectedStress() {

    Stress stress = new Stress();
    stress.setRestStressDurationSeconds(4d);
    stress.setStressDurationSeconds(52d);
    stress.setActivityStressDurationSeconds(30d);
    stress.setAvgStressLevel(5d);
    stress.setLowStressDurationSeconds(3d);
    stress.setMediumStressDurationSeconds(27d);
    stress.setHighStressDurationSeconds(41d);
    stress.setMaxStressLevel(6d);
    return stress;
  }

  private StressSample getExpectedStressSample() {

    StressSample sample = new StressSample();
    sample.setLevel(9);
    sample.setTimestamp(ZonedDateTime.of(2023, 1, 13, 23, 23, 0, 0, UTC));
    return sample;
  }

  @SneakyThrows
  private String readResource(String name) {

    return IOUtils.resourceToString("unit-test-data/" + name, StandardCharsets.UTF_8, this.getClass().getClassLoader());
  }

  @SneakyThrows
  private StressData getStressData(String data) {

    return objectMapper.readValue(data, StressData.class);
  }
}