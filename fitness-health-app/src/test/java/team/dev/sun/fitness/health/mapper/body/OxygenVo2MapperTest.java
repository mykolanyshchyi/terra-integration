package team.dev.sun.fitness.health.mapper.body;

import static org.junit.jupiter.api.Assertions.assertEquals;

import team.dev.sun.fitness.health.api.dto.body.OxygenVo2DTO;
import team.dev.sun.fitness.health.model.body.OxygenVo2;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class OxygenVo2MapperTest {

  private static final Long ID = 4536L;

  private static final Double VO2_MAX_ML_PER_MIN_PER_KG = 98.4d;

  private static final ZonedDateTime TIMESTAMP = ZonedDateTime.now();

  @Test
  void mapOxygenVo2() {

    OxygenVo2DTO expected = getDto();
    OxygenVo2Mapper oxygenVo2Mapper = new OxygenVo2Mapper();
    OxygenVo2DTO actual = oxygenVo2Mapper.map(getEntity());
    assertEquals(expected, actual);
  }

  private OxygenVo2DTO getDto() {

    return OxygenVo2DTO.builder().id(ID).vo2MaxMlPerMinPerKg(VO2_MAX_ML_PER_MIN_PER_KG).timestamp(TIMESTAMP).build();
  }

  private OxygenVo2 getEntity() {

    OxygenVo2 oxygenVo2 = new OxygenVo2();
    oxygenVo2.setId(ID);
    oxygenVo2.setVo2MaxMlPerMinPerKg(VO2_MAX_ML_PER_MIN_PER_KG);
    oxygenVo2.setTimestamp(TIMESTAMP);
    return oxygenVo2;
  }
}