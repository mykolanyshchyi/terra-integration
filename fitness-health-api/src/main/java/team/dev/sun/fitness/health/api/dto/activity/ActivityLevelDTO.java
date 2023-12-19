package team.dev.sun.fitness.health.api.dto.activity;

import team.dev.sun.fitness.health.api.model.ActivityLevelType;
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
public class ActivityLevelDTO implements Serializable {

  private Long id;

  private ActivityLevelType level;

  private ZonedDateTime timestamp;
}
