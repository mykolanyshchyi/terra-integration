package team.dev.sun.fitness.health.mapper.activity;

import static team.dev.sun.fitness.health.util.ObjectUtil.applyNonNull;

import team.dev.sun.fitness.health.api.dto.activity.ActivityFitnessHealthDataDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.activity.ActivityFitnessHealthData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivityFitnessHealthDataMapper extends AbstractMapper<ActivityFitnessHealthData, ActivityFitnessHealthDataDTO> {

  private final ActiveDurationMapper activeDurationMapper;

  private final CaloriesMapper caloriesMapper;

  private final DistanceMapper distanceMapper;

  @Override
  public ActivityFitnessHealthDataDTO map(final ActivityFitnessHealthData data) {

    ActivityFitnessHealthDataDTO dto = mapBase(data, new ActivityFitnessHealthDataDTO());
    applyNonNull(data::getActiveDuration, dto::setActiveDuration, activeDurationMapper::map);
    applyNonNull(data::getCalories, dto::setCalories, caloriesMapper::map);
    applyNonNull(data::getDistance, dto::setDistance, distanceMapper::map);
    return dto;
  }
}
