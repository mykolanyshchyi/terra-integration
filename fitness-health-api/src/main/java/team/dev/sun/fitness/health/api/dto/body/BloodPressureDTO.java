package team.dev.sun.fitness.health.api.dto.body;

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
public class BloodPressureDTO implements Serializable {

  private Long id;

  private Double diastolicBp;

  private Double systolicBp;

  private ZonedDateTime timestamp;
}
