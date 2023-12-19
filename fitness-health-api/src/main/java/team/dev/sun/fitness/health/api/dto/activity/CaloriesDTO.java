package team.dev.sun.fitness.health.api.dto.activity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaloriesDTO implements Serializable {

  private Long id;

  private Double netIntakeCalories;

  private Double bmrCalories;

  private Double totalBurnedCalories;

  private Double netActivityCalories;
}
