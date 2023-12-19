package team.dev.sun.fitness.health.mapper.daily;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import team.dev.sun.fitness.health.api.dto.daily.StressDTO;
import team.dev.sun.fitness.health.api.dto.daily.StressSampleDTO;
import team.dev.sun.fitness.health.model.daily.Stress;
import team.dev.sun.fitness.health.model.daily.StressSample;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StressMapperTest {

  private static final Long ID = 1L;

  private static final Double REST_STRESS_DURATION_SECONDS = 2d;

  private static final Double STRESS_DURATION_SECONDS = 3d;

  private static final Double ACTIVITY_STRESS_DURATION_SECONDS = 4d;

  private static final Double AVG_STRESS_LEVEL = 5.5d;

  private static final Double LOW_STRESS_DURATION_SECONDS = 6d;

  private static final Double MEDIUM_STRESS_DURATION_SECONDS = 7d;

  private static final Double HIGH_STRESS_DURATION_SECONDS = 8d;

  private static final Double MAX_STRESS_LEVEL = 9.1d;

  @Mock
  private StressSampleMapper stressSampleMapper;

  @InjectMocks
  private StressMapper stressMapper;

  @Mock
  private StressSample stressSample;

  @Mock
  private StressSampleDTO stressSampleDTO;

  @Test
  void mapStressWoStressSample() {

    StressDTO expected = getDto(null);
    when(stressSampleMapper.map((List<StressSample>) isNull())).thenReturn(emptyList());
    StressDTO actual = stressMapper.map(getEntity(null));
    verify(stressSampleMapper).map((List<StressSample>) isNull());
    assertEquals(expected, actual);
  }

  @Test
  void mapStress() {

    StressDTO expected = getDto(stressSampleDTO);
    when(stressSampleMapper.map(anyList())).thenReturn(List.of(stressSampleDTO));
    StressDTO actual = stressMapper.map(getEntity(stressSample));
    verify(stressSampleMapper).map(anyList());
    assertEquals(expected, actual);
  }

  private Stress getEntity(StressSample sample) {

    Stress stress = new Stress();
    stress.setId(ID);
    stress.setRestStressDurationSeconds(REST_STRESS_DURATION_SECONDS);
    stress.setStressDurationSeconds(STRESS_DURATION_SECONDS);
    stress.setActivityStressDurationSeconds(ACTIVITY_STRESS_DURATION_SECONDS);
    stress.setAvgStressLevel(AVG_STRESS_LEVEL);
    stress.setLowStressDurationSeconds(LOW_STRESS_DURATION_SECONDS);
    stress.setMediumStressDurationSeconds(MEDIUM_STRESS_DURATION_SECONDS);
    stress.setHighStressDurationSeconds(HIGH_STRESS_DURATION_SECONDS);
    stress.setMaxStressLevel(MAX_STRESS_LEVEL);
    stress.setSamples(sample == null ? null : List.of(sample));
    return stress;
  }

  private StressDTO getDto(StressSampleDTO sample) {

    return StressDTO.builder()
                    .id(ID)
                    .restStressDurationSeconds(REST_STRESS_DURATION_SECONDS)
                    .stressDurationSeconds(STRESS_DURATION_SECONDS)
                    .activityStressDurationSeconds(ACTIVITY_STRESS_DURATION_SECONDS)
                    .avgStressLevel(AVG_STRESS_LEVEL)
                    .lowStressDurationSeconds(LOW_STRESS_DURATION_SECONDS)
                    .mediumStressDurationSeconds(MEDIUM_STRESS_DURATION_SECONDS)
                    .highStressDurationSeconds(HIGH_STRESS_DURATION_SECONDS)
                    .maxStressLevel(MAX_STRESS_LEVEL)
                    .samples(sample == null ? emptyList() : List.of(sample))
                    .build();
  }
}