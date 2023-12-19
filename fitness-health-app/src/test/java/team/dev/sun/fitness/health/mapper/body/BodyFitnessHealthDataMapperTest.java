package team.dev.sun.fitness.health.mapper.body;

import static team.dev.sun.fitness.health.api.model.DataType.BODY;
import static team.dev.sun.fitness.health.api.model.Provider.GARMIN;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import team.dev.sun.fitness.health.api.dto.body.BloodPressureDTO;
import team.dev.sun.fitness.health.api.dto.body.BodyFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.body.GlucoseDTO;
import team.dev.sun.fitness.health.api.dto.body.HydrationDTO;
import team.dev.sun.fitness.health.api.dto.body.MeasurementDTO;
import team.dev.sun.fitness.health.api.dto.body.OxygenDTO;
import team.dev.sun.fitness.health.api.dto.body.TemperatureDTO;
import team.dev.sun.fitness.health.api.model.DataType;
import team.dev.sun.fitness.health.api.model.Provider;
import team.dev.sun.fitness.health.model.body.BloodPressure;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import team.dev.sun.fitness.health.model.body.Glucose;
import team.dev.sun.fitness.health.model.body.Hydration;
import team.dev.sun.fitness.health.model.body.Measurement;
import team.dev.sun.fitness.health.model.body.Oxygen;
import team.dev.sun.fitness.health.model.body.Temperature;
import java.time.ZonedDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BodyFitnessHealthDataMapperTest {

  private static final Long ID = 1L;

  private static final Long CLIENT_ID = 5L;

  private static final Long USER_ID = 9L;

  private static final String DEVICE_ID = "some device id";

  private static final Provider PROVIDER = GARMIN;

  private static final DataType DATA_TYPE = BODY;

  private static final ZonedDateTime CREATED_AT = ZonedDateTime.now();

  @Mock
  BloodPressure bloodPressure;

  @Mock
  Glucose glucose;

  @Mock
  Hydration hydration;

  @Mock
  Measurement measurement;

  @Mock
  Oxygen oxygen;

  @Mock
  Temperature temperature;

  @Mock
  BloodPressureDTO bloodPressureDto;

  @Mock
  GlucoseDTO glucoseDto;

  @Mock
  HydrationDTO hydrationDto;

  @Mock
  MeasurementDTO measurementDto;

  @Mock
  OxygenDTO oxygenDto;

  @Mock
  TemperatureDTO temperatureDto;

  @Mock
  private BloodPressureMapper bloodPressureMapper;

  @Mock
  private GlucoseMapper glucoseMapper;

  @Mock
  private HydrationMapper hydrationMapper;

  @Mock
  private MeasurementMapper measurementMapper;

  @Mock
  private OxygenMapper oxygenMapper;

  @Mock
  private TemperatureMapper temperatureMapper;

  @InjectMocks
  private BodyFitnessHealthDataMapper bodyFitnessHealthDataMapper;

  @Test
  void mapBodyFitnessHealthDataWoAnyNestedEntity() {

    BodyFitnessHealthDataDTO expected =
        getDto(null, null, null, null, null, null);

    BodyFitnessHealthData entity = getEntity(null, null, null, null, null, null);

    when(bloodPressureMapper.map((List<BloodPressure>) isNull())).thenReturn(emptyList());
    when(measurementMapper.map((List<Measurement>) isNull())).thenReturn(emptyList());
    when(temperatureMapper.map((List<Temperature>) isNull())).thenReturn(emptyList());

    BodyFitnessHealthDataDTO actual = bodyFitnessHealthDataMapper.map(entity);

    verify(bloodPressureMapper).map((List<BloodPressure>) isNull());
    verifyNoInteractions(glucoseMapper);
    verifyNoInteractions(hydrationMapper);
    verify(measurementMapper).map((List<Measurement>) isNull());
    verifyNoInteractions(oxygenMapper);
    verify(temperatureMapper).map((List<Temperature>) isNull());

    assertEquals(expected, actual);
  }

  @Test
  void mapBodyFitnessHealthDataWoBloodPressure() {

    BodyFitnessHealthDataDTO expected =
        getDto(null, glucoseDto, hydrationDto, measurementDto, oxygenDto, temperatureDto);

    BodyFitnessHealthData entity = getEntity(null, glucose, hydration, measurement, oxygen, temperature);

    when(bloodPressureMapper.map((List<BloodPressure>) isNull())).thenReturn(emptyList());
    when(glucoseMapper.map(any(Glucose.class))).thenReturn(glucoseDto);
    when(hydrationMapper.map(any(Hydration.class))).thenReturn(hydrationDto);
    when(measurementMapper.map(anyList())).thenReturn(List.of(measurementDto));
    when(oxygenMapper.map(any(Oxygen.class))).thenReturn(oxygenDto);
    when(temperatureMapper.map(anyList())).thenReturn(List.of(temperatureDto));

    BodyFitnessHealthDataDTO actual = bodyFitnessHealthDataMapper.map(entity);

    verify(bloodPressureMapper).map((List<BloodPressure>) isNull());
    verify(glucoseMapper).map(any(Glucose.class));
    verify(hydrationMapper).map(any(Hydration.class));
    verify(measurementMapper).map(anyList());
    verify(oxygenMapper).map(any(Oxygen.class));
    verify(temperatureMapper).map(anyList());

    assertEquals(expected, actual);
  }

  @Test
  void mapBodyFitnessHealthDataWoGlucose() {

    BodyFitnessHealthDataDTO expected =
        getDto(bloodPressureDto, null, hydrationDto, measurementDto, oxygenDto, temperatureDto);

    BodyFitnessHealthData entity = getEntity(bloodPressure, null, hydration, measurement, oxygen, temperature);

    when(bloodPressureMapper.map(anyList())).thenReturn(List.of(bloodPressureDto));
    when(hydrationMapper.map(any(Hydration.class))).thenReturn(hydrationDto);
    when(measurementMapper.map(anyList())).thenReturn(List.of(measurementDto));
    when(oxygenMapper.map(any(Oxygen.class))).thenReturn(oxygenDto);
    when(temperatureMapper.map(anyList())).thenReturn(List.of(temperatureDto));

    BodyFitnessHealthDataDTO actual = bodyFitnessHealthDataMapper.map(entity);

    verify(bloodPressureMapper).map(anyList());
    verifyNoInteractions(glucoseMapper);
    verify(hydrationMapper).map(any(Hydration.class));
    verify(measurementMapper).map(anyList());
    verify(oxygenMapper).map(any(Oxygen.class));
    verify(temperatureMapper).map(anyList());

    assertEquals(expected, actual);
  }

  @Test
  void mapBodyFitnessHealthDataWoHydration() {

    BodyFitnessHealthDataDTO expected =
        getDto(bloodPressureDto, glucoseDto, null, measurementDto, oxygenDto, temperatureDto);

    BodyFitnessHealthData entity = getEntity(bloodPressure, glucose, null, measurement, oxygen, temperature);

    when(bloodPressureMapper.map(anyList())).thenReturn(List.of(bloodPressureDto));
    when(glucoseMapper.map(any(Glucose.class))).thenReturn(glucoseDto);
    when(measurementMapper.map(anyList())).thenReturn(List.of(measurementDto));
    when(oxygenMapper.map(any(Oxygen.class))).thenReturn(oxygenDto);
    when(temperatureMapper.map(anyList())).thenReturn(List.of(temperatureDto));

    BodyFitnessHealthDataDTO actual = bodyFitnessHealthDataMapper.map(entity);

    verify(bloodPressureMapper).map(anyList());
    verify(glucoseMapper).map(any(Glucose.class));
    verifyNoInteractions(hydrationMapper);
    verify(measurementMapper).map(anyList());
    verify(oxygenMapper).map(any(Oxygen.class));
    verify(temperatureMapper).map(anyList());

    assertEquals(expected, actual);
  }

  @Test
  void mapBodyFitnessHealthDataWoMeasurement() {

    BodyFitnessHealthDataDTO expected =
        getDto(bloodPressureDto, glucoseDto, hydrationDto, null, oxygenDto, temperatureDto);

    BodyFitnessHealthData entity = getEntity(bloodPressure, glucose, hydration, null, oxygen, temperature);

    when(bloodPressureMapper.map(anyList())).thenReturn(List.of(bloodPressureDto));
    when(glucoseMapper.map(any(Glucose.class))).thenReturn(glucoseDto);
    when(hydrationMapper.map(any(Hydration.class))).thenReturn(hydrationDto);
    when(measurementMapper.map((List<Measurement>) isNull())).thenReturn(emptyList());
    when(oxygenMapper.map(any(Oxygen.class))).thenReturn(oxygenDto);
    when(temperatureMapper.map(anyList())).thenReturn(List.of(temperatureDto));

    BodyFitnessHealthDataDTO actual = bodyFitnessHealthDataMapper.map(entity);

    verify(bloodPressureMapper).map(anyList());
    verify(glucoseMapper).map(any(Glucose.class));
    verify(hydrationMapper).map(any(Hydration.class));
    verify(measurementMapper).map((List<Measurement>) isNull());
    verify(oxygenMapper).map(any(Oxygen.class));
    verify(temperatureMapper).map(anyList());

    assertEquals(expected, actual);
  }

  @Test
  void mapBodyFitnessHealthDataWoOxygen() {

    BodyFitnessHealthDataDTO expected =
        getDto(bloodPressureDto, glucoseDto, hydrationDto, measurementDto, null, temperatureDto);

    BodyFitnessHealthData entity = getEntity(bloodPressure, glucose, hydration, measurement, null, temperature);

    when(bloodPressureMapper.map(anyList())).thenReturn(List.of(bloodPressureDto));
    when(glucoseMapper.map(any(Glucose.class))).thenReturn(glucoseDto);
    when(hydrationMapper.map(any(Hydration.class))).thenReturn(hydrationDto);
    when(measurementMapper.map(anyList())).thenReturn(List.of(measurementDto));
    when(temperatureMapper.map(anyList())).thenReturn(List.of(temperatureDto));

    BodyFitnessHealthDataDTO actual = bodyFitnessHealthDataMapper.map(entity);

    verify(bloodPressureMapper).map(anyList());
    verify(glucoseMapper).map(any(Glucose.class));
    verify(hydrationMapper).map(any(Hydration.class));
    verify(measurementMapper).map(anyList());
    verifyNoInteractions(oxygenMapper);
    verify(temperatureMapper).map(anyList());

    assertEquals(expected, actual);
  }

  @Test
  void mapBodyFitnessHealthDataWoTemperature() {

    BodyFitnessHealthDataDTO expected =
        getDto(bloodPressureDto, glucoseDto, hydrationDto, measurementDto, oxygenDto, null);

    BodyFitnessHealthData entity = getEntity(bloodPressure, glucose, hydration, measurement, oxygen, null);

    when(bloodPressureMapper.map(anyList())).thenReturn(List.of(bloodPressureDto));
    when(glucoseMapper.map(any(Glucose.class))).thenReturn(glucoseDto);
    when(hydrationMapper.map(any(Hydration.class))).thenReturn(hydrationDto);
    when(measurementMapper.map(anyList())).thenReturn(List.of(measurementDto));
    when(oxygenMapper.map(any(Oxygen.class))).thenReturn(oxygenDto);
    when(temperatureMapper.map((List<Temperature>) isNull())).thenReturn(emptyList());

    BodyFitnessHealthDataDTO actual = bodyFitnessHealthDataMapper.map(entity);

    verify(bloodPressureMapper).map(anyList());
    verify(glucoseMapper).map(any(Glucose.class));
    verify(hydrationMapper).map(any(Hydration.class));
    verify(measurementMapper).map(anyList());
    verify(oxygenMapper).map(any(Oxygen.class));
    verify(temperatureMapper).map((List<Temperature>) isNull());

    assertEquals(expected, actual);
  }

  @Test
  void mapBodyFitnessHealthData() {

    BodyFitnessHealthDataDTO expected =
        getDto(bloodPressureDto, glucoseDto, hydrationDto, measurementDto, oxygenDto, temperatureDto);

    BodyFitnessHealthData entity = getEntity(bloodPressure, glucose, hydration, measurement, oxygen, temperature);

    when(bloodPressureMapper.map(anyList())).thenReturn(List.of(bloodPressureDto));
    when(glucoseMapper.map(any(Glucose.class))).thenReturn(glucoseDto);
    when(hydrationMapper.map(any(Hydration.class))).thenReturn(hydrationDto);
    when(measurementMapper.map(anyList())).thenReturn(List.of(measurementDto));
    when(oxygenMapper.map(any(Oxygen.class))).thenReturn(oxygenDto);
    when(temperatureMapper.map(anyList())).thenReturn(List.of(temperatureDto));

    BodyFitnessHealthDataDTO actual = bodyFitnessHealthDataMapper.map(entity);

    verify(bloodPressureMapper).map(anyList());
    verify(glucoseMapper).map(any(Glucose.class));
    verify(hydrationMapper).map(any(Hydration.class));
    verify(measurementMapper).map(anyList());
    verify(oxygenMapper).map(any(Oxygen.class));
    verify(temperatureMapper).map(anyList());

    assertEquals(expected, actual);
  }

  private BodyFitnessHealthData getEntity(BloodPressure bloodPressure, Glucose glucose, Hydration hydration,
                                          Measurement measurement, Oxygen oxygen, Temperature temperature) {

    BodyFitnessHealthData entity = new BodyFitnessHealthData();
    entity.setId(ID);
    entity.setClientId(CLIENT_ID);
    entity.setUserId(USER_ID);
    entity.setDeviceId(DEVICE_ID);
    entity.setProvider(PROVIDER);
    entity.setDataType(DATA_TYPE);
    entity.setCreatedAt(CREATED_AT);
    entity.setGlucose(glucose);
    entity.setHydration(hydration);
    entity.setOxygen(oxygen);
    entity.setBloodPressures(bloodPressure == null ? null : List.of(bloodPressure));
    entity.setMeasurements(measurement == null ? null : List.of(measurement));
    entity.setTemperatures(temperature == null ? null : List.of(temperature));
    return entity;
  }

  private BodyFitnessHealthDataDTO getDto(BloodPressureDTO bloodPressure, GlucoseDTO glucose, HydrationDTO hydration,
                                          MeasurementDTO measurement, OxygenDTO oxygen, TemperatureDTO temperature) {

    BodyFitnessHealthDataDTO dto = new BodyFitnessHealthDataDTO();
    dto.setId(ID);
    dto.setClientId(CLIENT_ID);
    dto.setUserId(USER_ID);
    dto.setDeviceId(DEVICE_ID);
    dto.setProvider(PROVIDER);
    dto.setDataType(DATA_TYPE);
    dto.setCreatedAt(CREATED_AT);
    dto.setGlucose(glucose);
    dto.setHydration(hydration);
    dto.setOxygen(oxygen);
    dto.setBloodPressures(bloodPressure == null ? emptyList() : List.of(bloodPressure));
    dto.setMeasurements(measurement == null ? emptyList() : List.of(measurement));
    dto.setTemperatures(temperature == null ? emptyList() : List.of(temperature));
    return dto;
  }
}