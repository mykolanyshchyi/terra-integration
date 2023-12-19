package team.dev.sun.fitness.health.mapper.activity;

import team.dev.sun.fitness.health.api.dto.activity.DistanceDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.activity.Distance;
import org.springframework.stereotype.Component;

@Component
public class DistanceMapper extends AbstractMapper<Distance, DistanceDTO> {

  @Override
  public DistanceDTO map(final Distance source) {

    return new DistanceDTO(source.getId(), source.getSteps());
  }
}
