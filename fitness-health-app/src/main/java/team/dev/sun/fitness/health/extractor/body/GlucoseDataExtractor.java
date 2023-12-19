package team.dev.sun.fitness.health.extractor.body;

import static team.dev.sun.fitness.health.api.model.BloodGlucoseType.BLOOD;
import static team.dev.sun.fitness.health.api.model.BloodGlucoseType.DETAILED_BLOOD;
import static team.dev.sun.fitness.health.util.DateUtil.parseDate;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import co.tryterra.terraclient.models.v2.body.GlucoseData;
import co.tryterra.terraclient.models.v2.samples.GlucoseDataSample;
import team.dev.sun.fitness.health.api.model.BloodGlucoseType;
import team.dev.sun.fitness.health.extractor.AbstractDataExtractor;
import team.dev.sun.fitness.health.model.body.BloodGlucoseSample;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import team.dev.sun.fitness.health.model.body.Glucose;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GlucoseDataExtractor extends AbstractDataExtractor<GlucoseData, BodyFitnessHealthData> {

  @Override
  protected void extractData(final GlucoseData source, final BodyFitnessHealthData entity) {

    Glucose glucose = new Glucose();
    glucose.setDayAvgBloodGlucoseMgPerDl(source.getDayAvgBloodGlucoseMgPerDl());
    List<BloodGlucoseSample> samples = getBloodGlucoseSamples(
        source.getBloodGlucoseSamples(),
        source.getDetailedBloodGlucoseSamples(),
        glucose
    );
    glucose.setSamples(samples);
    entity.setGlucose(glucose);
  }

  private List<BloodGlucoseSample> getBloodGlucoseSamples(final List<GlucoseDataSample> bloodGlucoseSamples,
                                                          final List<GlucoseDataSample> detailedBloodGlucoseSamples,
                                                          final Glucose glucose) {

    List<BloodGlucoseSample> result = new ArrayList<>();

    if (isNotEmpty(bloodGlucoseSamples)) {
      List<BloodGlucoseSample> samples =
          bloodGlucoseSamples.stream()
                             .map(sample -> createBloodGlucoseSample(sample, BLOOD, glucose))
                             .toList();
      result.addAll(samples);
    }

    if (isNotEmpty(detailedBloodGlucoseSamples)) {
      List<BloodGlucoseSample> samples =
          detailedBloodGlucoseSamples.stream()
                                     .map(sample -> createBloodGlucoseSample(sample, DETAILED_BLOOD, glucose))
                                     .toList();
      result.addAll(samples);
    }

    return result;
  }

  private BloodGlucoseSample createBloodGlucoseSample(GlucoseDataSample sample, BloodGlucoseType type, Glucose glucose) {

    BloodGlucoseSample bloodGlucoseSample = new BloodGlucoseSample();
    bloodGlucoseSample.setType(type);
    bloodGlucoseSample.setTimestamp(parseDate(sample.getTimestamp()));
    bloodGlucoseSample.setBloodGlucoseMgPerDl(sample.getBloodGlucoseMgPerDl());
    bloodGlucoseSample.setGlucoseLevelFlag(sample.getGlucoseLevelFlag());
    bloodGlucoseSample.setTrendArrow(sample.getTrendArrow());
    bloodGlucoseSample.setGlucose(glucose);
    return bloodGlucoseSample;
  }
}
