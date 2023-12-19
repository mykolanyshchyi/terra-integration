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
public class GlucoseDTO implements Serializable {

  private Long id;

  private Double dayAvgBloodGlucoseMgPerDl;

  private List<BloodGlucoseSampleDTO> samples;
}
