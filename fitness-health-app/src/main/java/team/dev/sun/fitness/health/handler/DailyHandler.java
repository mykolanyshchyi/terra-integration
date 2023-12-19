package team.dev.sun.fitness.health.handler;

import static team.dev.sun.fitness.health.api.model.DataType.DAILY;

import co.tryterra.terraclient.models.v2.common.HeartRateData;
import co.tryterra.terraclient.models.v2.daily.Daily;
import co.tryterra.terraclient.models.v2.daily.Metadata;
import co.tryterra.terraclient.models.v2.daily.ScoresData;
import co.tryterra.terraclient.models.v2.daily.StressData;
import team.dev.sun.fitness.health.dlq.DeadLetterQueue;
import team.dev.sun.fitness.health.extractor.daily.HeartRateDataExtractor;
import team.dev.sun.fitness.health.extractor.daily.ScoreDataExtractor;
import team.dev.sun.fitness.health.extractor.daily.StressDataExtractor;
import team.dev.sun.fitness.health.model.daily.DailyFitnessHealthData;
import team.dev.sun.fitness.health.parser.WebhookPayloadParser;
import team.dev.sun.fitness.health.persistence.DailyFitnessHealthDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DailyHandler extends AbstractHandler<Daily, DailyFitnessHealthData, DailyFitnessHealthDataRepository> {

  private final HeartRateDataExtractor heartRateDataExtractor;

  private final ScoreDataExtractor scoreDataExtractor;

  private final StressDataExtractor stressDataExtractor;

  @Autowired
  public DailyHandler(final DeadLetterQueue deadLetterQueue, final HeartRateDataExtractor heartRateDataExtractor,
                      final ScoreDataExtractor scoreDataExtractor, final StressDataExtractor stressDataExtractor,
                      final DailyFitnessHealthDataRepository repository, final WebhookPayloadParser payloadParser) {

    super(deadLetterQueue, payloadParser, repository, Daily.class);
    this.heartRateDataExtractor = heartRateDataExtractor;
    this.scoreDataExtractor = scoreDataExtractor;
    this.stressDataExtractor = stressDataExtractor;
  }

  @Override
  protected DailyFitnessHealthData extractData(Daily daily) {

    DailyFitnessHealthData dailyFitnessHealthData = new DailyFitnessHealthData();
    dailyFitnessHealthData.setDataType(DAILY);
    extractHeartRate(dailyFitnessHealthData, daily.getHeartRateData());
    extractScore(dailyFitnessHealthData, daily.getScores());
    extractStress(dailyFitnessHealthData, daily.getStressData());
    return dailyFitnessHealthData;
  }

  @Override
  protected void setMetadata(final Daily source, final DailyFitnessHealthData entity) {

    Metadata metadata = source.getMetadata();
    if (metadata != null) {
      createAndsetMetadata(entity, metadata.getStartTime(), metadata.getEndTime());
    }
  }

  private void extractHeartRate(DailyFitnessHealthData entity, final HeartRateData data) {

    if (data != null) {
      heartRateDataExtractor.extract(data, entity);
    }
  }

  private void extractScore(DailyFitnessHealthData entity, ScoresData data) {

    if (data != null) {
      scoreDataExtractor.extract(data, entity);
    }
  }

  private void extractStress(DailyFitnessHealthData entity, final StressData data) {

    if (data != null) {
      stressDataExtractor.extract(data, entity);
    }
  }
}
