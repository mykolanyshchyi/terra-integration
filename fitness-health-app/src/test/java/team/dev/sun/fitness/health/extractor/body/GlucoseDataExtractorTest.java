package team.dev.sun.fitness.health.extractor.body;

import static team.dev.sun.fitness.health.api.model.BloodGlucoseType.BLOOD;
import static team.dev.sun.fitness.health.api.model.BloodGlucoseType.DETAILED_BLOOD;
import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import co.tryterra.terraclient.models.v2.body.GlucoseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.api.model.BloodGlucoseType;
import team.dev.sun.fitness.health.model.body.BloodGlucoseSample;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import team.dev.sun.fitness.health.model.body.Glucose;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

class GlucoseDataExtractorTest {

  @Test
  void extractData() {

    Glucose expected = getExpectedGlucose();
    BodyFitnessHealthData bodyFitnessHealthData = new BodyFitnessHealthData();
    GlucoseDataExtractor glucoseDataExtractor = new GlucoseDataExtractor();
    glucoseDataExtractor.extractData(getGlucoseData(), bodyFitnessHealthData);
    Glucose actual = bodyFitnessHealthData.getGlucose();
    assertNotNull(actual);
    assertEquals(expected.getDayAvgBloodGlucoseMgPerDl(), actual.getDayAvgBloodGlucoseMgPerDl());
    assertEquals(2, actual.getSamples().size());

    BloodGlucoseSample expectedBloodSample = findSample(BLOOD, expected.getSamples());
    BloodGlucoseSample actualBloodSample = findSample(BLOOD, actual.getSamples());
    assertBloodGlucoseSample(expectedBloodSample, actualBloodSample);

    BloodGlucoseSample expectedDetailedBloodSample = findSample(DETAILED_BLOOD, expected.getSamples());
    BloodGlucoseSample actualDetailedBloodSample = findSample(DETAILED_BLOOD, actual.getSamples());
    assertBloodGlucoseSample(expectedDetailedBloodSample, actualDetailedBloodSample);
  }

  private void assertBloodGlucoseSample(BloodGlucoseSample expected, BloodGlucoseSample actual) {

    assertEquals(expected.getType(), actual.getType());
    assertEquals(expected.getTrendArrow(), actual.getTrendArrow());
    assertEquals(expected.getGlucoseLevelFlag(), actual.getGlucoseLevelFlag());
    assertEquals(expected.getBloodGlucoseMgPerDl(), actual.getBloodGlucoseMgPerDl());
    assertEquals(expected.getTimestamp(), actual.getTimestamp());
  }

  private BloodGlucoseSample findSample(BloodGlucoseType type, List<BloodGlucoseSample> samples) {

    return samples.stream()
                  .filter(sample -> sample.getType() == type)
                  .findAny()
                  .orElseThrow();
  }

  private Glucose getExpectedGlucose() {

    LocalDate localDate = LocalDate.of(2023, 1, 17);
    ZonedDateTime timestamp1 = ZonedDateTime.of(localDate, LocalTime.of(21, 21, 24), UTC);
    ZonedDateTime timestamp2 = ZonedDateTime.of(localDate, LocalTime.of(22, 15, 24), UTC);

    Glucose glucose = new Glucose();
    glucose.setDayAvgBloodGlucoseMgPerDl(159d);
    glucose.setSamples(List.of(
        createSample(BLOOD, 205d, 0, 1, timestamp1),
        createSample(DETAILED_BLOOD, 157d, 1, 2, timestamp2)
    ));
    return glucose;
  }

  private BloodGlucoseSample createSample(BloodGlucoseType type, Double glucoseMg, Integer trendArrow,
                                          Integer levelFlag, ZonedDateTime timestamp) {

    BloodGlucoseSample sample = new BloodGlucoseSample();
    sample.setType(type);
    sample.setBloodGlucoseMgPerDl(glucoseMg);
    sample.setTrendArrow(trendArrow);
    sample.setGlucoseLevelFlag(levelFlag);
    sample.setTimestamp(timestamp);
    return sample;
  }

  @SneakyThrows
  private GlucoseData getGlucoseData() {

    InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("unit-test-data/glucose_data.json");
    return new ObjectMapper().readValue(resourceAsStream, GlucoseData.class);
  }
}