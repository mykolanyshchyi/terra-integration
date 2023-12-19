package team.dev.sun.fitness.health.api.dto.activity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistanceDTO implements Serializable {

  private Long id;

  private Integer steps;
}
