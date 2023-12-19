package team.dev.sun.fitness.health.mapper.daily;

import static team.dev.sun.fitness.health.api.model.DataType.DAILY;
import static team.dev.sun.fitness.health.api.model.Provider.GARMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import team.dev.sun.fitness.health.api.dto.daily.DailyFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.daily.HeartRateDTO;
import team.dev.sun.fitness.health.api.dto.daily.ScoreDTO;
import team.dev.sun.fitness.health.api.dto.daily.StressDTO;
import team.dev.sun.fitness.health.api.model.DataType;
import team.dev.sun.fitness.health.api.model.Provider;
import team.dev.sun.fitness.health.model.daily.DailyFitnessHealthData;
import team.dev.sun.fitness.health.model.daily.HeartRate;
import team.dev.sun.fitness.health.model.daily.Score;
import team.dev.sun.fitness.health.model.daily.Stress;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DailyFitnessHealthDataMapperTest {

  private static final Long ID = 1L;

  private static final Long CLIENT_ID = 5L;

  private static final Long USER_ID = 9L;

  private static final String DEVICE_ID = "some device id";

  private static final Provider PROVIDER = GARMIN;

  private static final DataType DATA_TYPE = DAILY;

  private static final ZonedDateTime CREATED_AT = ZonedDateTime.now();

  @Mock
  private HeartRateMapper heartRateMapper;

  @Mock
  private ScoreMapper scoreMapper;

  @Mock
  private StressMapper stressMapper;

  @Mock
  private HeartRate heartRate;

  @Mock
  private HeartRateDTO heartRateDTO;

  @Mock
  private Score score;

  @Mock
  private ScoreDTO scoreDTO;

  @Mock
  private Stress stress;

  @Mock
  private StressDTO stressDTO;

  @InjectMocks
  private DailyFitnessHealthDataMapper dailyFitnessHealthDataMapper;

  @Test
  void mapDailyFitnessHealthDataWoAnyNestedEntity() {

    DailyFitnessHealthDataDTO expected = getDto(null, null, null);
    DailyFitnessHealthData entity = getEntity(null, null, null);

    DailyFitnessHealthDataDTO actual = dailyFitnessHealthDataMapper.map(entity);

    verifyNoInteractions(heartRateMapper);
    verifyNoInteractions(scoreMapper);
    verifyNoInteractions(stressMapper);

    assertEquals(expected, actual);
  }

  @Test
  void mapDailyFitnessHealthDataWoHeartRate() {

    DailyFitnessHealthDataDTO expected = getDto(null, scoreDTO, stressDTO);
    DailyFitnessHealthData entity = getEntity(null, score, stress);

    when(scoreMapper.map(any(Score.class))).thenReturn(scoreDTO);
    when(stressMapper.map(any(Stress.class))).thenReturn(stressDTO);

    DailyFitnessHealthDataDTO actual = dailyFitnessHealthDataMapper.map(entity);

    verifyNoInteractions(heartRateMapper);
    verify(scoreMapper).map(any(Score.class));
    verify(stressMapper).map(any(Stress.class));

    assertEquals(expected, actual);
  }

  @Test
  void mapDailyFitnessHealthDataWoScore() {

    DailyFitnessHealthDataDTO expected = getDto(heartRateDTO, null, stressDTO);
    DailyFitnessHealthData entity = getEntity(heartRate, null, stress);

    when(heartRateMapper.map(any(HeartRate.class))).thenReturn(heartRateDTO);
    when(stressMapper.map(any(Stress.class))).thenReturn(stressDTO);

    DailyFitnessHealthDataDTO actual = dailyFitnessHealthDataMapper.map(entity);

    verify(heartRateMapper).map(any(HeartRate.class));
    verifyNoInteractions(scoreMapper);
    verify(stressMapper).map(any(Stress.class));

    assertEquals(expected, actual);
  }

  @Test
  void mapDailyFitnessHealthDataWoStress() {

    DailyFitnessHealthDataDTO expected = getDto(heartRateDTO, scoreDTO, null);
    DailyFitnessHealthData entity = getEntity(heartRate, score, null);

    when(heartRateMapper.map(any(HeartRate.class))).thenReturn(heartRateDTO);
    when(scoreMapper.map(any(Score.class))).thenReturn(scoreDTO);

    DailyFitnessHealthDataDTO actual = dailyFitnessHealthDataMapper.map(entity);

    verify(heartRateMapper).map(any(HeartRate.class));
    verify(scoreMapper).map(any(Score.class));
    verifyNoInteractions(stressMapper);

    assertEquals(expected, actual);
  }

  @Test
  void mapDailyFitnessHealthData() {

    DailyFitnessHealthDataDTO expected = getDto(heartRateDTO, scoreDTO, stressDTO);
    DailyFitnessHealthData entity = getEntity(heartRate, score, stress);

    when(heartRateMapper.map(any(HeartRate.class))).thenReturn(heartRateDTO);
    when(scoreMapper.map(any(Score.class))).thenReturn(scoreDTO);
    when(stressMapper.map(any(Stress.class))).thenReturn(stressDTO);

    DailyFitnessHealthDataDTO actual = dailyFitnessHealthDataMapper.map(entity);

    verify(heartRateMapper).map(any(HeartRate.class));
    verify(scoreMapper).map(any(Score.class));
    verify(stressMapper).map(any(Stress.class));

    assertEquals(expected, actual);
  }

  private DailyFitnessHealthData getEntity(HeartRate heartRate, Score score, Stress stress) {

    DailyFitnessHealthData entity = new DailyFitnessHealthData();
    entity.setId(ID);
    entity.setClientId(CLIENT_ID);
    entity.setUserId(USER_ID);
    entity.setDeviceId(DEVICE_ID);
    entity.setProvider(PROVIDER);
    entity.setDataType(DATA_TYPE);
    entity.setCreatedAt(CREATED_AT);
    entity.setHeartRate(heartRate);
    entity.setScore(score);
    entity.setStress(stress);
    return entity;
  }

  private DailyFitnessHealthDataDTO getDto(HeartRateDTO heartRate, ScoreDTO score, StressDTO stress) {

    DailyFitnessHealthDataDTO dto = new DailyFitnessHealthDataDTO();
    dto.setId(ID);
    dto.setClientId(CLIENT_ID);
    dto.setUserId(USER_ID);
    dto.setDeviceId(DEVICE_ID);
    dto.setProvider(PROVIDER);
    dto.setDataType(DATA_TYPE);
    dto.setCreatedAt(CREATED_AT);
    dto.setHeartRate(heartRate);
    dto.setScore(score);
    dto.setStress(stress);
    return dto;
  }
}