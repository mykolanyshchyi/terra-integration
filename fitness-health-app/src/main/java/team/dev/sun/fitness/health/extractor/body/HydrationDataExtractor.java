package team.dev.sun.fitness.health.extractor.body;

import static team.dev.sun.fitness.health.util.DateUtil.parseDate;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import co.tryterra.terraclient.models.v2.body.HydrationData;
import co.tryterra.terraclient.models.v2.samples.HydrationLevelSample;
import co.tryterra.terraclient.models.v2.samples.HydrationMeasurementSample;
import team.dev.sun.fitness.health.extractor.AbstractDataExtractor;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import team.dev.sun.fitness.health.model.body.Hydration;
import team.dev.sun.fitness.health.model.body.HydrationLevel;
import team.dev.sun.fitness.health.model.body.HydrationMeasurement;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HydrationDataExtractor extends AbstractDataExtractor<HydrationData, BodyFitnessHealthData> {

  @Override
  protected void extractData(final HydrationData source, final BodyFitnessHealthData entity) {

    Hydration hydration = new Hydration();
    hydration.setDayTotalWaterConsumptionMl(source.getDayTotalWaterConsumptionMl());

    if (isNotEmpty(source.getHydrationLevelSamples())) {
      List<HydrationMeasurement> hydrationMeasurements = getHydrationMeasurements(source.getHydrationLevelSamples(), hydration);
      hydration.setHydrationMeasurements(hydrationMeasurements);
    }

    if (isNotEmpty(source.getHydrationAmountSamples())) {
      List<HydrationLevel> hydrationLevels = getHydrationLevels(source.getHydrationAmountSamples(), hydration);
      hydration.setHydrationLevels(hydrationLevels);
    }

    entity.setHydration(hydration);
  }

  private List<HydrationLevel> getHydrationLevels(final List<HydrationLevelSample> samples, final Hydration hydration) {

    return samples.stream()
                  .map(sample -> {
                    HydrationLevel hydrationLevel = new HydrationLevel();
                    hydrationLevel.setLevel(sample.getHydrationLevel());
                    hydrationLevel.setTimestamp(parseDate(sample.getTimestamp()));
                    hydrationLevel.setHydration(hydration);
                    return hydrationLevel;
                  })
                  .toList();
  }

  private List<HydrationMeasurement> getHydrationMeasurements(final List<HydrationMeasurementSample> samples,
                                                              final Hydration hydration) {

    return samples.stream()
                  .map(sample -> {
                    HydrationMeasurement hydrationMeasurement = new HydrationMeasurement();
                    hydrationMeasurement.setHydrationKg(sample.getHydrationKg());
                    hydrationMeasurement.setTimestamp(parseDate(sample.getTimestamp()));
                    hydrationMeasurement.setHydration(hydration);
                    return hydrationMeasurement;
                  })
                  .toList();
  }
}
