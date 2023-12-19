package team.dev.sun.fitness.health.mapper.activity;

import team.dev.sun.fitness.health.api.dto.activity.ActiveDurationDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.activity.ActiveDuration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActiveDurationMapper extends AbstractMapper<ActiveDuration, ActiveDurationDTO> {

  private final ActivityLevelMapper activityLevelMapper;

  @Override
  public ActiveDurationDTO map(final ActiveDuration source) {

    return ActiveDurationDTO
        .builder()
        .id(source.getId())
        .activitySeconds(source.getActivitySeconds())
        .restSeconds(source.getRestSeconds())
        .lowIntensitySeconds(source.getLowIntensitySeconds())
        .vigorousIntensitySeconds(source.getVigorousIntensitySeconds())
        .numContinuousInactivePeriods(source.getNumContinuousInactivePeriods())
        .inactivitySeconds(source.getInactivitySeconds())
        .moderateIntensitySeconds(source.getModerateIntensitySeconds())
        .activityLevels(activityLevelMapper.map(source.getActivityLevels()))
        .build();
  }
}
