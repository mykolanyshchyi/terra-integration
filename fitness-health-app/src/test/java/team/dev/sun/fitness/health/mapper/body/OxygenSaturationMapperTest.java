package team.dev.sun.fitness.health.mapper.body;

import static org.junit.jupiter.api.Assertions.assertEquals;

import team.dev.sun.fitness.health.api.dto.body.OxygenSaturationDTO;
import team.dev.sun.fitness.health.model.body.OxygenSaturation;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class OxygenSaturationMapperTest {

  private static final Long ID = 4536L;

  private static final Double PERCENTAGE = 98.4d;

  private static final ZonedDateTime TIMESTAMP = ZonedDateTime.now();

  @Test
  void mapOxygenSaturation() {

    OxygenSaturationDTO expected = getDto();
    OxygenSaturationMapper mapper = new OxygenSaturationMapper();
    OxygenSaturationDTO actual = mapper.map(getEntity());
    assertEquals(expected, actual);
  }

  private OxygenSaturation getEntity() {

    OxygenSaturation saturation = new OxygenSaturation();
    saturation.setId(ID);
    saturation.setPercentage(PERCENTAGE);
    saturation.setTimestamp(TIMESTAMP);
    return saturation;
  }

  private OxygenSaturationDTO getDto() {

    return OxygenSaturationDTO.builder()
                              .id(ID)
                              .percentage(PERCENTAGE)
                              .timestamp(TIMESTAMP)
                              .build();
  }
}