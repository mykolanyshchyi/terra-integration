package team.dev.sun.fitness.health.extractor.daily;

import co.tryterra.terraclient.models.v2.common.HeartRateData;
import co.tryterra.terraclient.models.v2.common.HeartRateData.Summary;
import team.dev.sun.fitness.health.extractor.AbstractDataExtractor;
import team.dev.sun.fitness.health.model.daily.DailyFitnessHealthData;
import team.dev.sun.fitness.health.model.daily.HeartRate;
import org.springframework.stereotype.Component;

@Component
public class HeartRateDataExtractor extends AbstractDataExtractor<HeartRateData, DailyFitnessHealthData> {

  @Override
  protected void extractData(final HeartRateData source, final DailyFitnessHealthData entity) {

    if (source.getSummary() != null) {
      Summary summary = source.getSummary();
      HeartRate heartRate = new HeartRate();
      heartRate.setMaxHrBpm(summary.getMaxHrBpm());
      heartRate.setRestingHrBpm(summary.getRestingHrBpm());
      heartRate.setAvgHrvRmssd(summary.getAvgHrvRmssd());
      heartRate.setMinHrBpm(summary.getMinHrBpm());
      heartRate.setUserMaxHrBpm(summary.getUserMaxHrBpm());
      heartRate.setAvgHrvSdnn(summary.getAvgHrvSdnn());
      heartRate.setAvgHrBpm(summary.getAvgHrBpm());
      entity.setHeartRate(heartRate);
    }
  }
}
