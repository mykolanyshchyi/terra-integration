package team.dev.sun.fitness.health.mapper.body;

import team.dev.sun.fitness.health.api.dto.body.BloodGlucoseSampleDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.body.BloodGlucoseSample;
import org.springframework.stereotype.Component;

@Component
public class BloodGlucoseSampleMapper extends AbstractMapper<BloodGlucoseSample, BloodGlucoseSampleDTO> {

  @Override
  public BloodGlucoseSampleDTO map(final BloodGlucoseSample source) {

    return BloodGlucoseSampleDTO.builder()
                                .id(source.getId())
                                .type(source.getType())
                                .timestamp(source.getTimestamp())
                                .bloodGlucoseMgPerDl(source.getBloodGlucoseMgPerDl())
                                .glucoseLevelFlag(source.getGlucoseLevelFlag())
                                .trendArrow(source.getTrendArrow())
                                .build();
  }
}
