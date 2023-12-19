package team.dev.sun.fitness.health.mapper.sleep;

import team.dev.sun.fitness.health.api.dto.sleep.AwakeDurationDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.sleep.AwakeDuration;
import org.springframework.stereotype.Component;

@Component
public class AwakeDurationMapper extends AbstractMapper<AwakeDuration, AwakeDurationDTO> {

  @Override
  public AwakeDurationDTO map(final AwakeDuration source) {

    return AwakeDurationDTO.builder()
                           .id(source.getId())
                           .shortInterruptionSeconds(source.getShortInterruptionSeconds())
                           .awakeStateSeconds(source.getAwakeStateSeconds())
                           .longInterruptionSeconds(source.getLongInterruptionSeconds())
                           .numWakeupEvents(source.getNumWakeupEvents())
                           .wakeUpLatencySeconds(source.getWakeUpLatencySeconds())
                           .numOutOfBedEvents(source.getNumOutOfBedEvents())
                           .sleepLatencySeconds(source.getSleepLatencySeconds())
                           .build();
  }
}
