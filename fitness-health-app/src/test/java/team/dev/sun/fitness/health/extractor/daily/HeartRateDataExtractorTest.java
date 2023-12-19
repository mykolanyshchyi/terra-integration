package team.dev.sun.fitness.health.extractor.daily;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import co.tryterra.terraclient.models.v2.common.HeartRateData;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.model.daily.DailyFitnessHealthData;
import team.dev.sun.fitness.health.model.daily.HeartRate;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HeartRateDataExtractorTest {

  private HeartRateDataExtractor heartRateDataExtractor;

  private DailyFitnessHealthData dailyFitnessHealthData;

  @BeforeEach
  void setUp() {

    heartRateDataExtractor = new HeartRateDataExtractor();
    dailyFitnessHealthData = new DailyFitnessHealthData();
  }

  @Test
  void extractDataWithEmptySummary() {

    heartRateDataExtractor.extract(new HeartRateData(), dailyFitnessHealthData);
    assertNull(dailyFitnessHealthData.getHeartRate());
  }

  @Test
  void extractData() {

    HeartRate expected = getExpected();
    heartRateDataExtractor.extract(getHeartRateData(), dailyFitnessHealthData);
    HeartRate actual = dailyFitnessHealthData.getHeartRate();
    assertNotNull(actual);
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getMaxHrBpm(), actual.getMaxHrBpm());
    assertEquals(expected.getRestingHrBpm(), actual.getRestingHrBpm());
    assertEquals(expected.getAvgHrvRmssd(), actual.getAvgHrvRmssd());
    assertEquals(expected.getMinHrBpm(), actual.getMinHrBpm());
    assertEquals(expected.getUserMaxHrBpm(), actual.getUserMaxHrBpm());
    assertEquals(expected.getAvgHrvSdnn(), actual.getAvgHrvSdnn());
    assertEquals(expected.getAvgHrBpm(), actual.getAvgHrBpm());
  }

  private HeartRate getExpected() {

    HeartRate heartRate = new HeartRate();
    heartRate.setMaxHrBpm(68);
    heartRate.setRestingHrBpm(52);
    heartRate.setAvgHrvRmssd(34d);
    heartRate.setMinHrBpm(61);
    heartRate.setUserMaxHrBpm(72);
    heartRate.setAvgHrvSdnn(98d);
    heartRate.setAvgHrBpm(56);
    return heartRate;
  }

  @SneakyThrows
  private HeartRateData getHeartRateData() {

    String data = IOUtils.resourceToString("unit-test-data/heart_rate_data.json", UTF_8, this.getClass().getClassLoader());
    return new ObjectMapper().readValue(data, HeartRateData.class);
  }
}