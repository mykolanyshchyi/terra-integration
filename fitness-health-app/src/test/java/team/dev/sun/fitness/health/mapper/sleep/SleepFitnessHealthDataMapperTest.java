package team.dev.sun.fitness.health.mapper.sleep;

import static team.dev.sun.fitness.health.api.model.DataType.SLEEP;
import static team.dev.sun.fitness.health.api.model.Provider.FREESTYLELIBRE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import team.dev.sun.fitness.health.api.dto.sleep.AsleepDurationDTO;
import team.dev.sun.fitness.health.api.dto.sleep.AwakeDurationDTO;
import team.dev.sun.fitness.health.api.dto.sleep.SleepFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.model.DataType;
import team.dev.sun.fitness.health.api.model.Provider;
import team.dev.sun.fitness.health.model.sleep.AsleepDuration;
import team.dev.sun.fitness.health.model.sleep.AwakeDuration;
import team.dev.sun.fitness.health.model.sleep.SleepFitnessHealthData;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SleepFitnessHealthDataMapperTest {

  private static final Long ID = 1L;

  private static final Long CLIENT_ID = 5L;

  private static final Long USER_ID = 9L;

  private static final String DEVICE_ID = "some device id";

  private static final Provider PROVIDER = FREESTYLELIBRE;

  private static final DataType DATA_TYPE = SLEEP;

  private static final ZonedDateTime CREATED_AT = ZonedDateTime.now();

  @Mock
  private AwakeDurationMapper awakeDurationMapper;

  @Mock
  private AsleepDurationMapper asleepDurationMapper;

  @InjectMocks
  private SleepFitnessHealthDataMapper sleepFitnessHealthDataMapper;

  @Mock
  private AwakeDuration awakeDuration;

  @Mock
  private AsleepDuration asleepDuration;

  @Mock
  private AwakeDurationDTO awakeDurationDTO;

  @Mock
  private AsleepDurationDTO asleepDurationDTO;

  @Test
  void mapSleepFitnessHealthDataWoAnyNestedEntity() {

    SleepFitnessHealthDataDTO expected = getDto(null, null);
    SleepFitnessHealthData entity = getEntity(null, null);

    SleepFitnessHealthDataDTO actual = sleepFitnessHealthDataMapper.map(entity);

    verifyNoInteractions(awakeDurationMapper);
    verifyNoInteractions(asleepDurationMapper);

    assertEquals(expected, actual);
  }

  @Test
  void mapSleepFitnessHealthDataWoAwakeDuration() {

    SleepFitnessHealthDataDTO expected = getDto(null, asleepDurationDTO);
    SleepFitnessHealthData entity = getEntity(null, asleepDuration);

    when(asleepDurationMapper.map(any(AsleepDuration.class))).thenReturn(asleepDurationDTO);

    SleepFitnessHealthDataDTO actual = sleepFitnessHealthDataMapper.map(entity);

    verifyNoInteractions(awakeDurationMapper);
    verify(asleepDurationMapper).map(any(AsleepDuration.class));

    assertEquals(expected, actual);
  }

  @Test
  void mapSleepFitnessHealthDataWoAsleepDuration() {

    SleepFitnessHealthDataDTO expected = getDto(awakeDurationDTO, null);
    SleepFitnessHealthData entity = getEntity(awakeDuration, null);

    when(awakeDurationMapper.map(any(AwakeDuration.class))).thenReturn(awakeDurationDTO);

    SleepFitnessHealthDataDTO actual = sleepFitnessHealthDataMapper.map(entity);

    verify(awakeDurationMapper).map(any(AwakeDuration.class));
    verifyNoInteractions(asleepDurationMapper);

    assertEquals(expected, actual);
  }

  @Test
  void mapSleepFitnessHealthData() {

    SleepFitnessHealthDataDTO expected = getDto(awakeDurationDTO, asleepDurationDTO);
    SleepFitnessHealthData entity = getEntity(awakeDuration, asleepDuration);

    when(awakeDurationMapper.map(any(AwakeDuration.class))).thenReturn(awakeDurationDTO);
    when(asleepDurationMapper.map(any(AsleepDuration.class))).thenReturn(asleepDurationDTO);

    SleepFitnessHealthDataDTO actual = sleepFitnessHealthDataMapper.map(entity);

    verify(awakeDurationMapper).map(any(AwakeDuration.class));
    verify(asleepDurationMapper).map(any(AsleepDuration.class));

    assertEquals(expected, actual);
  }

  private SleepFitnessHealthData getEntity(AwakeDuration awakeDuration, AsleepDuration asleepDuration) {

    SleepFitnessHealthData entity = new SleepFitnessHealthData();
    entity.setId(ID);
    entity.setClientId(CLIENT_ID);
    entity.setUserId(USER_ID);
    entity.setDeviceId(DEVICE_ID);
    entity.setProvider(PROVIDER);
    entity.setDataType(DATA_TYPE);
    entity.setCreatedAt(CREATED_AT);
    entity.setAwakeDuration(awakeDuration);
    entity.setAsleepDuration(asleepDuration);
    return entity;
  }

  private SleepFitnessHealthDataDTO getDto(AwakeDurationDTO awakeDuration, AsleepDurationDTO asleepDuration) {

    SleepFitnessHealthDataDTO dto = new SleepFitnessHealthDataDTO();
    dto.setId(ID);
    dto.setClientId(CLIENT_ID);
    dto.setUserId(USER_ID);
    dto.setDeviceId(DEVICE_ID);
    dto.setProvider(PROVIDER);
    dto.setDataType(DATA_TYPE);
    dto.setCreatedAt(CREATED_AT);
    dto.setAwakeDuration(awakeDuration);
    dto.setAsleepDuration(asleepDuration);
    return dto;
  }
}