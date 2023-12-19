package team.dev.sun.fitness.health.mapper.daily;

import team.dev.sun.fitness.health.api.dto.daily.StressSampleDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.daily.StressSample;
import org.springframework.stereotype.Component;

@Component
public class StressSampleMapper extends AbstractMapper<StressSample, StressSampleDTO> {

  @Override
  public StressSampleDTO map(final StressSample source) {

    return StressSampleDTO.builder()
                          .id(source.getId())
                          .level(source.getLevel())
                          .timestamp(source.getTimestamp())
                          .build();
  }
}
