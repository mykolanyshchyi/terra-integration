package team.dev.sun.fitness.health.extractor.activity;

import static team.dev.sun.fitness.health.util.DateUtil.parseDate;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import co.tryterra.terraclient.models.v2.activity.ActiveDurationsData;
import co.tryterra.terraclient.models.v2.samples.ActivityLevelSample;
import team.dev.sun.fitness.health.api.model.ActivityLevelType;
import team.dev.sun.fitness.health.extractor.AbstractDataExtractor;
import team.dev.sun.fitness.health.model.activity.ActiveDuration;
import team.dev.sun.fitness.health.model.activity.ActivityFitnessHealthData;
import team.dev.sun.fitness.health.model.activity.ActivityLevel;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ActiveDurationDataExtractor extends AbstractDataExtractor<ActiveDurationsData, ActivityFitnessHealthData> {

  @Override
  protected void extractData(final ActiveDurationsData source, final ActivityFitnessHealthData entity) {

    if (source != null) {
      ActiveDuration activeDuration = new ActiveDuration();
      activeDuration.setActivitySeconds(source.getActivitySeconds());
      activeDuration.setRestSeconds(source.getRestSeconds());
      activeDuration.setLowIntensitySeconds(source.getLowIntensitySeconds());
      activeDuration.setVigorousIntensitySeconds(source.getVigorousIntensitySeconds());
      activeDuration.setNumContinuousInactivePeriods(source.getNumContinuousInactivePeriods());
      activeDuration.setInactivitySeconds(source.getInactivitySeconds());
      activeDuration.setModerateIntensitySeconds(source.getModerateIntensitySeconds());

      List<ActivityLevel> activityLevels = extractActivityLevels(source.getActivityLevelsSamples(), activeDuration);
      activeDuration.setActivityLevels(activityLevels);
      entity.setActiveDuration(activeDuration);
    }
  }

  private List<ActivityLevel> extractActivityLevels(final List<ActivityLevelSample> samples, ActiveDuration activeDuration) {

    if (isNotEmpty(samples)) {
      return samples.stream()
                    .map(sample -> {
                      ActivityLevel activityLevel = new ActivityLevel();
                      ActivityLevelType levelType = ActivityLevelType.byIntLevel(sample.getLevel());
                      activityLevel.setLevel(levelType);
                      activityLevel.setTimestamp(parseDate(sample.getTimestamp()));
                      activityLevel.setActiveDuration(activeDuration);
                      return activityLevel;
                    })
                    .toList();
    }
    return Collections.emptyList();
  }
}
