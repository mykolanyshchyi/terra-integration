package team.dev.sun.fitness.health.mapper.body;

import static org.junit.jupiter.api.Assertions.assertEquals;

import team.dev.sun.fitness.health.api.dto.body.HydrationLevelDTO;
import team.dev.sun.fitness.health.model.body.HydrationLevel;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class HydrationLevelMapperTest {

  private static final Long ID = 56L;

  private static final Integer HYDRATION_LEVEL = 4;

  private static final ZonedDateTime TIMESTAMP = ZonedDateTime.now();

  @Test
  void mapHydrationLevel() {

    HydrationLevelDTO expected = getDto();
    HydrationLevelMapper mapper = new HydrationLevelMapper();
    HydrationLevelDTO actual = mapper.map(getEntity());
    assertEquals(expected, actual);
  }

  private HydrationLevel getEntity() {

    HydrationLevel level = new HydrationLevel();
    level.setId(ID);
    level.setLevel(HYDRATION_LEVEL);
    level.setTimestamp(TIMESTAMP);
    return level;
  }

  private HydrationLevelDTO getDto() {

    return HydrationLevelDTO.builder()
                            .id(ID)
                            .level(HYDRATION_LEVEL)
                            .timestamp(TIMESTAMP)
                            .build();
  }
}