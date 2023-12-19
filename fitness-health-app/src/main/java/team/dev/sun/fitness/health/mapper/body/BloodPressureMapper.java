package team.dev.sun.fitness.health.mapper.body;

import team.dev.sun.fitness.health.api.dto.body.BloodPressureDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.body.BloodPressure;
import org.springframework.stereotype.Component;

@Component
public class BloodPressureMapper extends AbstractMapper<BloodPressure, BloodPressureDTO> {

  @Override
  public BloodPressureDTO map(final BloodPressure source) {

    return BloodPressureDTO.builder()
                           .id(source.getId())
                           .diastolicBp(source.getDiastolicBp())
                           .systolicBp(source.getSystolicBp())
                           .timestamp(source.getTimestamp())
                           .build();
  }
}
