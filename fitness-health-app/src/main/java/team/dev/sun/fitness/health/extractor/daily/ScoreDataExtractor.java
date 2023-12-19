package team.dev.sun.fitness.health.extractor.daily;

import co.tryterra.terraclient.models.v2.daily.ScoresData;
import team.dev.sun.fitness.health.extractor.AbstractDataExtractor;
import team.dev.sun.fitness.health.model.daily.DailyFitnessHealthData;
import team.dev.sun.fitness.health.model.daily.Score;
import org.springframework.stereotype.Component;

@Component
public class ScoreDataExtractor extends AbstractDataExtractor<ScoresData, DailyFitnessHealthData> {

  @Override
  protected void extractData(final ScoresData source, final DailyFitnessHealthData entity) {

    Score score = new Score();
    score.setRecovery(source.getRecovery());
    score.setActivity(source.getActivity());
    score.setSleep(source.getSleep());
    entity.setScore(score);
  }
}
