package team.dev.sun.fitness.health.mapper.body;

import team.dev.sun.fitness.health.api.dto.body.OxygenSaturationDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.body.OxygenSaturation;
import org.springframework.stereotype.Component;

@Component
public class OxygenSaturationMapper extends AbstractMapper<OxygenSaturation, OxygenSaturationDTO> {

  @Override
  public OxygenSaturationDTO map(final OxygenSaturation source) {

    return OxygenSaturationDTO.builder()
                              .id(source.getId())
                              .percentage(source.getPercentage())
                              .timestamp(source.getTimestamp())
                              .build();
  }
}
