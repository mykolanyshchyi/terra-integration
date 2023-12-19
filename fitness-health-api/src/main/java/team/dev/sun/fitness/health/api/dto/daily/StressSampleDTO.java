package team.dev.sun.fitness.health.api.dto.daily;

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
public class StressSampleDTO implements Serializable {

  private Long id;

  private Integer level;

  private ZonedDateTime timestamp;
}
