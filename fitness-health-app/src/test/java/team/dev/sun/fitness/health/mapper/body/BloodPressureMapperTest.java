package team.dev.sun.fitness.health.mapper.body;

import static org.junit.jupiter.api.Assertions.assertEquals;

import team.dev.sun.fitness.health.api.dto.body.BloodPressureDTO;
import team.dev.sun.fitness.health.model.body.BloodPressure;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class BloodPressureMapperTest {

  private static final Long ID = 9876L;

  private static final Double DIASTOLIC_BP = 56.23d;

  private static final Double SYSTOLIC_BP = 12345.45d;

  private static final ZonedDateTime TIMESTAMP = ZonedDateTime.now();

  @Test
  void mapBloodPressure() {

    BloodPressureDTO expected = getDto();
    BloodPressureMapper bloodPressureMapper = new BloodPressureMapper();
    BloodPressureDTO actual = bloodPressureMapper.map(getEntity());
    assertEquals(expected, actual);
  }

  private BloodPressure getEntity() {

    BloodPressure bloodPressure = new BloodPressure();
    bloodPressure.setId(ID);
    bloodPressure.setDiastolicBp(DIASTOLIC_BP);
    bloodPressure.setSystolicBp(SYSTOLIC_BP);
    bloodPressure.setTimestamp(TIMESTAMP);
    return bloodPressure;
  }

  private BloodPressureDTO getDto() {

    return BloodPressureDTO.builder()
                           .id(ID)
                           .diastolicBp(DIASTOLIC_BP)
                           .systolicBp(SYSTOLIC_BP)
                           .timestamp(TIMESTAMP)
                           .build();
  }
}