package team.dev.sun.fitness.health.handler;

import static team.dev.sun.fitness.health.api.model.DataType.ACTIVITY;

import co.tryterra.terraclient.models.v2.activity.ActiveDurationsData;
import co.tryterra.terraclient.models.v2.activity.Activity;
import co.tryterra.terraclient.models.v2.activity.Metadata;
import co.tryterra.terraclient.models.v2.common.CaloriesData;
import co.tryterra.terraclient.models.v2.common.DistanceData;
import team.dev.sun.fitness.health.dlq.DeadLetterQueue;
import team.dev.sun.fitness.health.extractor.activity.ActiveDurationDataExtractor;
import team.dev.sun.fitness.health.extractor.activity.CaloriesDataExtractor;
import team.dev.sun.fitness.health.extractor.activity.DistanceDataExtractor;
import team.dev.sun.fitness.health.model.activity.ActivityFitnessHealthData;
import team.dev.sun.fitness.health.parser.WebhookPayloadParser;
import team.dev.sun.fitness.health.persistence.ActivityFitnessHealthDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivityHandler extends AbstractHandler<Activity, ActivityFitnessHealthData, ActivityFitnessHealthDataRepository> {

  private final ActiveDurationDataExtractor activeDurationDataExtractor;

  private final DistanceDataExtractor distanceDataExtractor;

  private final CaloriesDataExtractor caloriesDataExtractor;

  @Autowired
  public ActivityHandler(final DeadLetterQueue deadLetterQueue, final ActiveDurationDataExtractor activeDurationDataExtractor,
                         final DistanceDataExtractor distanceDataExtractor, final CaloriesDataExtractor caloriesDataExtractor,
                         final ActivityFitnessHealthDataRepository repository, final WebhookPayloadParser payloadParser) {

    super(deadLetterQueue, payloadParser, repository, Activity.class);
    this.activeDurationDataExtractor = activeDurationDataExtractor;
    this.distanceDataExtractor = distanceDataExtractor;
    this.caloriesDataExtractor = caloriesDataExtractor;
  }

  @Override
  protected ActivityFitnessHealthData extractData(Activity activity) {

    ActivityFitnessHealthData activityFitnessHealthData = new ActivityFitnessHealthData();
    activityFitnessHealthData.setDataType(ACTIVITY);
    extractDistance(activityFitnessHealthData, activity.getDistanceData());
    extractActiveDuration(activityFitnessHealthData, activity.getActiveDurationsData());
    extractCalories(activityFitnessHealthData, activity.getCaloriesData());
    return activityFitnessHealthData;
  }

  @Override
  protected void setMetadata(final Activity source, final ActivityFitnessHealthData entity) {

    Metadata metadata = source.getMetadata();
    if (metadata != null) {
     createAndsetMetadata(entity, metadata.getStartTime(), metadata.getEndTime());
    }
  }

  private void extractDistance(final ActivityFitnessHealthData activityFitnessHealthData, final DistanceData data) {

    if (data != null) {
      distanceDataExtractor.extract(data, activityFitnessHealthData);
    }
  }

  private void extractActiveDuration(final ActivityFitnessHealthData activityFitnessHealthData, final ActiveDurationsData data) {

    if (data != null) {
      activeDurationDataExtractor.extract(data, activityFitnessHealthData);
    }
  }

  private void extractCalories(final ActivityFitnessHealthData activityFitnessHealthData, final CaloriesData data) {

    if (data != null) {
      caloriesDataExtractor.extract(data, activityFitnessHealthData);
    }
  }
}
