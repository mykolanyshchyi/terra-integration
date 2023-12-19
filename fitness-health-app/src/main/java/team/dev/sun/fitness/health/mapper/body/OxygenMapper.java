package team.dev.sun.fitness.health.mapper.body;

import team.dev.sun.fitness.health.api.dto.body.OxygenDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.body.Oxygen;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OxygenMapper extends AbstractMapper<Oxygen, OxygenDTO> {

  private final OxygenSaturationMapper oxygenSaturationMapper;

  private final OxygenVo2Mapper oxygenVo2Mapper;

  @Override
  public OxygenDTO map(final Oxygen source) {

    return OxygenDTO.builder()
                    .id(source.getId())
                    .avgSaturationPercentage(source.getAvgSaturationPercentage())
                    .vo2MaxMlPerMinPerKg(source.getVo2MaxMlPerMinPerKg())
                    .oxygenSaturations(oxygenSaturationMapper.map(source.getOxygenSaturations()))
                    .oxygenVo2s(oxygenVo2Mapper.map(source.getOxygenVo2s()))
                    .build();
  }
}
