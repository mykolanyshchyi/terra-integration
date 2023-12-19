package team.dev.sun.fitness.health.mapper.activity;

import team.dev.sun.fitness.health.api.dto.activity.ActivityLevelDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.activity.ActivityLevel;
import org.springframework.stereotype.Component;

@Component
public class ActivityLevelMapper extends AbstractMapper<ActivityLevel, ActivityLevelDTO> {

  @Override
  public ActivityLevelDTO map(final ActivityLevel source) {

    return ActivityLevelDTO.builder()
                           .id(source.getId())
                           .level(source.getLevel())
                           .timestamp(source.getTimestamp())
                           .build();
  }
}
