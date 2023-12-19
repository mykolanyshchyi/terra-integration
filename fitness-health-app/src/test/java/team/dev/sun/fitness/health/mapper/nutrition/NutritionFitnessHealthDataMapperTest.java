package team.dev.sun.fitness.health.mapper.nutrition;

import static team.dev.sun.fitness.health.api.model.DataType.NUTRITION;
import static team.dev.sun.fitness.health.api.model.Provider.OMRON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import team.dev.sun.fitness.health.api.dto.nutrition.NutritionFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.nutrition.NutritionMacrosDTO;
import team.dev.sun.fitness.health.api.dto.nutrition.NutritionMicrosDTO;
import team.dev.sun.fitness.health.api.model.DataType;
import team.dev.sun.fitness.health.api.model.Provider;
import team.dev.sun.fitness.health.model.nutrition.NutritionFitnessHealthData;
import team.dev.sun.fitness.health.model.nutrition.NutritionMacros;
import team.dev.sun.fitness.health.model.nutrition.NutritionMicros;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NutritionFitnessHealthDataMapperTest {

  private static final Long ID = 1L;

  private static final Long CLIENT_ID = 5L;

  private static final Long USER_ID = 9L;

  private static final String DEVICE_ID = "some device id";

  private static final Provider PROVIDER = OMRON;

  private static final DataType DATA_TYPE = NUTRITION;

  private static final ZonedDateTime CREATED_AT = ZonedDateTime.now();

  @Mock
  private NutritionMacrosMapper nutritionMacrosMapper;

  @Mock
  private NutritionMicrosMapper nutritionMicrosMapper;

  @InjectMocks
  private NutritionFitnessHealthDataMapper nutritionFitnessHealthDataMapper;

  @Mock
  private NutritionMacros macros;

  @Mock
  private NutritionMacrosDTO macrosDTO;

  @Mock
  private NutritionMicros micros;

  @Mock
  private NutritionMicrosDTO microsDTO;

  @Test
  void mapNutritionFitnessHealthDataWoAnyNestedEntity() {

    NutritionFitnessHealthDataDTO expected = getDto(null, null);
    NutritionFitnessHealthData entity = getEntity(null, null);

    NutritionFitnessHealthDataDTO actual = nutritionFitnessHealthDataMapper.map(entity);

    verifyNoMoreInteractions(nutritionMacrosMapper);
    verifyNoMoreInteractions(nutritionMicrosMapper);

    assertEquals(expected, actual);
  }

  @Test
  void mapNutritionFitnessHealthDataWoMacros() {

    NutritionFitnessHealthDataDTO expected = getDto(null, microsDTO);
    NutritionFitnessHealthData entity = getEntity(null, micros);

    when(nutritionMicrosMapper.map(any(NutritionMicros.class))).thenReturn(microsDTO);

    NutritionFitnessHealthDataDTO actual = nutritionFitnessHealthDataMapper.map(entity);

    verifyNoInteractions(nutritionMacrosMapper);
    verify(nutritionMicrosMapper).map(any(NutritionMicros.class));

    assertEquals(expected, actual);
  }

  @Test
  void mapNutritionFitnessHealthDataWoMicros() {

    NutritionFitnessHealthDataDTO expected = getDto(macrosDTO, null);
    NutritionFitnessHealthData entity = getEntity(macros, null);

    when(nutritionMacrosMapper.map(any(NutritionMacros.class))).thenReturn(macrosDTO);

    NutritionFitnessHealthDataDTO actual = nutritionFitnessHealthDataMapper.map(entity);

    verify(nutritionMacrosMapper).map(any(NutritionMacros.class));
    verifyNoMoreInteractions(nutritionMicrosMapper);

    assertEquals(expected, actual);
  }

  @Test
  void mapNutritionFitnessHealthData() {

    NutritionFitnessHealthDataDTO expected = getDto(macrosDTO, microsDTO);
    NutritionFitnessHealthData entity = getEntity(macros, micros);

    when(nutritionMacrosMapper.map(any(NutritionMacros.class))).thenReturn(macrosDTO);
    when(nutritionMicrosMapper.map(any(NutritionMicros.class))).thenReturn(microsDTO);

    NutritionFitnessHealthDataDTO actual = nutritionFitnessHealthDataMapper.map(entity);

    verify(nutritionMacrosMapper).map(any(NutritionMacros.class));
    verify(nutritionMicrosMapper).map(any(NutritionMicros.class));

    assertEquals(expected, actual);
  }

  private NutritionFitnessHealthData getEntity(NutritionMacros macros, NutritionMicros micros) {

    NutritionFitnessHealthData entity = new NutritionFitnessHealthData();
    entity.setId(ID);
    entity.setClientId(CLIENT_ID);
    entity.setUserId(USER_ID);
    entity.setDeviceId(DEVICE_ID);
    entity.setProvider(PROVIDER);
    entity.setDataType(DATA_TYPE);
    entity.setCreatedAt(CREATED_AT);
    entity.setMacros(macros);
    entity.setMicros(micros);
    return entity;
  }

  private NutritionFitnessHealthDataDTO getDto(NutritionMacrosDTO macros, NutritionMicrosDTO micros) {

    NutritionFitnessHealthDataDTO dto = new NutritionFitnessHealthDataDTO();
    dto.setId(ID);
    dto.setClientId(CLIENT_ID);
    dto.setUserId(USER_ID);
    dto.setDeviceId(DEVICE_ID);
    dto.setProvider(PROVIDER);
    dto.setDataType(DATA_TYPE);
    dto.setCreatedAt(CREATED_AT);
    dto.setMacros(macros);
    dto.setMicros(micros);
    return dto;
  }
}