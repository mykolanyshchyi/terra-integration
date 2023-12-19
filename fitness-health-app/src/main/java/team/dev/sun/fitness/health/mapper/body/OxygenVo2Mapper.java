package team.dev.sun.fitness.health.mapper.body;

import team.dev.sun.fitness.health.api.dto.body.OxygenVo2DTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.body.OxygenVo2;
import org.springframework.stereotype.Component;

@Component
public class OxygenVo2Mapper extends AbstractMapper<OxygenVo2, OxygenVo2DTO> {

  @Override
  public OxygenVo2DTO map(final OxygenVo2 source) {

    return OxygenVo2DTO.builder()
                       .id(source.getId())
                       .timestamp(source.getTimestamp())
                       .vo2MaxMlPerMinPerKg(source.getVo2MaxMlPerMinPerKg())
                       .build();
  }
}
