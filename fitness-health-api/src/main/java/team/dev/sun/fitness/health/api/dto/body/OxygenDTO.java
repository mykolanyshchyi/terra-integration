package team.dev.sun.fitness.health.api.dto.body;

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
public class OxygenDTO implements Serializable {

  private Long id;

  private Double avgSaturationPercentage;

  private Double vo2MaxMlPerMinPerKg;

  private List<OxygenSaturationDTO> oxygenSaturations;

  private List<OxygenVo2DTO> oxygenVo2s;
}
