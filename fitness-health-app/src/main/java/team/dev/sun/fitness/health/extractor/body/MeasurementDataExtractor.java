package team.dev.sun.fitness.health.extractor.body;

import static team.dev.sun.fitness.health.util.DateUtil.parseDate;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import co.tryterra.terraclient.models.v2.body.MeasurementsData;
import co.tryterra.terraclient.models.v2.samples.MeasurementDataSample;
import team.dev.sun.fitness.health.extractor.AbstractDataExtractor;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import team.dev.sun.fitness.health.model.body.Measurement;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MeasurementDataExtractor extends AbstractDataExtractor<MeasurementsData, BodyFitnessHealthData> {

  @Override
  protected void extractData(final MeasurementsData source, final BodyFitnessHealthData entity) {

    if (isNotEmpty(source.getMeasurements())) {
      List<Measurement> measurements = source.getMeasurements()
                                             .stream()
                                             .map(measurementDataSample -> createMeasurement(measurementDataSample, entity))
                                             .toList();
      entity.setMeasurements(measurements);
    }
  }

  private Measurement createMeasurement(MeasurementDataSample measurementSample, BodyFitnessHealthData bodyFitnessHealthData) {

    Measurement measurement = new Measurement();
    measurement.setMeasurementTime(parseDate(measurementSample.getMeasurementTime()));
    measurement.setBmi(measurementSample.getBmi());
    measurement.setBmr(measurementSample.getBmr());
    measurement.setRmr(measurementSample.getRmr());
    measurement.setEstimatedFitnessAge(measurementSample.getEstimatedFitnessAge());
    measurement.setSkinFoldMm(measurementSample.getSkinFoldMm());
    measurement.setBodyfatPercentage(measurementSample.getBodyfatPercentage());
    measurement.setWeightKg(measurementSample.getWeightKg());
    measurement.setHeightCm(measurementSample.getHeightCm());
    measurement.setBoneMassG(measurementSample.getBoneMassG());
    measurement.setMuscleMassG(measurementSample.getMuscleMassG());
    measurement.setLeanMassG(measurementSample.getLeanMassG());
    measurement.setWaterPercentage(measurementSample.getWaterPercentage());
    measurement.setInsulinType(measurementSample.getInsulinType());
    measurement.setInsulinUnits(measurementSample.getInsulinUnits());
    measurement.setUrineColor(measurementSample.getUrineColor());
    measurement.setBodyFitnessHealthData(bodyFitnessHealthData);
    return measurement;
  }
}
