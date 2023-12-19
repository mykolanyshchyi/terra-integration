package team.dev.sun.fitness.health.mapper.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import team.dev.sun.fitness.health.api.dto.activity.DistanceDTO;
import team.dev.sun.fitness.health.model.activity.Distance;
import org.junit.jupiter.api.Test;

class DistanceMapperTest {

  private static final Long ID = 567L;

  private static final Integer STEPS = 6786;

  @Test
  void mapDistance() {

    Distance entity = new Distance();
    entity.setId(ID);
    entity.setSteps(STEPS);
    DistanceDTO expected = new DistanceDTO(ID, STEPS);
    DistanceMapper distanceMapper = new DistanceMapper();
    DistanceDTO actual = distanceMapper.map(entity);
    assertEquals(expected, actual);
  }
}