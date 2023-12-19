package team.dev.sun.fitness.health.handler;

import static team.dev.sun.fitness.health.api.model.DataType.BODY;

import co.tryterra.terraclient.models.v2.body.BloodPressureData;
import co.tryterra.terraclient.models.v2.body.Body;
import co.tryterra.terraclient.models.v2.body.GlucoseData;
import co.tryterra.terraclient.models.v2.body.HydrationData;
import co.tryterra.terraclient.models.v2.body.MeasurementsData;
import co.tryterra.terraclient.models.v2.body.TemperatureData;
import co.tryterra.terraclient.models.v2.common.Metadata;
import co.tryterra.terraclient.models.v2.common.OxygenData;
import team.dev.sun.fitness.health.dlq.DeadLetterQueue;
import team.dev.sun.fitness.health.extractor.body.BloodPressureDataExtractor;
import team.dev.sun.fitness.health.extractor.body.GlucoseDataExtractor;
import team.dev.sun.fitness.health.extractor.body.HydrationDataExtractor;
import team.dev.sun.fitness.health.extractor.body.MeasurementDataExtractor;
import team.dev.sun.fitness.health.extractor.body.OxygenDataExtractor;
import team.dev.sun.fitness.health.extractor.body.TemperatureDataExtractor;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import team.dev.sun.fitness.health.parser.WebhookPayloadParser;
import team.dev.sun.fitness.health.persistence.BodyFitnessHealthDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BodyHandler extends AbstractHandler<Body, BodyFitnessHealthData, BodyFitnessHealthDataRepository> {

  private final BloodPressureDataExtractor bloodPressureDataExtractor;

  private final GlucoseDataExtractor glucoseDataExtractor;

  private final HydrationDataExtractor hydrationDataExtractor;

  private final MeasurementDataExtractor measurementDataExtractor;

  private final OxygenDataExtractor oxygenDataExtractor;

  private final TemperatureDataExtractor temperatureDataExtractor;

  @Autowired
  public BodyHandler(final DeadLetterQueue deadLetterQueue, final BloodPressureDataExtractor bloodPressureDataExtractor,
                     final GlucoseDataExtractor glucoseDataExtractor, final HydrationDataExtractor hydrationDataExtractor,
                     final MeasurementDataExtractor measurementDataExtractor, final OxygenDataExtractor oxygenDataExtractor,
                     final TemperatureDataExtractor temperatureDataExtractor, final BodyFitnessHealthDataRepository repository,
                     final WebhookPayloadParser payloadParser) {

    super(deadLetterQueue, payloadParser, repository, Body.class);
    this.bloodPressureDataExtractor = bloodPressureDataExtractor;
    this.glucoseDataExtractor = glucoseDataExtractor;
    this.hydrationDataExtractor = hydrationDataExtractor;
    this.measurementDataExtractor = measurementDataExtractor;
    this.oxygenDataExtractor = oxygenDataExtractor;
    this.temperatureDataExtractor = temperatureDataExtractor;
  }

  @Override
  protected BodyFitnessHealthData extractData(Body body) {

    BodyFitnessHealthData bodyFitnessHealthData = new BodyFitnessHealthData();
    bodyFitnessHealthData.setDataType(BODY);
    extractBloodPressures(bodyFitnessHealthData, body.getBloodPressureData());
    extractGlucose(bodyFitnessHealthData, body.getGlucoseData());
    extractHydration(bodyFitnessHealthData, body.getHydrationData());
    extractMeasurements(bodyFitnessHealthData, body.getMeasurementsData());
    extractOxygen(bodyFitnessHealthData, body.getOxygenData());
    extractTemperatures(bodyFitnessHealthData, body.getTemperatureData());
    return bodyFitnessHealthData;
  }

  @Override
  protected void setMetadata(final Body source, final BodyFitnessHealthData entity) {

    Metadata metadata = source.getMetadata();
    if (metadata != null) {
      createAndsetMetadata(entity, metadata.getStartTime(), metadata.getEndTime());
    }
  }

  private void extractBloodPressures(BodyFitnessHealthData entity, final BloodPressureData data) {

    if (data != null) {
      bloodPressureDataExtractor.extract(data, entity);
    }
  }

  private void extractGlucose(BodyFitnessHealthData entity, final GlucoseData data) {

    if (data != null) {
      glucoseDataExtractor.extract(data, entity);
    }
  }

  private void extractHydration(BodyFitnessHealthData entity, final HydrationData data) {

    if (data != null) {
      hydrationDataExtractor.extract(data, entity);
    }
  }

  private void extractMeasurements(BodyFitnessHealthData entity, final MeasurementsData data) {

    if (data != null) {
      measurementDataExtractor.extract(data, entity);
    }
  }

  private void extractOxygen(BodyFitnessHealthData entity, final OxygenData data) {

    if (data != null) {
      oxygenDataExtractor.extract(data, entity);
    }
  }

  private void extractTemperatures(BodyFitnessHealthData entity, final TemperatureData data) {

    if (data != null) {
      temperatureDataExtractor.extract(data, entity);
    }
  }
}
