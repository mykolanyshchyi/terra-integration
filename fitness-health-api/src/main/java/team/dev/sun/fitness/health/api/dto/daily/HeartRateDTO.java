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
public class HeartRateDTO implements Serializable {

  private Long id;

  private Integer maxHrBpm;

  private Integer restingHrBpm;

  private Double avgHrvRmssd;

  private Integer minHrBpm;

  private Integer userMaxHrBpm;

  private Double avgHrvSdnn;

  private Integer avgHrBpm;
}
