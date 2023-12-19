package team.dev.sun.fitness.health.mapper.body;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import team.dev.sun.fitness.health.api.dto.body.BloodGlucoseSampleDTO;
import team.dev.sun.fitness.health.api.dto.body.GlucoseDTO;
import team.dev.sun.fitness.health.model.body.BloodGlucoseSample;
import team.dev.sun.fitness.health.model.body.Glucose;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GlucoseMapperTest {

  private static final Long ID = 56L;

  private static final Double DAY_AVG_BLOOD_GLUCOSE_MG_PER_DL = 876.34d;

  @Mock
  private BloodGlucoseSampleMapper bloodGlucoseSampleMapper;

  @InjectMocks
  private GlucoseMapper glucoseMapper;

  @Mock
  private BloodGlucoseSample bloodGlucoseSample;

  @Mock
  private BloodGlucoseSampleDTO bloodGlucoseSampleDTO;

  @Test
  void mapGlucoseWoSample() {

    GlucoseDTO expected = getDto(null);
    Glucose entity = getEntity(null);
    when(bloodGlucoseSampleMapper.map(anyList())).thenReturn(emptyList());
    GlucoseDTO actual = glucoseMapper.map(entity);
    verify(bloodGlucoseSampleMapper).map(anyList());
    assertEquals(expected, actual);
  }

  @Test
  void mapGlucose() {

    GlucoseDTO expected = getDto(bloodGlucoseSampleDTO);
    Glucose entity = getEntity(bloodGlucoseSample);
    when(bloodGlucoseSampleMapper.map(anyList())).thenReturn(List.of(bloodGlucoseSampleDTO));
    GlucoseDTO actual = glucoseMapper.map(entity);
    verify(bloodGlucoseSampleMapper).map(anyList());
    assertEquals(expected, actual);
  }

  private Glucose getEntity(BloodGlucoseSample bloodGlucoseSample) {

    Glucose glucose = new Glucose();
    glucose.setId(ID);
    glucose.setDayAvgBloodGlucoseMgPerDl(DAY_AVG_BLOOD_GLUCOSE_MG_PER_DL);
    glucose.setSamples(bloodGlucoseSample == null ? emptyList() : List.of(bloodGlucoseSample));
    return glucose;
  }

  private GlucoseDTO getDto(BloodGlucoseSampleDTO bloodGlucoseSample) {

    return GlucoseDTO.builder()
                     .id(ID)
                     .dayAvgBloodGlucoseMgPerDl(DAY_AVG_BLOOD_GLUCOSE_MG_PER_DL)
                     .samples(bloodGlucoseSample == null ? emptyList() : List.of(bloodGlucoseSample))
                     .build();
  }
}