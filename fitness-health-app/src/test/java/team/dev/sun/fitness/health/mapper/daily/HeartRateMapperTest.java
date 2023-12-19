package team.dev.sun.fitness.health.mapper.daily;

import static org.junit.jupiter.api.Assertions.assertEquals;

import team.dev.sun.fitness.health.api.dto.daily.HeartRateDTO;
import team.dev.sun.fitness.health.model.daily.HeartRate;
import org.junit.jupiter.api.Test;

class HeartRateMapperTest {

  private static final Long ID = 1L;

  private static final Integer MAX_HR_BPM = 2;

  private static final Integer RESTING_HR_BPM = 3;

  private static final Double AVG_HRV_RMSSD = 4.56d;

  private static final Integer MIN_HR_BPM = 5;

  private static final Integer USER_MAX_HR_BPM = 6;

  private static final Double AVG_HRV_SDNN = 7.456d;

  private static final Integer AVG_HR_BPM = 8;

  @Test
  void mapHeartRate() {

    HeartRateDTO expected = getDto();
    HeartRateMapper heartRateMapper = new HeartRateMapper();
    HeartRateDTO actual = heartRateMapper.map(getEntity());
    assertEquals(expected, actual);
  }

  private HeartRate getEntity() {

    HeartRate heartRate = new HeartRate();
    heartRate.setId(ID);
    heartRate.setMaxHrBpm(MAX_HR_BPM);
    heartRate.setRestingHrBpm(RESTING_HR_BPM);
    heartRate.setAvgHrvRmssd(AVG_HRV_RMSSD);
    heartRate.setMinHrBpm(MIN_HR_BPM);
    heartRate.setUserMaxHrBpm(USER_MAX_HR_BPM);
    heartRate.setAvgHrvSdnn(AVG_HRV_SDNN);
    heartRate.setAvgHrBpm(AVG_HR_BPM);
    return heartRate;
  }

  private HeartRateDTO getDto() {

    return HeartRateDTO.builder()
                       .id(ID)
                       .maxHrBpm(MAX_HR_BPM)
                       .restingHrBpm(RESTING_HR_BPM)
                       .avgHrvRmssd(AVG_HRV_RMSSD)
                       .minHrBpm(MIN_HR_BPM).userMaxHrBpm(USER_MAX_HR_BPM)
                       .avgHrvSdnn(AVG_HRV_SDNN)
                       .avgHrBpm(AVG_HR_BPM)
                       .build();
  }
}