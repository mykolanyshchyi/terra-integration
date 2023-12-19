package team.dev.sun.fitness.health.extractor.body;

import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import co.tryterra.terraclient.models.v2.common.OxygenData;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import team.dev.sun.fitness.health.model.body.Oxygen;
import team.dev.sun.fitness.health.model.body.OxygenSaturation;
import team.dev.sun.fitness.health.model.body.OxygenVo2;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OxygenDataExtractorTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  private OxygenDataExtractor oxygenDataExtractor;

  private BodyFitnessHealthData bodyFitnessHealthData;

  @BeforeEach
  void setUp() {

    oxygenDataExtractor = new OxygenDataExtractor();
    bodyFitnessHealthData = new BodyFitnessHealthData();
  }

  @Test
  void extractDataWoOxygenSaturationAndOxygenVo2() {

    String data = "{\"vo2max_ml_per_min_per_kg\": 23, \"avg_saturation_percentage\": 87}";
    oxygenDataExtractor.extract(getOxygenData(data), bodyFitnessHealthData);
    Oxygen oxygen = bodyFitnessHealthData.getOxygen();
    assertNotNull(oxygen);
    assertNull(oxygen.getOxygenSaturations());
    assertNull(oxygen.getOxygenVo2s());
    assertEquals(23, oxygen.getVo2MaxMlPerMinPerKg());
    assertEquals(87, oxygen.getAvgSaturationPercentage());
  }

  @Test
  void extractDataWoOxygenSaturation() {

    OxygenVo2 expectedOxygenVo2 = getExpectedOxygenVo2();
    oxygenDataExtractor.extract(getOxygenData(readResource("oxygen_data_wo_saturation.json")), bodyFitnessHealthData);
    Oxygen oxygen = bodyFitnessHealthData.getOxygen();
    assertNotNull(oxygen);
    assertEquals(23, oxygen.getVo2MaxMlPerMinPerKg());
    assertEquals(87, oxygen.getAvgSaturationPercentage());
    assertNull(oxygen.getOxygenSaturations());
    assertOxygenVo2(expectedOxygenVo2, oxygen);
  }

  @Test
  void extractDataWoOxygenVo2() {

    OxygenSaturation expectedOxygenSaturation = getExpectedOxygenSaturation();
    oxygenDataExtractor.extract(getOxygenData(readResource("oxygen_data_wo_vo2.json")), bodyFitnessHealthData);
    Oxygen oxygen = bodyFitnessHealthData.getOxygen();
    assertNotNull(oxygen);
    assertEquals(23, oxygen.getVo2MaxMlPerMinPerKg());
    assertEquals(87, oxygen.getAvgSaturationPercentage());
    assertOxygenSaturation(expectedOxygenSaturation, oxygen);
    assertNull(oxygen.getOxygenVo2s());
  }

  @Test
  void extractData() {

    OxygenVo2 expectedOxygenVo2 = getExpectedOxygenVo2();
    OxygenSaturation expectedOxygenSaturation = getExpectedOxygenSaturation();
    oxygenDataExtractor.extract(getOxygenData(readResource("oxygen_data.json")), bodyFitnessHealthData);
    Oxygen oxygen = bodyFitnessHealthData.getOxygen();
    assertNotNull(oxygen);
    assertEquals(23, oxygen.getVo2MaxMlPerMinPerKg());
    assertEquals(87, oxygen.getAvgSaturationPercentage());
    assertOxygenSaturation(expectedOxygenSaturation, oxygen);
    assertOxygenVo2(expectedOxygenVo2, oxygen);
  }

  private void assertOxygenSaturation(OxygenSaturation expected, Oxygen oxygen) {

    assertNotNull(oxygen.getOxygenSaturations());
    assertEquals(1, oxygen.getOxygenSaturations().size());
    OxygenSaturation actual = oxygen.getOxygenSaturations().get(0);
    assertEquals(expected.getPercentage(), actual.getPercentage());
    assertEquals(expected.getTimestamp(), actual.getTimestamp());
    assertEquals(expected.getId(), actual.getId());
    assertNotNull(actual.getOxygen());
  }

  private void assertOxygenVo2(OxygenVo2 expected, Oxygen oxygen) {

    assertNotNull(oxygen.getOxygenVo2s());
    assertEquals(1, oxygen.getOxygenVo2s().size());
    OxygenVo2 actual = oxygen.getOxygenVo2s().get(0);
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getVo2MaxMlPerMinPerKg(), actual.getVo2MaxMlPerMinPerKg());
    assertEquals(expected.getTimestamp(), actual.getTimestamp());
  }

  private OxygenSaturation getExpectedOxygenSaturation() {

    OxygenSaturation saturation = new OxygenSaturation();
    saturation.setTimestamp(ZonedDateTime.of(2023, 1, 17, 11, 53, 18, 0, UTC));
    saturation.setPercentage(84d);
    return saturation;
  }

  private OxygenVo2 getExpectedOxygenVo2() {

    OxygenVo2 vo2 = new OxygenVo2();
    vo2.setTimestamp(ZonedDateTime.of(2023, 1, 17, 22, 45, 13, 0, UTC));
    vo2.setVo2MaxMlPerMinPerKg(345d);
    return vo2;
  }

  @SneakyThrows
  private String readResource(String name) {

    return IOUtils.resourceToString("unit-test-data/" + name, StandardCharsets.UTF_8, this.getClass().getClassLoader());
  }

  @SneakyThrows
  private OxygenData getOxygenData(String data) {

    return objectMapper.readValue(data, OxygenData.class);
  }
}