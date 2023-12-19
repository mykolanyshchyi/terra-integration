package team.dev.sun.fitness.health.mapper.daily;

import static org.junit.jupiter.api.Assertions.assertEquals;

import team.dev.sun.fitness.health.api.dto.daily.ScoreDTO;
import team.dev.sun.fitness.health.model.daily.Score;
import org.junit.jupiter.api.Test;

class ScoreMapperTest {

  private static final Long ID = 1L;

  private static final Double RECOVERY = 5d;

  private static final Double ACTIVITY = 55d;

  private static final Double SLEEP = 555d;

  @Test
  void mapScore() {

    ScoreDTO expected = getDto();
    ScoreMapper scoreMapper = new ScoreMapper();
    ScoreDTO actual = scoreMapper.map(getEntity());
    assertEquals(expected, actual);
  }

  private Score getEntity() {

    Score score = new Score();
    score.setId(ID);
    score.setRecovery(RECOVERY);
    score.setActivity(ACTIVITY);
    score.setSleep(SLEEP);
    return score;
  }

  private ScoreDTO getDto() {

    return ScoreDTO.builder()
                   .id(ID)
                   .recovery(RECOVERY)
                   .activity(ACTIVITY)
                   .sleep(SLEEP)
                   .build();
  }
}