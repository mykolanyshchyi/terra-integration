package team.dev.sun.fitness.health.extractor.body;

import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import co.tryterra.terraclient.models.v2.body.BloodPressureData;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.model.body.BloodPressure;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BloodPressureDataExtractorTest {

  private BloodPressureDataExtractor bloodPressureDataExtractor;

  @BeforeEach
  void setUp() {

    bloodPressureDataExtractor = new BloodPressureDataExtractor();
  }

  @Test
  void noBloodPressureAsBloodPressureSamplesIsEmpty() {

    BodyFitnessHealthData bodyFitnessHealthData = new BodyFitnessHealthData();
    bloodPressureDataExtractor.extractData(new BloodPressureData(), bodyFitnessHealthData);
    assertNull(bodyFitnessHealthData.getBloodPressures());
  }

  @Test
  void extractData() {

    BloodPressure expected = getExpectedBloodPressure();
    BodyFitnessHealthData bodyFitnessHealthData = new BodyFitnessHealthData();
    bloodPressureDataExtractor.extractData(getBloodPressureData(), bodyFitnessHealthData);
    List<BloodPressure> actualBloodPressureList = bodyFitnessHealthData.getBloodPressures();
    assertNotNull(actualBloodPressureList);
    assertEquals(1, actualBloodPressureList.size());
    BloodPressure actual = actualBloodPressureList.get(0);
    assertNotNull(actual.getBodyFitnessHealthData());
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getDiastolicBp(), actual.getDiastolicBp());
    assertEquals(expected.getSystolicBp(), actual.getSystolicBp());
    assertEquals(expected.getTimestamp(), actual.getTimestamp());
  }

  private BloodPressure getExpectedBloodPressure() {

    BloodPressure bloodPressure = new BloodPressure();
    bloodPressure.setDiastolicBp(97d);
    bloodPressure.setSystolicBp(124d);
    bloodPressure.setTimestamp(ZonedDateTime.of(2023, 1, 17, 21, 33, 24, 0, UTC));
    return bloodPressure;
  }

  @SneakyThrows
  private BloodPressureData getBloodPressureData() {

    InputStream resourceAsStream = this.getClass().getClassLoader()
                                       .getResourceAsStream("unit-test-data/blood_pressure_data.json");
    return new ObjectMapper().readValue(resourceAsStream, BloodPressureData.class);
  }
}