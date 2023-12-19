package team.dev.sun.fitness.health.mapper.body;

import team.dev.sun.fitness.health.api.dto.body.HydrationDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.body.Hydration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HydrationMapper extends AbstractMapper<Hydration, HydrationDTO> {

  private final HydrationLevelMapper hydrationLevelMapper;

  private final HydrationMeasurementMapper hydrationMeasurementMapper;

  @Override
  public HydrationDTO map(final Hydration source) {

    return HydrationDTO.builder()
                       .id(source.getId())
                       .dayTotalWaterConsumptionMl(source.getDayTotalWaterConsumptionMl())
                       .hydrationLevels(hydrationLevelMapper.map(source.getHydrationLevels()))
                       .hydrationMeasurements(hydrationMeasurementMapper.map(source.getHydrationMeasurements()))
                       .build();
  }
}
