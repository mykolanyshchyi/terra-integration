package team.dev.sun.fitness.health.api.dto.activity;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActiveDurationDTO implements Serializable {

  private Long id;

  private Double activitySeconds;

  private Double restSeconds;

  private Double lowIntensitySeconds;

  private Double vigorousIntensitySeconds;

  private Integer numContinuousInactivePeriods;

  private Double inactivitySeconds;

  private Double moderateIntensitySeconds;

  private List<ActivityLevelDTO> activityLevels;
}
