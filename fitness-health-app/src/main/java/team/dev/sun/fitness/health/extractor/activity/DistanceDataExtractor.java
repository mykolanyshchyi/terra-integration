package team.dev.sun.fitness.health.extractor.activity;

import co.tryterra.terraclient.models.v2.common.DistanceData;
import team.dev.sun.fitness.health.extractor.AbstractDataExtractor;
import team.dev.sun.fitness.health.model.activity.ActivityFitnessHealthData;
import team.dev.sun.fitness.health.model.activity.Distance;
import org.springframework.stereotype.Component;

@Component
public class DistanceDataExtractor extends AbstractDataExtractor<DistanceData, ActivityFitnessHealthData> {

  @Override
  protected void extractData(final DistanceData source, final ActivityFitnessHealthData entity) {

    if (source.getSummary() != null) {
      Distance distance = new Distance();
      distance.setSteps(source.getSummary().getSteps());
      entity.setDistance(distance);
    }
  }
}
