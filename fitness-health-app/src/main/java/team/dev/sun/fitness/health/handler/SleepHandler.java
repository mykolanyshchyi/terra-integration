package team.dev.sun.fitness.health.handler;

import static team.dev.sun.fitness.health.api.model.DataType.SLEEP;

import co.tryterra.terraclient.models.v2.sleep.Metadata;
import co.tryterra.terraclient.models.v2.sleep.Sleep;
import co.tryterra.terraclient.models.v2.sleep.SleepDurationsData;
import team.dev.sun.fitness.health.dlq.DeadLetterQueue;
import team.dev.sun.fitness.health.extractor.sleep.AsleepDurationDataExtractor;
import team.dev.sun.fitness.health.extractor.sleep.AwakeDurationDataExtractor;
import team.dev.sun.fitness.health.model.sleep.SleepFitnessHealthData;
import team.dev.sun.fitness.health.parser.WebhookPayloadParser;
import team.dev.sun.fitness.health.persistence.SleepFitnessHealthDataRepository;
import org.springframework.stereotype.Component;

@Component
public class SleepHandler extends AbstractHandler<Sleep, SleepFitnessHealthData, SleepFitnessHealthDataRepository> {

  private final AwakeDurationDataExtractor awakeDurationDataExtractor;

  private final AsleepDurationDataExtractor asleepDurationDataExtractor;

  public SleepHandler(final DeadLetterQueue deadLetterQueue,
                      final AwakeDurationDataExtractor awakeDurationDataExtractor,
                      final AsleepDurationDataExtractor asleepDurationDataExtractor,
                      final SleepFitnessHealthDataRepository repository, final WebhookPayloadParser payloadParser) {

    super(deadLetterQueue, payloadParser, repository, Sleep.class);
    this.awakeDurationDataExtractor = awakeDurationDataExtractor;
    this.asleepDurationDataExtractor = asleepDurationDataExtractor;
  }

  @Override
  protected SleepFitnessHealthData extractData(Sleep sleep) {

    SleepFitnessHealthData sleepFitnessHealthData = new SleepFitnessHealthData();
    sleepFitnessHealthData.setDataType(SLEEP);
    extractAwakeDuration(sleepFitnessHealthData, sleep.getSleepDurationsData());
    extractAsleepDuration(sleepFitnessHealthData, sleep.getSleepDurationsData());
    return sleepFitnessHealthData;
  }

  @Override
  protected void setMetadata(final Sleep source, final SleepFitnessHealthData entity) {

    Metadata metadata = source.getMetadata();
    if (metadata != null) {
      createAndsetMetadata(entity, metadata.getStartTime(), metadata.getEndTime());
    }
  }

  private void extractAwakeDuration(final SleepFitnessHealthData entity, final SleepDurationsData data) {

    if (data != null && data.getAwake() != null) {
      awakeDurationDataExtractor.extract(data.getAwake(), entity);
    }
  }

  private void extractAsleepDuration(final SleepFitnessHealthData entity, final SleepDurationsData data) {

    if (data != null && data.getAsleep() != null) {
      asleepDurationDataExtractor.extract(data.getAsleep(), entity);
    }
  }
}
