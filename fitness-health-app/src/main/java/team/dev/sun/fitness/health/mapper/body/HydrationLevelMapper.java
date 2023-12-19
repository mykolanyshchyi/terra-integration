package team.dev.sun.fitness.health.mapper.body;

import team.dev.sun.fitness.health.api.dto.body.HydrationLevelDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.body.HydrationLevel;
import org.springframework.stereotype.Component;

@Component
public class HydrationLevelMapper extends AbstractMapper<HydrationLevel, HydrationLevelDTO> {

  @Override
  public HydrationLevelDTO map(final HydrationLevel source) {

    return HydrationLevelDTO.builder()
                            .id(source.getId())
                            .level(source.getLevel())
                            .timestamp(source.getTimestamp())
                            .build();
  }
}
