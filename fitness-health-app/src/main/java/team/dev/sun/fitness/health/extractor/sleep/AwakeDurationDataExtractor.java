package team.dev.sun.fitness.health.extractor.sleep;

import co.tryterra.terraclient.models.v2.sleep.SleepDurationsData.Awake;
import team.dev.sun.fitness.health.extractor.AbstractDataExtractor;
import team.dev.sun.fitness.health.model.sleep.AwakeDuration;
import team.dev.sun.fitness.health.model.sleep.SleepFitnessHealthData;
import org.springframework.stereotype.Component;

@Component
public class AwakeDurationDataExtractor extends AbstractDataExtractor<Awake, SleepFitnessHealthData> {

  @Override
  protected void extractData(final Awake source, final SleepFitnessHealthData entity) {

    AwakeDuration awakeDuration = new AwakeDuration();
    awakeDuration.setShortInterruptionSeconds(source.getDurationShortInterruptionSeconds());
    awakeDuration.setAwakeStateSeconds(source.getDurationAwakeStateSeconds());
    awakeDuration.setLongInterruptionSeconds(source.getDurationLongInterruptionSeconds());
    awakeDuration.setNumWakeupEvents(source.getNumWakeupEvents());
    awakeDuration.setWakeUpLatencySeconds(source.getWakeUpLatencySeconds());
    awakeDuration.setNumOutOfBedEvents(source.getNumOutOfBedEvents());
    awakeDuration.setSleepLatencySeconds(source.getSleepLatencySeconds());
    entity.setAwakeDuration(awakeDuration);
  }
}
