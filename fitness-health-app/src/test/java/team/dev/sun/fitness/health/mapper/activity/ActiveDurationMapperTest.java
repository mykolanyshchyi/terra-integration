package team.dev.sun.fitness.health.mapper.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import team.dev.sun.fitness.health.api.dto.activity.ActiveDurationDTO;
import team.dev.sun.fitness.health.api.dto.activity.ActivityLevelDTO;
import team.dev.sun.fitness.health.model.activity.ActiveDuration;
import team.dev.sun.fitness.health.model.activity.ActivityLevel;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ActiveDurationMapperTest {

  private static final Long ID = 1L;

  private static final Double ACTIVITY_SECONDS = 2d;

  private static final Double REST_SECONDS = 3d;

  private static final Double LOW_INTENSITY_SECONDS = 4d;

  private static final Double VIGOROUS_INTENSITY_SECONDS = 5d;

  private static final Integer NUM_CONT_INUOUSIN_ACTIVE_PERIODS = 6;

  private static final Double INACTIVITY_SECONDS = 7d;

  private static final Double MODERATE_INTENSITY_SECONDS = 8d;

  @Mock
  private ActivityLevelMapper activityLevelMapper;

  @InjectMocks
  private ActiveDurationMapper activeDurationMapper;

  @Mock
  private ActivityLevel activityLevel;

  @Mock
  private ActivityLevelDTO activityLevelDTO;

  @Test
  void mapActiveDuration() {

    ActiveDurationDTO expected = getDto();
    when(activityLevelMapper.map(anyList())).thenReturn(List.of(activityLevelDTO));
    ActiveDurationDTO actual = activeDurationMapper.map(getEntity());
    assertEquals(expected, actual);
  }

  private ActiveDuration getEntity() {

    ActiveDuration activeDuration = new ActiveDuration();
    activeDuration.setId(ID);
    activeDuration.setActivitySeconds(ACTIVITY_SECONDS);
    activeDuration.setRestSeconds(REST_SECONDS);
    activeDuration.setLowIntensitySeconds(LOW_INTENSITY_SECONDS);
    activeDuration.setVigorousIntensitySeconds(VIGOROUS_INTENSITY_SECONDS);
    activeDuration.setNumContinuousInactivePeriods(NUM_CONT_INUOUSIN_ACTIVE_PERIODS);
    activeDuration.setInactivitySeconds(INACTIVITY_SECONDS);
    activeDuration.setModerateIntensitySeconds(MODERATE_INTENSITY_SECONDS);
    activeDuration.setActivityLevels(List.of(activityLevel));
    return activeDuration;
  }

  private ActiveDurationDTO getDto() {

    return ActiveDurationDTO
        .builder()
        .id(ID)
        .activitySeconds(ACTIVITY_SECONDS)
        .restSeconds(REST_SECONDS)
        .lowIntensitySeconds(LOW_INTENSITY_SECONDS)
        .vigorousIntensitySeconds(VIGOROUS_INTENSITY_SECONDS)
        .numContinuousInactivePeriods(NUM_CONT_INUOUSIN_ACTIVE_PERIODS)
        .inactivitySeconds(INACTIVITY_SECONDS)
        .moderateIntensitySeconds(MODERATE_INTENSITY_SECONDS)
        .activityLevels(List.of(activityLevelDTO))
        .build();
  }
}