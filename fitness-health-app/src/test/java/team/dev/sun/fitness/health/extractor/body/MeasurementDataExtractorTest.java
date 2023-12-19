package team.dev.sun.fitness.health.extractor.body;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import co.tryterra.terraclient.models.v2.body.MeasurementsData;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import team.dev.sun.fitness.health.model.body.Measurement;
import java.time.ZonedDateTime;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MeasurementDataExtractorTest {

  private MeasurementDataExtractor measurementDataExtractor;

  private BodyFitnessHealthData bodyFitnessHealthData;

  @BeforeEach
  void setUp() {

    measurementDataExtractor = new MeasurementDataExtractor();
    bodyFitnessHealthData = new BodyFitnessHealthData();
  }

  @Test
  void extractDataWhereNoMeasurement() {

    measurementDataExtractor.extract(new MeasurementsData(), bodyFitnessHealthData);
    assertNull(bodyFitnessHealthData.getMeasurements());
  }

  @Test
  void extractData() {

    Measurement expected = getExpectedMeasurement();
    measurementDataExtractor.extract(getMeasurementsData(), bodyFitnessHealthData);
    assertNotNull(bodyFitnessHealthData.getMeasurements());
    assertEquals(1, bodyFitnessHealthData.getMeasurements().size());
    Measurement actual = bodyFitnessHealthData.getMeasurements().get(0);

    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getMeasurementTime(), actual.getMeasurementTime());
    assertEquals(expected.getBmi(), actual.getBmi());
    assertEquals(expected.getBmr(), actual.getBmr());
    assertEquals(expected.getRmr(), actual.getRmr());
    assertEquals(expected.getEstimatedFitnessAge(), actual.getEstimatedFitnessAge());
    assertEquals(expected.getSkinFoldMm(), actual.getSkinFoldMm());
    assertEquals(expected.getBodyfatPercentage(), actual.getBodyfatPercentage());
    assertEquals(expected.getWeightKg(), actual.getWeightKg());
    assertEquals(expected.getHeightCm(), actual.getHeightCm());
    assertEquals(expected.getBoneMassG(), actual.getBoneMassG());
    assertEquals(expected.getMuscleMassG(), actual.getMuscleMassG());
    assertEquals(expected.getLeanMassG(), actual.getLeanMassG());
    assertEquals(expected.getWaterPercentage(), actual.getWaterPercentage());
    assertEquals(expected.getInsulinUnits(), actual.getInsulinUnits());
    assertEquals(expected.getInsulinType(), actual.getInsulinType());
    assertEquals(expected.getUrineColor(), actual.getUrineColor());
  }

  private Measurement getExpectedMeasurement() {

    Measurement measurement = new Measurement();
    measurement.setRmr(1d);
    measurement.setInsulinType("someInsulinType");
    measurement.setSkinFoldMm(2d);
    measurement.setWaterPercentage(68d);
    measurement.setBmr(3d);
    measurement.setWeightKg(67d);
    measurement.setMeasurementTime(ZonedDateTime.of(2023, 1, 17, 22, 45, 13, 0, UTC));
    measurement.setMuscleMassG(45d);
    measurement.setHeightCm(180d);
    measurement.setBmi(36d);
    measurement.setBoneMassG(29d);
    measurement.setUrineColor("yellow");
    measurement.setEstimatedFitnessAge(35);
    measurement.setInsulinUnits(4d);
    measurement.setBodyfatPercentage(24d);
    measurement.setLeanMassG(5d);
    return measurement;
  }

  @SneakyThrows
  private MeasurementsData getMeasurementsData() {

    String data = IOUtils.resourceToString("unit-test-data/measurements_data.json", UTF_8, this.getClass().getClassLoader());
    return new ObjectMapper().readValue(data, MeasurementsData.class);
  }
}