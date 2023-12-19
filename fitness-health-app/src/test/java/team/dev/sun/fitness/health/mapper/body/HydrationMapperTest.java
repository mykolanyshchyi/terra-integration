package team.dev.sun.fitness.health.mapper.body;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import team.dev.sun.fitness.health.api.dto.body.HydrationDTO;
import team.dev.sun.fitness.health.api.dto.body.HydrationLevelDTO;
import team.dev.sun.fitness.health.api.dto.body.HydrationMeasurementDTO;
import team.dev.sun.fitness.health.model.body.Hydration;
import team.dev.sun.fitness.health.model.body.HydrationLevel;
import team.dev.sun.fitness.health.model.body.HydrationMeasurement;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HydrationMapperTest {

  private static final Long ID = 4567L;

  private static final Integer DAY_TOTAL_WATER_CONSUMPTION_ML = 76543;

  @Mock
  private HydrationLevelMapper hydrationLevelMapper;

  @Mock
  private HydrationMeasurementMapper hydrationMeasurementMapper;

  @InjectMocks
  private HydrationMapper hydrationMapper;

  @Mock
  private HydrationLevel hydrationLevel;

  @Mock
  private HydrationMeasurement hydrationMeasurement;

  @Mock
  private HydrationLevelDTO hydrationLevelDTO;

  @Mock
  private HydrationMeasurementDTO hydrationMeasurementDTO;

  @Test
  void mapHydrationWoAnyNestedEntity() {

    HydrationDTO expected = getDto(null, null);
    Hydration entity = getEntity(null, null);

    when(hydrationLevelMapper.map((List<HydrationLevel>) isNull())).thenReturn(emptyList());
    when(hydrationMeasurementMapper.map((List<HydrationMeasurement>) isNull())).thenReturn(emptyList());

    HydrationDTO actual = hydrationMapper.map(entity);

    verify(hydrationLevelMapper).map((List<HydrationLevel>) isNull());
    verify(hydrationMeasurementMapper).map((List<HydrationMeasurement>) isNull());

    assertEquals(expected, actual);
  }

  @Test
  void mapHydrationWoHydrationLevel() {

    HydrationDTO expected = getDto(null, hydrationMeasurementDTO);
    Hydration entity = getEntity(null, hydrationMeasurement);

    when(hydrationLevelMapper.map((List<HydrationLevel>) isNull())).thenReturn(emptyList());
    when(hydrationMeasurementMapper.map(anyList())).thenReturn(List.of(hydrationMeasurementDTO));

    HydrationDTO actual = hydrationMapper.map(entity);

    verify(hydrationLevelMapper).map((List<HydrationLevel>) isNull());
    verify(hydrationMeasurementMapper).map(anyList());

    assertEquals(expected, actual);
  }

  @Test
  void mapHydrationWoHydrationMeasurement() {

    HydrationDTO expected = getDto(hydrationLevelDTO, null);
    Hydration entity = getEntity(hydrationLevel, null);

    when(hydrationLevelMapper.map(anyList())).thenReturn(List.of(hydrationLevelDTO));
    when(hydrationMeasurementMapper.map((List<HydrationMeasurement>) isNull())).thenReturn(emptyList());

    HydrationDTO actual = hydrationMapper.map(entity);

    verify(hydrationLevelMapper).map(anyList());
    verify(hydrationMeasurementMapper).map((List<HydrationMeasurement>) isNull());

    assertEquals(expected, actual);
  }

  @Test
  void mapHydration() {

    HydrationDTO expected = getDto(hydrationLevelDTO, hydrationMeasurementDTO);
    Hydration entity = getEntity(hydrationLevel, hydrationMeasurement);

    when(hydrationLevelMapper.map(anyList())).thenReturn(List.of(hydrationLevelDTO));
    when(hydrationMeasurementMapper.map(anyList())).thenReturn(List.of(hydrationMeasurementDTO));

    HydrationDTO actual = hydrationMapper.map(entity);

    verify(hydrationLevelMapper).map(anyList());
    verify(hydrationMeasurementMapper).map(anyList());

    assertEquals(expected, actual);
  }

  private Hydration getEntity(HydrationLevel hydrationLevel, HydrationMeasurement hydrationMeasurement) {

    Hydration hydration = new Hydration();
    hydration.setId(ID);
    hydration.setDayTotalWaterConsumptionMl(DAY_TOTAL_WATER_CONSUMPTION_ML);
    hydration.setHydrationLevels(hydrationLevel == null ? null : List.of(hydrationLevel));
    hydration.setHydrationMeasurements(hydrationMeasurement == null ? null : List.of(hydrationMeasurement));
    return hydration;
  }

  private HydrationDTO getDto(HydrationLevelDTO hydrationLevel, HydrationMeasurementDTO hydrationMeasurement) {

    return HydrationDTO.builder()
                       .id(ID)
                       .dayTotalWaterConsumptionMl(DAY_TOTAL_WATER_CONSUMPTION_ML)
                       .hydrationLevels(hydrationLevel == null ? emptyList() : List.of(hydrationLevel))
                       .hydrationMeasurements(hydrationMeasurement == null ? emptyList() : List.of(hydrationMeasurement))
                       .build();
  }
}