package team.dev.sun.fitness.health.mapper.daily;

import static team.dev.sun.fitness.health.util.ObjectUtil.applyNonNull;

import team.dev.sun.fitness.health.api.dto.daily.DailyFitnessHealthDataDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.daily.DailyFitnessHealthData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyFitnessHealthDataMapper extends AbstractMapper<DailyFitnessHealthData, DailyFitnessHealthDataDTO> {

  private final HeartRateMapper heartRateMapper;

  private final ScoreMapper scoreMapper;

  private final StressMapper stressMapper;

  @Override
  public DailyFitnessHealthDataDTO map(final DailyFitnessHealthData source) {

    DailyFitnessHealthDataDTO dto = mapBase(source, new DailyFitnessHealthDataDTO());
    applyNonNull(source::getHeartRate, dto::setHeartRate, heartRateMapper::map);
    applyNonNull(source::getScore, dto::setScore, scoreMapper::map);
    applyNonNull(source::getStress, dto::setStress, stressMapper::map);
    return dto;
  }
}
