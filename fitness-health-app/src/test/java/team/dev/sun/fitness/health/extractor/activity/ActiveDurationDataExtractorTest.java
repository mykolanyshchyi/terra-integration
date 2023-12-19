package team.dev.sun.fitness.health.extractor.activity;

import static team.dev.sun.fitness.health.api.model.ActivityLevelType.HIGH_INTENSITY;
import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import co.tryterra.terraclient.models.v2.activity.ActiveDurationsData;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.model.activity.ActiveDuration;
import team.dev.sun.fitness.health.model.activity.ActivityFitnessHealthData;
import team.dev.sun.fitness.health.model.activity.ActivityLevel;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActiveDurationDataExtractorTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  private ActiveDurationDataExtractor activeDurationDataExtractor;

  @BeforeEach
  void setUp() {

    activeDurationDataExtractor = new ActiveDurationDataExtractor();
  }

  @Test
  void activeDurationsDataIsNull() {

    ActivityFitnessHealthData activityFitnessHealthData = new ActivityFitnessHealthData();
    activeDurationDataExtractor.extractData(null, activityFitnessHealthData);
    assertNull(activityFitnessHealthData.getActiveDuration());
  }

  @Test
  void extractData() {

    ActiveDuration expected = getExpectedActiveDuration();
    ActivityFitnessHealthData activityFitnessHealthData = new ActivityFitnessHealthData();
    ActiveDurationsData activeDurationsData = getActiveDurationsData();
    activeDurationDataExtractor.extractData(activeDurationsData, activityFitnessHealthData);
    assertNotNull(activityFitnessHealthData.getActiveDuration());
    ActiveDuration actual = activityFitnessHealthData.getActiveDuration();
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getActivitySeconds(), actual.getActivitySeconds());
    assertEquals(expected.getRestSeconds(), actual.getRestSeconds());
    assertEquals(expected.getLowIntensitySeconds(), actual.getLowIntensitySeconds());
    assertEquals(expected.getVigorousIntensitySeconds(), actual.getVigorousIntensitySeconds());
    assertEquals(expected.getNumContinuousInactivePeriods(), actual.getNumContinuousInactivePeriods());
    assertEquals(expected.getInactivitySeconds(), actual.getInactivitySeconds());
    assertEquals(expected.getModerateIntensitySeconds(), actual.getModerateIntensitySeconds());
    assertEquals(expected.getActivityLevels().size(), actual.getActivityLevels().size());
    assertEquals(expected.getActivityLevels().get(0).getLevel(), actual.getActivityLevels().get(0).getLevel());
    assertEquals(expected.getActivityLevels().get(0).getTimestamp(), actual.getActivityLevels().get(0).getTimestamp());
  }

  private ActiveDuration getExpectedActiveDuration() {

    ActiveDuration activeDuration = new ActiveDuration();
    activeDuration.setActivitySeconds(4096.5112142490225d);
    activeDuration.setRestSeconds(243.56d);
    activeDuration.setLowIntensitySeconds(5678.5678d);
    activeDuration.setVigorousIntensitySeconds(876543.34567d);
    activeDuration.setNumContinuousInactivePeriods(45);
    activeDuration.setInactivitySeconds(67.3456d);
    activeDuration.setModerateIntensitySeconds(56787.45678909876d);
    activeDuration.setActivityLevels(List.of(getExpectedActivityLevel(activeDuration)));
    return activeDuration;
  }

  private ActivityLevel getExpectedActivityLevel(final ActiveDuration activeDuration) {

    ActivityLevel activityLevel = new ActivityLevel();
    activityLevel.setActiveDuration(activeDuration);
    activityLevel.setLevel(HIGH_INTENSITY);
    activityLevel.setTimestamp(ZonedDateTime.of(2023, 1, 17, 20, 37, 17, 0, UTC));
    return activityLevel;
  }

  @SneakyThrows
  private ActiveDurationsData getActiveDurationsData() {

    InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(
        "unit-test-data/active_durations_data.json");
    return objectMapper.readValue(resourceAsStream, ActiveDurationsData.class);
  }
}