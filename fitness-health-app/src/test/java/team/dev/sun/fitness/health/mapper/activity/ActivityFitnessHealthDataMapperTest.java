package team.dev.sun.fitness.health.mapper.activity;

import static team.dev.sun.fitness.health.api.model.DataType.ACTIVITY;
import static team.dev.sun.fitness.health.api.model.Provider.FITBIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import team.dev.sun.fitness.health.api.dto.activity.ActiveDurationDTO;
import team.dev.sun.fitness.health.api.dto.activity.ActivityFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.activity.CaloriesDTO;
import team.dev.sun.fitness.health.api.dto.activity.DistanceDTO;
import team.dev.sun.fitness.health.api.model.DataType;
import team.dev.sun.fitness.health.api.model.Provider;
import team.dev.sun.fitness.health.model.activity.ActiveDuration;
import team.dev.sun.fitness.health.model.activity.ActivityFitnessHealthData;
import team.dev.sun.fitness.health.model.activity.Calories;
import team.dev.sun.fitness.health.model.activity.Distance;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ActivityFitnessHealthDataMapperTest {

  private static final Long ID = 1L;

  private static final Long CLIENT_ID = 5L;

  private static final Long USER_ID = 9L;

  private static final String DEVICE_ID = "some device id";

  private static final Provider PROVIDER = FITBIT;

  private static final DataType DATA_TYPE = ACTIVITY;

  private static final ZonedDateTime CREATED_AT = ZonedDateTime.now();

  @Mock
  private ActiveDurationMapper activeDurationMapper;

  @Mock
  private CaloriesMapper caloriesMapper;

  @Mock
  private DistanceMapper distanceMapper;

  @InjectMocks
  private ActivityFitnessHealthDataMapper activityFitnessHealthDataMapper;

  @Mock
  private ActiveDuration activeDuration;

  @Mock
  private Calories calories;

  @Mock
  private Distance distance;

  @Mock
  private ActiveDurationDTO activeDurationDTO;

  @Mock
  private CaloriesDTO caloriesDTO;

  @Mock
  private DistanceDTO distanceDTO;

  @Test
  void mapActivityFitnessHealthDataWoAnyNestedEntity() {

    ActivityFitnessHealthDataDTO expected = getDto(null, null, null);
    ActivityFitnessHealthData entity = getEntity(null, null, null);
    ActivityFitnessHealthDataDTO actual = activityFitnessHealthDataMapper.map(entity);

    verifyNoInteractions(activeDurationMapper);
    verifyNoInteractions(caloriesMapper);
    verifyNoInteractions(distanceMapper);

    assertEquals(expected, actual);
  }

  @Test
  void mapActivityFitnessHealthDataWoActiveDuration() {

    when(caloriesMapper.map(any(Calories.class))).thenReturn(caloriesDTO);
    when(distanceMapper.map(any(Distance.class))).thenReturn(distanceDTO);

    ActivityFitnessHealthDataDTO expected = getDto(null, caloriesDTO, distanceDTO);
    ActivityFitnessHealthData entity = getEntity(null, calories, distance);
    ActivityFitnessHealthDataDTO actual = activityFitnessHealthDataMapper.map(entity);

    verifyNoInteractions(activeDurationMapper);
    verify(caloriesMapper).map(any(Calories.class));
    verify(distanceMapper).map(any(Distance.class));

    assertEquals(expected, actual);
  }

  @Test
  void mapActivityFitnessHealthDataWoCalories() {

    when(activeDurationMapper.map(any(ActiveDuration.class))).thenReturn(activeDurationDTO);
    when(distanceMapper.map(any(Distance.class))).thenReturn(distanceDTO);

    ActivityFitnessHealthDataDTO expected = getDto(activeDurationDTO, null, distanceDTO);
    ActivityFitnessHealthData entity = getEntity(activeDuration, null, distance);
    ActivityFitnessHealthDataDTO actual = activityFitnessHealthDataMapper.map(entity);

    verify(activeDurationMapper).map(any(ActiveDuration.class));
    verifyNoInteractions(caloriesMapper);
    verify(distanceMapper).map(any(Distance.class));

    assertEquals(expected, actual);
  }

  @Test
  void mapActivityFitnessHealthDataWoDistance() {

    when(activeDurationMapper.map(any(ActiveDuration.class))).thenReturn(activeDurationDTO);
    when(caloriesMapper.map(any(Calories.class))).thenReturn(caloriesDTO);

    ActivityFitnessHealthDataDTO expected = getDto(activeDurationDTO, caloriesDTO, null);
    ActivityFitnessHealthData entity = getEntity(activeDuration, calories, null);
    ActivityFitnessHealthDataDTO actual = activityFitnessHealthDataMapper.map(entity);

    verify(activeDurationMapper).map(any(ActiveDuration.class));
    verify(caloriesMapper).map(any(Calories.class));
    verifyNoInteractions(distanceMapper);

    assertEquals(expected, actual);
  }

  @Test
  void mapActivityFitnessHealthData() {

    when(activeDurationMapper.map(any(ActiveDuration.class))).thenReturn(activeDurationDTO);
    when(caloriesMapper.map(any(Calories.class))).thenReturn(caloriesDTO);
    when(distanceMapper.map(any(Distance.class))).thenReturn(distanceDTO);

    ActivityFitnessHealthDataDTO expected = getDto(activeDurationDTO, caloriesDTO, distanceDTO);
    ActivityFitnessHealthData entity = getEntity(activeDuration, calories, distance);
    ActivityFitnessHealthDataDTO actual = activityFitnessHealthDataMapper.map(entity);

    verify(activeDurationMapper).map(any(ActiveDuration.class));
    verify(caloriesMapper).map(any(Calories.class));
    verify(distanceMapper).map(any(Distance.class));

    assertEquals(expected, actual);
  }

  private ActivityFitnessHealthData getEntity(ActiveDuration activeDuration, Calories calories, Distance distance) {

    ActivityFitnessHealthData entity = new ActivityFitnessHealthData();
    entity.setId(ID);
    entity.setClientId(CLIENT_ID);
    entity.setUserId(USER_ID);
    entity.setDeviceId(DEVICE_ID);
    entity.setProvider(PROVIDER);
    entity.setDataType(DATA_TYPE);
    entity.setCreatedAt(CREATED_AT);
    entity.setActiveDuration(activeDuration);
    entity.setCalories(calories);
    entity.setDistance(distance);
    return entity;
  }

  private ActivityFitnessHealthDataDTO getDto(ActiveDurationDTO activeDuration, CaloriesDTO calories, DistanceDTO distance) {

    ActivityFitnessHealthDataDTO dto = new ActivityFitnessHealthDataDTO();
    dto.setId(ID);
    dto.setClientId(CLIENT_ID);
    dto.setUserId(USER_ID);
    dto.setDeviceId(DEVICE_ID);
    dto.setProvider(PROVIDER);
    dto.setDataType(DATA_TYPE);
    dto.setCreatedAt(CREATED_AT);
    dto.setActiveDuration(activeDuration);
    dto.setCalories(calories);
    dto.setDistance(distance);
    return dto;
  }
}