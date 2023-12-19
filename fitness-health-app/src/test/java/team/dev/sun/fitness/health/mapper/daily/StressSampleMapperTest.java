package team.dev.sun.fitness.health.mapper.daily;

import static org.junit.jupiter.api.Assertions.assertEquals;

import team.dev.sun.fitness.health.api.dto.daily.StressSampleDTO;
import team.dev.sun.fitness.health.model.daily.StressSample;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class StressSampleMapperTest {

  private static final Long ID = 543L;

  private static final Integer LEVEL = 5;

  private static final ZonedDateTime TIMESTAMP = ZonedDateTime.now();

  @Test
  void mapStressSample() {

    StressSampleDTO expected = getDto();
    StressSampleMapper stressSampleMapper = new StressSampleMapper();
    StressSampleDTO actual = stressSampleMapper.map(getEntity());
    assertEquals(expected, actual);
  }

  private StressSample getEntity() {

    StressSample sample = new StressSample();
    sample.setId(ID);
    sample.setLevel(LEVEL);
    sample.setTimestamp(TIMESTAMP);
    return sample;
  }

  private StressSampleDTO getDto() {

    return StressSampleDTO.builder().id(ID).level(LEVEL).timestamp(TIMESTAMP).build();
  }
}