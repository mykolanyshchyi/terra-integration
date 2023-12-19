package team.dev.sun.fitness.health.mapper.sleep;

import static team.dev.sun.fitness.health.util.ObjectUtil.applyNonNull;

import team.dev.sun.fitness.health.api.dto.sleep.SleepFitnessHealthDataDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.sleep.SleepFitnessHealthData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SleepFitnessHealthDataMapper extends AbstractMapper<SleepFitnessHealthData, SleepFitnessHealthDataDTO> {

  private final AwakeDurationMapper awakeDurationMapper;

  private final AsleepDurationMapper asleepDurationMapper;

  @Override
  public SleepFitnessHealthDataDTO map(final SleepFitnessHealthData source) {

    SleepFitnessHealthDataDTO dto = mapBase(source, new SleepFitnessHealthDataDTO());
    applyNonNull(source::getAwakeDuration, dto::setAwakeDuration, awakeDurationMapper::map);
    applyNonNull(source::getAsleepDuration, dto::setAsleepDuration, asleepDurationMapper::map);
    return dto;
  }
}
