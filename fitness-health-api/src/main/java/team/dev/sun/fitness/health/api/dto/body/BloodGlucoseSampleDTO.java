package team.dev.sun.fitness.health.api.dto.body;

import team.dev.sun.fitness.health.api.model.BloodGlucoseType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BloodGlucoseSampleDTO implements Serializable {

  private Long id;

  private BloodGlucoseType type;

  private ZonedDateTime timestamp;

  private Double bloodGlucoseMgPerDl;

  private Integer glucoseLevelFlag;

  private Integer trendArrow;
}
