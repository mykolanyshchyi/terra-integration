package team.dev.sun.fitness.health.api.dto;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetadataDTO {
  private Long id;
  private ZonedDateTime startTime;
  private ZonedDateTime endTime;
}
