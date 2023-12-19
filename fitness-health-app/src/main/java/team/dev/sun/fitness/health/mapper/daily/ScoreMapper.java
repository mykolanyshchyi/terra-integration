package team.dev.sun.fitness.health.mapper.daily;

import team.dev.sun.fitness.health.api.dto.daily.ScoreDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.daily.Score;
import org.springframework.stereotype.Component;

@Component
public class ScoreMapper extends AbstractMapper<Score, ScoreDTO> {

  @Override
  public ScoreDTO map(final Score source) {

    return ScoreDTO.builder()
                   .id(source.getId())
                   .recovery(source.getRecovery())
                   .activity(source.getActivity())
                   .sleep(source.getSleep())
                   .build();
  }
}
