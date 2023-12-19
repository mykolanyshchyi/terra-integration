package team.dev.sun.fitness.health.mapper.body;

import team.dev.sun.fitness.health.api.dto.body.TemperatureDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.body.Temperature;
import org.springframework.stereotype.Component;

@Component
public class TemperatureMapper extends AbstractMapper<Temperature, TemperatureDTO> {

  @Override
  public TemperatureDTO map(final Temperature source) {

    return TemperatureDTO.builder()
                         .id(source.getId())
                         .type(source.getType())
                         .temperatureCelsius(source.getTemperatureCelsius())
                         .timestamp(source.getTimestamp())
                         .build();
  }
}
