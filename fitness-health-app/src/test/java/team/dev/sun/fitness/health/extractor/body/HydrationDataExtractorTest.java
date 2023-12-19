package team.dev.sun.fitness.health.extractor.body;

import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import co.tryterra.terraclient.models.v2.body.HydrationData;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import team.dev.sun.fitness.health.model.body.Hydration;
import team.dev.sun.fitness.health.model.body.HydrationLevel;
import team.dev.sun.fitness.health.model.body.HydrationMeasurement;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HydrationDataExtractorTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  private HydrationDataExtractor hydrationDataExtractor;

  private BodyFitnessHealthData bodyFitnessHealthData;

  @BeforeEach
  void setUp() {

    hydrationDataExtractor = new HydrationDataExtractor();
    bodyFitnessHealthData = new BodyFitnessHealthData();
  }

  @Test
  void extractDataWoMeasurementsAndLevels() {

    String resource = "{\"day_total_water_consumption_ml\": 546}";
    hydrationDataExtractor.extract(getHydrationData(resource), bodyFitnessHealthData);
    Hydration actual = bodyFitnessHealthData.getHydration();
    assertNotNull(actual);
    assertEquals(546, actual.getDayTotalWaterConsumptionMl());
    assertNull(actual.getHydrationLevels());
    assertNull(actual.getHydrationMeasurements());
  }

  @Test
  void extractDataWoMeasurements() {

    HydrationLevel expectedLevel = getExpectedHydrationLevel();
    HydrationData hydrationData = getHydrationData(readResource("hydration_data_wo_measurement.json"));
    hydrationDataExtractor.extract(hydrationData, bodyFitnessHealthData);
    Hydration actual = bodyFitnessHealthData.getHydration();
    assertNotNull(actual);
    assertEquals(789, actual.getDayTotalWaterConsumptionMl());
    assertNotNull(actual.getHydrationLevels());
    assertNull(actual.getHydrationMeasurements());
    assertEquals(1, actual.getHydrationLevels().size());
    HydrationLevel actualLevel = actual.getHydrationLevels().get(0);
    assertEquals(expectedLevel.getLevel(), actualLevel.getLevel());
    assertEquals(expectedLevel.getTimestamp(), actualLevel.getTimestamp());
    assertNotNull(actualLevel.getHydration());
  }

  @Test
  void extractDataWoLevels() {

    HydrationMeasurement expectedMeasurement = getExpectedHydrationMeasurement();
    HydrationData hydrationData = getHydrationData(readResource("hydration_data_wo_level.json"));
    hydrationDataExtractor.extract(hydrationData, bodyFitnessHealthData);
    Hydration actual = bodyFitnessHealthData.getHydration();
    assertNotNull(actual);
    assertEquals(890, actual.getDayTotalWaterConsumptionMl());
    assertNull(actual.getHydrationLevels());
    assertNotNull(actual.getHydrationMeasurements());
    assertEquals(1, actual.getHydrationMeasurements().size());
    HydrationMeasurement actualMeasurement = actual.getHydrationMeasurements().get(0);
    assertEquals(expectedMeasurement.getHydrationKg(), actualMeasurement.getHydrationKg());
    assertEquals(expectedMeasurement.getTimestamp(), actualMeasurement.getTimestamp());
    assertNotNull(actualMeasurement.getHydration());
  }

  @Test
  void extractData() {

    Hydration expected = getExpectedHydration();
    HydrationData hydrationData = getHydrationData(readResource("hydration_data.json"));
    hydrationDataExtractor.extract(hydrationData, bodyFitnessHealthData);
    Hydration actual = bodyFitnessHealthData.getHydration();
    assertNotNull(actual);
    assertEquals(expected.getDayTotalWaterConsumptionMl(), actual.getDayTotalWaterConsumptionMl());

    assertNotNull(actual.getHydrationMeasurements());
    assertEquals(1, actual.getHydrationMeasurements().size());
    HydrationMeasurement expectedMeasurement = expected.getHydrationMeasurements().get(0);
    HydrationMeasurement actualMeasurement = actual.getHydrationMeasurements().get(0);
    assertEquals(expectedMeasurement.getHydrationKg(), actualMeasurement.getHydrationKg());
    assertEquals(expectedMeasurement.getTimestamp(), actualMeasurement.getTimestamp());
    assertNotNull(actualMeasurement.getHydration());

    assertNotNull(actual.getHydrationLevels());
    assertEquals(1, actual.getHydrationLevels().size());
    HydrationLevel expectedLevel = expected.getHydrationLevels().get(0);
    HydrationLevel actualLevel = actual.getHydrationLevels().get(0);
    assertEquals(expectedLevel.getLevel(), actualLevel.getLevel());
    assertEquals(expectedLevel.getTimestamp(), actualLevel.getTimestamp());
    assertNotNull(actualLevel.getHydration());
  }

  private Hydration getExpectedHydration() {

    Hydration hydration = new Hydration();
    hydration.setDayTotalWaterConsumptionMl(789);
    hydration.setHydrationMeasurements(List.of(getExpectedHydrationMeasurement()));
    hydration.setHydrationLevels(List.of(getExpectedHydrationLevel()));
    return hydration;
  }

  private HydrationMeasurement getExpectedHydrationMeasurement() {

    HydrationMeasurement measurement = new HydrationMeasurement();
    measurement.setHydrationKg(3d);
    measurement.setTimestamp(ZonedDateTime.of(2023, 2, 13, 11, 33, 34, 0, UTC));
    return measurement;
  }

  private HydrationLevel getExpectedHydrationLevel() {

    HydrationLevel level = new HydrationLevel();
    level.setLevel(2);
    level.setTimestamp(ZonedDateTime.of(2023, 2, 12, 21, 46, 21, 0, UTC));
    return level;
  }

  @SneakyThrows
  private String readResource(String name) {

    return IOUtils.resourceToString("unit-test-data/" + name, StandardCharsets.UTF_8, this.getClass().getClassLoader());
  }

  @SneakyThrows
  private HydrationData getHydrationData(String data) {

    return objectMapper.readValue(data, HydrationData.class);
  }
}