package team.dev.sun.fitness.health.extractor.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import co.tryterra.terraclient.models.v2.common.DistanceData;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.model.activity.ActivityFitnessHealthData;
import team.dev.sun.fitness.health.model.activity.Distance;
import java.io.InputStream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DistanceDataExtractorTest {

  private DistanceDataExtractor distanceDataExtractor;

  @BeforeEach
  void setUp() {

    distanceDataExtractor = new DistanceDataExtractor();
  }

  @Test
  void noDistanceAsSummaryIsNull() {

    ActivityFitnessHealthData activityFitnessHealthData = new ActivityFitnessHealthData();
    distanceDataExtractor.extractData(new DistanceData(), activityFitnessHealthData);
    assertNull(activityFitnessHealthData.getDistance());
  }

  @Test
  void extractData() {

    ActivityFitnessHealthData activityFitnessHealthData = new ActivityFitnessHealthData();
    DistanceData data = getDistanceData();
    distanceDataExtractor.extractData(data, activityFitnessHealthData);
    Distance distance = activityFitnessHealthData.getDistance();
    assertNotNull(distance);
    assertNull(distance.getId());
    assertEquals(15785, distance.getSteps());
  }

  @SneakyThrows
  private DistanceData getDistanceData() {

    InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("unit-test-data/distance_data.json");
    return new ObjectMapper().readValue(resourceAsStream, DistanceData.class);
  }
}