package team.dev.sun.fitness.health.mapper.body;

import team.dev.sun.fitness.health.api.dto.body.HydrationMeasurementDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.body.HydrationMeasurement;
import org.springframework.stereotype.Component;

@Component
public class HydrationMeasurementMapper extends AbstractMapper<HydrationMeasurement, HydrationMeasurementDTO> {

  @Override
  public HydrationMeasurementDTO map(final HydrationMeasurement source) {

    return HydrationMeasurementDTO.builder()
                                  .id(source.getId())
                                  .hydrationKg(source.getHydrationKg())
                                  .timestamp(source.getTimestamp())
                                  .build();
  }
}
