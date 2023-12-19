package team.dev.sun.fitness.health.extractor.daily;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

import co.tryterra.terraclient.models.v2.daily.ScoresData;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.model.daily.DailyFitnessHealthData;
import team.dev.sun.fitness.health.model.daily.Score;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

class ScoreDataExtractorTest {

  @Test
  void extractData() {

    Score expected = getExpected();
    ScoreDataExtractor scoreDataExtractor = new ScoreDataExtractor();
    DailyFitnessHealthData dailyFitnessHealthData = new DailyFitnessHealthData();
    scoreDataExtractor.extractData(getScoresData(), dailyFitnessHealthData);
    Score actual = dailyFitnessHealthData.getScore();
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getRecovery(), actual.getRecovery());
    assertEquals(expected.getActivity(), actual.getActivity());
    assertEquals(expected.getSleep(), actual.getSleep());
  }

  private Score getExpected() {

    Score score = new Score();
    score.setRecovery(5d);
    score.setActivity(4d);
    score.setSleep(3d);
    return score;
  }

  @SneakyThrows
  private ScoresData getScoresData() {

    String data = IOUtils.resourceToString("unit-test-data/scores_data.json", UTF_8, this.getClass().getClassLoader());
    return new ObjectMapper().readValue(data, ScoresData.class);
  }
}