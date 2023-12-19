package team.dev.sun.fitness.health.mapper.sleep;

import team.dev.sun.fitness.health.api.dto.sleep.AsleepDurationDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.sleep.AsleepDuration;
import org.springframework.stereotype.Component;

@Component
public class AsleepDurationMapper extends AbstractMapper<AsleepDuration, AsleepDurationDTO> {

  @Override
  public AsleepDurationDTO map(final AsleepDuration source) {

    return AsleepDurationDTO.builder()
                            .id(source.getId())
                            .lightSleepStateSeconds(source.getLightSleepStateSeconds())
                            .asleepStateSeconds(source.getAsleepStateSeconds())
                            .numRemEvents(source.getNumRemEvents())
                            .remSleepStateSeconds(source.getRemSleepStateSeconds())
                            .deepSleepStateSeconds(source.getDeepSleepStateSeconds())
                            .build();
  }
}
