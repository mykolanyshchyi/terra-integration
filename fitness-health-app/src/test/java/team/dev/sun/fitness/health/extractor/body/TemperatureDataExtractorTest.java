package team.dev.sun.fitness.health.extractor.body;

import static team.dev.sun.fitness.health.api.model.TemperatureType.AMBIENT;
import static team.dev.sun.fitness.health.api.model.TemperatureType.BODY;
import static team.dev.sun.fitness.health.api.model.TemperatureType.SKIN;
import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import co.tryterra.terraclient.models.v2.body.TemperatureData;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.api.model.TemperatureType;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import team.dev.sun.fitness.health.model.body.Temperature;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TemperatureDataExtractorTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  private TemperatureDataExtractor temperatureDataExtractor;

  private BodyFitnessHealthData bodyFitnessHealthData;

  @BeforeEach
  void setUp() {

    temperatureDataExtractor = new TemperatureDataExtractor();
    bodyFitnessHealthData = new BodyFitnessHealthData();
  }

  @Test
  void extractDataWoAnyTemperature() {

    temperatureDataExtractor.extractData(new TemperatureData(), bodyFitnessHealthData);
    assertNotNull(bodyFitnessHealthData.getTemperatures());
    assertEquals(0, bodyFitnessHealthData.getTemperatures().size());
  }

  @Test
  void extractDataWoAmbientTemperature() {

    Temperature expectedBodyTemperature = getTemperature(BODY, 38d, 20);
    Temperature expectedSkinTemperature = getTemperature(SKIN, 36d, 22);
    TemperatureData temperatureData = getTemperatureData(readResource("temperature_data_wo_ambient.json"));
    temperatureDataExtractor.extractData(temperatureData, bodyFitnessHealthData);
    assertNotNull(bodyFitnessHealthData.getTemperatures());
    assertEquals(2, bodyFitnessHealthData.getTemperatures().size());
    assertTemperature(expectedBodyTemperature, bodyFitnessHealthData.getTemperatures());
    assertTemperature(expectedSkinTemperature, bodyFitnessHealthData.getTemperatures());
  }

  @Test
  void extractDataWoBodyTemperature() {

    Temperature expectedAmbientTemperature = getTemperature(AMBIENT, 37d, 21);
    Temperature expectedSkinTemperature = getTemperature(SKIN, 36d, 22);
    TemperatureData temperatureData = getTemperatureData(readResource("temperature_data_wo_body.json"));
    temperatureDataExtractor.extractData(temperatureData, bodyFitnessHealthData);
    assertNotNull(bodyFitnessHealthData.getTemperatures());
    assertEquals(2, bodyFitnessHealthData.getTemperatures().size());
    assertTemperature(expectedAmbientTemperature, bodyFitnessHealthData.getTemperatures());
    assertTemperature(expectedSkinTemperature, bodyFitnessHealthData.getTemperatures());
  }

  @Test
  void extractDataWoSkinTemperature() {

    Temperature expectedAmbientTemperature = getTemperature(AMBIENT, 37d, 21);
    Temperature expectedBodyTemperature = getTemperature(BODY, 38d, 20);
    TemperatureData temperatureData = getTemperatureData(readResource("temperature_data_wo_skin.json"));
    temperatureDataExtractor.extractData(temperatureData, bodyFitnessHealthData);
    assertNotNull(bodyFitnessHealthData.getTemperatures());
    assertEquals(2, bodyFitnessHealthData.getTemperatures().size());
    assertTemperature(expectedAmbientTemperature, bodyFitnessHealthData.getTemperatures());
    assertTemperature(expectedBodyTemperature, bodyFitnessHealthData.getTemperatures());
  }

  @Test
  void extractDataWithAllTemperature() {

    Temperature expectedAmbientTemperature = getTemperature(AMBIENT, 37d, 21);
    Temperature expectedBodyTemperature = getTemperature(BODY, 38d, 20);
    Temperature expectedSkinTemperature = getTemperature(SKIN, 36d, 22);
    temperatureDataExtractor.extractData(getTemperatureData(readResource("temperature_data.json")), bodyFitnessHealthData);
    assertNotNull(bodyFitnessHealthData.getTemperatures());
    assertEquals(3, bodyFitnessHealthData.getTemperatures().size());
    assertTemperature(expectedAmbientTemperature, bodyFitnessHealthData.getTemperatures());
    assertTemperature(expectedBodyTemperature, bodyFitnessHealthData.getTemperatures());
    assertTemperature(expectedSkinTemperature, bodyFitnessHealthData.getTemperatures());
  }

  private void assertTemperature(Temperature expected, List<Temperature> temperatures) {

    Temperature actual = temperatures.stream()
                                     .filter(temperature -> temperature.getType() == expected.getType())
                                     .findAny()
                                     .orElseThrow();
    assertNotNull(actual);
    assertEquals(expected.getTemperatureCelsius(), actual.getTemperatureCelsius());
    assertEquals(expected.getTimestamp(), actual.getTimestamp());
    assertNotNull(actual.getBodyFitnessHealthData());
  }

  private Temperature getTemperature(TemperatureType type, Double value, int hour) {

    Temperature temperature = new Temperature();
    temperature.setType(type);
    temperature.setTimestamp(ZonedDateTime.of(2023, 1, 17, hour, 21, 23, 0, UTC));
    temperature.setTemperatureCelsius(value);
    return temperature;
  }

  @SneakyThrows
  private String readResource(String name) {

    return IOUtils.resourceToString("unit-test-data/" + name, StandardCharsets.UTF_8, this.getClass().getClassLoader());
  }

  @SneakyThrows
  private TemperatureData getTemperatureData(String data) {

    return objectMapper.readValue(data, TemperatureData.class);
  }
}