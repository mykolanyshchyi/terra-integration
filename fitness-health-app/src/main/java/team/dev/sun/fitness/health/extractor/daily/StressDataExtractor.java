package team.dev.sun.fitness.health.extractor.daily;

import static team.dev.sun.fitness.health.util.DateUtil.parseDate;
import static org.apache.commons.collections4.IterableUtils.isEmpty;

import co.tryterra.terraclient.models.v2.daily.StressData;
import team.dev.sun.fitness.health.extractor.AbstractDataExtractor;
import team.dev.sun.fitness.health.model.daily.DailyFitnessHealthData;
import team.dev.sun.fitness.health.model.daily.Stress;
import team.dev.sun.fitness.health.model.daily.StressSample;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StressDataExtractor extends AbstractDataExtractor<StressData, DailyFitnessHealthData> {

  @Override
  protected void extractData(final StressData source, final DailyFitnessHealthData entity) {

    Stress stress = new Stress();
    stress.setRestStressDurationSeconds(source.getRestStressDurationSeconds());
    stress.setStressDurationSeconds(source.getStressDurationSeconds());
    stress.setActivityStressDurationSeconds(source.getActivityStressDurationSeconds());
    stress.setAvgStressLevel(source.getAvgStressLevel());
    stress.setLowStressDurationSeconds(source.getLowStressDurationSeconds());
    stress.setMediumStressDurationSeconds(source.getMediumStressDurationSeconds());
    stress.setHighStressDurationSeconds(source.getHighStressDurationSeconds());
    stress.setMaxStressLevel(source.getMaxStressLevel());
    stress.setSamples(createSamples(source.getStressSamples(), stress));
    entity.setStress(stress);
  }

  private List<StressSample> createSamples(final List<co.tryterra.terraclient.models.v2.samples.StressSample> stressSamples,
                                           final Stress stress) {

    if (isEmpty(stressSamples)) {
      return Collections.emptyList();
    }
    return stressSamples.stream()
                        .map(sample -> {
                          StressSample stressSample = new StressSample();
                          stressSample.setLevel(sample.getLevel());
                          stressSample.setTimestamp(parseDate(sample.getTimestamp()));
                          stressSample.setStress(stress);
                          return stressSample;
                        })
                        .toList();
  }
}
