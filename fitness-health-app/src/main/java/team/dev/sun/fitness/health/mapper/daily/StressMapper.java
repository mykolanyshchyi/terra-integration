package team.dev.sun.fitness.health.mapper.daily;

import team.dev.sun.fitness.health.api.dto.daily.StressDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.daily.Stress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StressMapper extends AbstractMapper<Stress, StressDTO> {

  private final StressSampleMapper stressSampleMapper;

  @Override
  public StressDTO map(final Stress source) {

    return StressDTO.builder()
                    .id(source.getId())
                    .restStressDurationSeconds(source.getRestStressDurationSeconds())
                    .stressDurationSeconds(source.getStressDurationSeconds())
                    .activityStressDurationSeconds(source.getActivityStressDurationSeconds())
                    .avgStressLevel(source.getAvgStressLevel())
                    .lowStressDurationSeconds(source.getLowStressDurationSeconds())
                    .mediumStressDurationSeconds(source.getMediumStressDurationSeconds())
                    .highStressDurationSeconds(source.getHighStressDurationSeconds())
                    .maxStressLevel(source.getMaxStressLevel())
                    .samples(stressSampleMapper.map(source.getSamples()))
                    .build();
  }
}
