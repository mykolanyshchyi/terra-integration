package team.dev.sun.fitness.health.extractor.body;

import static team.dev.sun.fitness.health.util.DateUtil.parseDate;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import co.tryterra.terraclient.models.v2.body.BloodPressureData;
import co.tryterra.terraclient.models.v2.samples.BloodPressureSample;
import team.dev.sun.fitness.health.extractor.AbstractDataExtractor;
import team.dev.sun.fitness.health.model.body.BloodPressure;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BloodPressureDataExtractor extends AbstractDataExtractor<BloodPressureData, BodyFitnessHealthData> {

  @Override
  protected void extractData(final BloodPressureData source, final BodyFitnessHealthData entity) {

    if (isNotEmpty(source.getBloodPressureSamples())) {
      List<BloodPressure> bloodPressures = source.getBloodPressureSamples()
                                                 .stream()
                                                 .map(sample -> createBloodPressure(sample, entity))
                                                 .toList();
      entity.setBloodPressures(bloodPressures);
    }
  }

  private BloodPressure createBloodPressure(final BloodPressureSample source, final BodyFitnessHealthData entity) {

    BloodPressure bloodPressure = new BloodPressure();
    bloodPressure.setDiastolicBp(source.getDiastolicBp());
    bloodPressure.setSystolicBp(source.getSystolicBp());
    bloodPressure.setTimestamp(parseDate(source.getTimestamp()));
    bloodPressure.setBodyFitnessHealthData(entity);
    return bloodPressure;
  }
}
