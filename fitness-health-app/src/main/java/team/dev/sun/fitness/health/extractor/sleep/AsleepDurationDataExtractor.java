package team.dev.sun.fitness.health.extractor.sleep;

import co.tryterra.terraclient.models.v2.sleep.SleepDurationsData.Asleep;
import team.dev.sun.fitness.health.extractor.AbstractDataExtractor;
import team.dev.sun.fitness.health.model.sleep.AsleepDuration;
import team.dev.sun.fitness.health.model.sleep.SleepFitnessHealthData;
import org.springframework.stereotype.Component;

@Component
public class AsleepDurationDataExtractor extends AbstractDataExtractor<Asleep, SleepFitnessHealthData> {

  @Override
  protected void extractData(final Asleep source, final SleepFitnessHealthData entity) {

    AsleepDuration asleepDuration = new AsleepDuration();
    asleepDuration.setLightSleepStateSeconds(source.getDurationLightSleepStateSeconds());
    asleepDuration.setAsleepStateSeconds(source.getDurationAsleepStateSeconds());
    asleepDuration.setNumRemEvents(source.getNumRemEvents());
    asleepDuration.setRemSleepStateSeconds(source.getDurationRemSleepStateSeconds());
    asleepDuration.setDeepSleepStateSeconds(source.getDurationDeepSleepStateSeconds());
    entity.setAsleepDuration(asleepDuration);
  }
}
