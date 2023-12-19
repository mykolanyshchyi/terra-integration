package team.dev.sun.fitness.health.api.dto.daily;

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
public class StressDTO implements Serializable {

  private Long id;

  private Double restStressDurationSeconds;

  private Double stressDurationSeconds;

  private Double activityStressDurationSeconds;

  private Double avgStressLevel;

  private Double lowStressDurationSeconds;

  private Double mediumStressDurationSeconds;

  private Double highStressDurationSeconds;

  private Double maxStressLevel;

  private List<StressSampleDTO> samples;
}
