package team.dev.sun.fitness.health.mapper.body;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import team.dev.sun.fitness.health.api.dto.body.OxygenDTO;
import team.dev.sun.fitness.health.api.dto.body.OxygenSaturationDTO;
import team.dev.sun.fitness.health.api.dto.body.OxygenVo2DTO;
import team.dev.sun.fitness.health.model.body.Oxygen;
import team.dev.sun.fitness.health.model.body.OxygenSaturation;
import team.dev.sun.fitness.health.model.body.OxygenVo2;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OxygenMapperTest {

  private static final Long ID = 675L;

  private static final Double AVG_SATURATION_PERCENTAGE = 85.4d;

  private static final Double VO2_MAX_ML_PER_MIN_PER_KG = 45.321d;

  @Mock
  private OxygenSaturationMapper oxygenSaturationMapper;

  @Mock
  private OxygenVo2Mapper oxygenVo2Mapper;

  @InjectMocks
  private OxygenMapper oxygenMapper;

  @Mock
  private OxygenSaturation saturation;

  @Mock
  private OxygenSaturationDTO saturationDTO;

  @Mock
  private OxygenVo2 vo2;

  @Mock
  private OxygenVo2DTO vo2DTO;

  @Test
  void mapOxygenWoAnyNestedEntity() {

    OxygenDTO expected = getDto(null, null);
    Oxygen entity = getEntity(null, null);

    when(oxygenSaturationMapper.map((List<OxygenSaturation>) isNull())).thenReturn(emptyList());
    when(oxygenVo2Mapper.map((List<OxygenVo2>) isNull())).thenReturn(emptyList());

    OxygenDTO actual = oxygenMapper.map(entity);

    verify(oxygenSaturationMapper).map((List<OxygenSaturation>) isNull());
    verify(oxygenVo2Mapper).map((List<OxygenVo2>) isNull());

    assertEquals(expected, actual);
  }

  @Test
  void mapOxygenWoOxygenSaturation() {

    OxygenDTO expected = getDto(null, vo2DTO);
    Oxygen entity = getEntity(null, vo2);

    when(oxygenSaturationMapper.map((List<OxygenSaturation>) isNull())).thenReturn(emptyList());
    when(oxygenVo2Mapper.map(anyList())).thenReturn(List.of(vo2DTO));

    OxygenDTO actual = oxygenMapper.map(entity);

    verify(oxygenSaturationMapper).map((List<OxygenSaturation>) isNull());
    verify(oxygenVo2Mapper).map(anyList());

    assertEquals(expected, actual);
  }

  @Test
  void mapOxygenWoOxygenVo2() {

    OxygenDTO expected = getDto(saturationDTO, null);
    Oxygen entity = getEntity(saturation, null);

    when(oxygenSaturationMapper.map(anyList())).thenReturn(List.of(saturationDTO));
    when(oxygenVo2Mapper.map((List<OxygenVo2>) isNull())).thenReturn(emptyList());

    OxygenDTO actual = oxygenMapper.map(entity);

    verify(oxygenSaturationMapper).map(anyList());
    verify(oxygenVo2Mapper).map((List<OxygenVo2>) isNull());

    assertEquals(expected, actual);
  }

  @Test
  void mapOxygen() {

    OxygenDTO expected = getDto(saturationDTO, vo2DTO);
    Oxygen entity = getEntity(saturation, vo2);

    when(oxygenSaturationMapper.map(anyList())).thenReturn(List.of(saturationDTO));
    when(oxygenVo2Mapper.map(anyList())).thenReturn(List.of(vo2DTO));

    OxygenDTO actual = oxygenMapper.map(entity);

    verify(oxygenSaturationMapper).map(anyList());
    verify(oxygenVo2Mapper).map(anyList());

    assertEquals(expected, actual);
  }

  private Oxygen getEntity(OxygenSaturation saturation, OxygenVo2 vo2) {

    Oxygen oxygen = new Oxygen();
    oxygen.setId(ID);
    oxygen.setAvgSaturationPercentage(AVG_SATURATION_PERCENTAGE);
    oxygen.setVo2MaxMlPerMinPerKg(VO2_MAX_ML_PER_MIN_PER_KG);
    oxygen.setOxygenSaturations(saturation == null ? null : List.of(saturation));
    oxygen.setOxygenVo2s(vo2 == null ? null : List.of(vo2));
    return oxygen;
  }

  private OxygenDTO getDto(OxygenSaturationDTO saturation, OxygenVo2DTO vo2) {

    return OxygenDTO.builder()
                    .id(ID)
                    .avgSaturationPercentage(AVG_SATURATION_PERCENTAGE)
                    .vo2MaxMlPerMinPerKg(VO2_MAX_ML_PER_MIN_PER_KG)
                    .oxygenSaturations(saturation == null ? emptyList() : List.of(saturation))
                    .oxygenVo2s(vo2 == null ? emptyList() : List.of(vo2))
                    .build();
  }
}