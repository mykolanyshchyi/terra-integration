package team.dev.sun.fitness.health.extractor.body;

import static team.dev.sun.fitness.health.util.DateUtil.parseDate;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import co.tryterra.terraclient.models.v2.common.OxygenData;
import co.tryterra.terraclient.models.v2.samples.OxygenSaturationSample;
import co.tryterra.terraclient.models.v2.samples.Vo2MaxSample;
import team.dev.sun.fitness.health.extractor.AbstractDataExtractor;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import team.dev.sun.fitness.health.model.body.Oxygen;
import team.dev.sun.fitness.health.model.body.OxygenSaturation;
import team.dev.sun.fitness.health.model.body.OxygenVo2;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OxygenDataExtractor extends AbstractDataExtractor<OxygenData, BodyFitnessHealthData> {

  @Override
  protected void extractData(final OxygenData source, final BodyFitnessHealthData entity) {

    Oxygen oxygen = new Oxygen();
    oxygen.setAvgSaturationPercentage(source.getAvgSaturationPercentage());
    oxygen.setVo2MaxMlPerMinPerKg(source.getVo2MaxMlPerMinPerKg());

    if (isNotEmpty(source.getSaturationSamples())) {
      List<OxygenSaturation> oxygenSaturations = getOxygenSaturations(source.getSaturationSamples(), oxygen);
      oxygen.setOxygenSaturations(oxygenSaturations);
    }

    if (isNotEmpty(source.getVo2Samples())) {
      List<OxygenVo2> oxygenVo2s = getOxygenVo2s(source.getVo2Samples(), oxygen);
      oxygen.setOxygenVo2s(oxygenVo2s);
    }

    entity.setOxygen(oxygen);
  }

  private List<OxygenSaturation> getOxygenSaturations(final List<OxygenSaturationSample> samples, final Oxygen oxygen) {

    return samples.stream()
                  .map(sample -> {
                    OxygenSaturation oxygenSaturation = new OxygenSaturation();
                    oxygenSaturation.setPercentage(sample.getPercentage());
                    oxygenSaturation.setTimestamp(parseDate(sample.getTimestamp()));
                    oxygenSaturation.setOxygen(oxygen);
                    return oxygenSaturation;
                  })
                  .toList();
  }

  private List<OxygenVo2> getOxygenVo2s(final List<Vo2MaxSample> samples, final Oxygen oxygen) {

    return samples.stream()
                  .map(sample -> {
                    OxygenVo2 vo2 = new OxygenVo2();
                    vo2.setOxygen(oxygen);
                    vo2.setVo2MaxMlPerMinPerKg(sample.getVo2MaxMlPerMinPerKg());
                    vo2.setTimestamp(parseDate(sample.getTimestamp()));
                    return vo2;
                  })
                  .toList();
  }
}
