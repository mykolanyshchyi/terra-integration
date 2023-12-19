package team.dev.sun.fitness.health.mapper.daily;

import team.dev.sun.fitness.health.api.dto.daily.HeartRateDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.daily.HeartRate;
import org.springframework.stereotype.Component;

@Component
public class HeartRateMapper extends AbstractMapper<HeartRate, HeartRateDTO> {

  @Override
  public HeartRateDTO map(final HeartRate source) {

    return HeartRateDTO.builder()
                       .id(source.getId())
                       .maxHrBpm(source.getMaxHrBpm())
                       .restingHrBpm(source.getRestingHrBpm())
                       .avgHrvRmssd(source.getAvgHrvRmssd())
                       .minHrBpm(source.getMinHrBpm())
                       .userMaxHrBpm(source.getUserMaxHrBpm())
                       .avgHrvSdnn(source.getAvgHrvSdnn())
                       .avgHrBpm(source.getAvgHrBpm())
                       .build();
  }
}
