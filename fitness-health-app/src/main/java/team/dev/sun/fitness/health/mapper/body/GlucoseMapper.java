package team.dev.sun.fitness.health.mapper.body;

import team.dev.sun.fitness.health.api.dto.body.GlucoseDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.body.Glucose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GlucoseMapper extends AbstractMapper<Glucose, GlucoseDTO> {

  private final BloodGlucoseSampleMapper bloodGlucoseSampleMapper;

  @Override
  public GlucoseDTO map(final Glucose source) {

    return GlucoseDTO.builder()
                     .id(source.getId())
                     .dayAvgBloodGlucoseMgPerDl(source.getDayAvgBloodGlucoseMgPerDl())
                     .samples(bloodGlucoseSampleMapper.map(source.getSamples()))
                     .build();
  }
}
