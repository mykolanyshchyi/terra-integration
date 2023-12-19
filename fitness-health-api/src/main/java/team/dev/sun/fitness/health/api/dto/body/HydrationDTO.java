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
public class HydrationDTO implements Serializable {

  private Long id;

  private Integer dayTotalWaterConsumptionMl;

  private List<HydrationLevelDTO> hydrationLevels;

  private List<HydrationMeasurementDTO> hydrationMeasurements;
}
