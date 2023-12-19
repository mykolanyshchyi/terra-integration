package team.dev.sun.fitness.health.api.dto.daily;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDTO implements Serializable {

  private Long id;

  private Double recovery;

  private Double activity;

  private Double sleep;
}
