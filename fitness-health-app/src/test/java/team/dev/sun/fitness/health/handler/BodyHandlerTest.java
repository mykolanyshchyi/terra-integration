package team.dev.sun.fitness.health.handler;

import static team.dev.sun.fitness.health.api.model.Provider.GARMIN;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import co.tryterra.terraclient.models.v2.body.BloodPressureData;
import co.tryterra.terraclient.models.v2.body.Body;
import co.tryterra.terraclient.models.v2.body.GlucoseData;
import co.tryterra.terraclient.models.v2.body.HydrationData;
import co.tryterra.terraclient.models.v2.body.MeasurementsData;
import co.tryterra.terraclient.models.v2.body.TemperatureData;
import co.tryterra.terraclient.models.v2.common.OxygenData;
import team.dev.sun.fitness.health.dlq.DeadLetterQueue;
import team.dev.sun.fitness.health.extractor.body.BloodPressureDataExtractor;
import team.dev.sun.fitness.health.extractor.body.GlucoseDataExtractor;
import team.dev.sun.fitness.health.extractor.body.HydrationDataExtractor;
import team.dev.sun.fitness.health.extractor.body.MeasurementDataExtractor;
import team.dev.sun.fitness.health.extractor.body.OxygenDataExtractor;
import team.dev.sun.fitness.health.extractor.body.TemperatureDataExtractor;
import team.dev.sun.fitness.health.model.Reference;
import team.dev.sun.fitness.health.model.WebhookPayload;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import team.dev.sun.fitness.health.parser.WebhookPayloadParser;
import team.dev.sun.fitness.health.persistence.BodyFitnessHealthDataRepository;
import java.time.ZonedDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BodyHandlerTest {

  private static final Long CLIENT_ID = 3L;

  private static final Long USER_ID = 5L;

  private static final String DEVICE_ID = "iPhone devide id";

  @Mock
  private DeadLetterQueue deadLetterQueue;

  @Mock
  private BloodPressureDataExtractor bloodPressureDataExtractor;

  @Mock
  private GlucoseDataExtractor glucoseDataExtractor;

  @Mock
  private HydrationDataExtractor hydrationDataExtractor;

  @Mock
  private MeasurementDataExtractor measurementDataExtractor;

  @Mock
  private OxygenDataExtractor oxygenDataExtractor;

  @Mock
  private TemperatureDataExtractor temperatureDataExtractor;

  @Mock
  private BodyFitnessHealthDataRepository repository;

  @Mock
  private WebhookPayloadParser payloadParser;

  @InjectMocks
  private BodyHandler bodyHandler;

  @Mock
  private WebhookPayload payload;

  @Mock
  private Body body;

  @Mock
  private BloodPressureData bloodPressureData;

  @Mock
  private GlucoseData glucoseData;

  @Mock
  private HydrationData hydrationData;

  @Mock
  private MeasurementsData measurementsData;

  @Mock
  private OxygenData oxygenData;

  @Mock
  private TemperatureData temperatureData;

  @Captor
  private ArgumentCaptor<List<BodyFitnessHealthData>> bodyFitnessHealthDataArgumentCaptor;

  @Test
  void bodyListIsEmpty() {

    when(payloadParser.parseAllData(any(), eq(Body.class))).thenReturn(emptyList());
    bodyHandler.accept(payload);
    verifyNoInteractions(bloodPressureDataExtractor);
    verifyNoInteractions(glucoseDataExtractor);
    verifyNoInteractions(hydrationDataExtractor);
    verifyNoInteractions(measurementDataExtractor);
    verifyNoInteractions(oxygenDataExtractor);
    verifyNoInteractions(temperatureDataExtractor);
    verify(repository).saveAll(eq(emptyList()));
  }

  @Test
  void extractDataWoBloodPressureData() {

    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Body.class))).thenReturn(List.of(body));
    when(body.getBloodPressureData()).thenReturn(null);
    when(body.getGlucoseData()).thenReturn(glucoseData);
    when(body.getHydrationData()).thenReturn(hydrationData);
    when(body.getMeasurementsData()).thenReturn(measurementsData);
    when(body.getOxygenData()).thenReturn(oxygenData);
    when(body.getTemperatureData()).thenReturn(temperatureData);

    bodyHandler.accept(payload);
    verifyNoInteractions(bloodPressureDataExtractor);
    verify(glucoseDataExtractor).extract(any(), any());
    verify(hydrationDataExtractor).extract(any(), any());
    verify(measurementDataExtractor).extract(any(), any());
    verify(oxygenDataExtractor).extract(any(), any());
    verify(temperatureDataExtractor).extract(any(), any());
    verify(repository).saveAll(anyList());
  }

  @Test
  void extractDataWoGlucoseData() {

    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Body.class))).thenReturn(List.of(body));
    when(body.getBloodPressureData()).thenReturn(bloodPressureData);
    when(body.getGlucoseData()).thenReturn(null);
    when(body.getHydrationData()).thenReturn(hydrationData);
    when(body.getMeasurementsData()).thenReturn(measurementsData);
    when(body.getOxygenData()).thenReturn(oxygenData);
    when(body.getTemperatureData()).thenReturn(temperatureData);

    bodyHandler.accept(payload);
    verify(bloodPressureDataExtractor).extract(any(), any());
    verifyNoInteractions(glucoseDataExtractor);
    verify(hydrationDataExtractor).extract(any(), any());
    verify(measurementDataExtractor).extract(any(), any());
    verify(oxygenDataExtractor).extract(any(), any());
    verify(temperatureDataExtractor).extract(any(), any());
    verify(repository).saveAll(anyList());
  }

  @Test
  void extractDataWoHydrationData() {

    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Body.class))).thenReturn(List.of(body));
    when(body.getBloodPressureData()).thenReturn(bloodPressureData);
    when(body.getGlucoseData()).thenReturn(glucoseData);
    when(body.getHydrationData()).thenReturn(null);
    when(body.getMeasurementsData()).thenReturn(measurementsData);
    when(body.getOxygenData()).thenReturn(oxygenData);
    when(body.getTemperatureData()).thenReturn(temperatureData);

    bodyHandler.accept(payload);
    verify(bloodPressureDataExtractor).extract(any(), any());
    verify(glucoseDataExtractor).extract(any(), any());
    verifyNoInteractions(hydrationDataExtractor);
    verify(measurementDataExtractor).extract(any(), any());
    verify(oxygenDataExtractor).extract(any(), any());
    verify(temperatureDataExtractor).extract(any(), any());
    verify(repository).saveAll(anyList());
  }

  @Test
  void extractDataWoMeasurementsData() {

    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Body.class))).thenReturn(List.of(body));
    when(body.getBloodPressureData()).thenReturn(bloodPressureData);
    when(body.getGlucoseData()).thenReturn(glucoseData);
    when(body.getHydrationData()).thenReturn(hydrationData);
    when(body.getMeasurementsData()).thenReturn(null);
    when(body.getOxygenData()).thenReturn(oxygenData);
    when(body.getTemperatureData()).thenReturn(temperatureData);

    bodyHandler.accept(payload);
    verify(bloodPressureDataExtractor).extract(any(), any());
    verify(glucoseDataExtractor).extract(any(), any());
    verify(hydrationDataExtractor).extract(any(), any());
    verifyNoInteractions(measurementDataExtractor);
    verify(oxygenDataExtractor).extract(any(), any());
    verify(temperatureDataExtractor).extract(any(), any());
    verify(repository).saveAll(anyList());
  }

  @Test
  void extractDataWoOxygenData() {

    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Body.class))).thenReturn(List.of(body));
    when(body.getBloodPressureData()).thenReturn(bloodPressureData);
    when(body.getGlucoseData()).thenReturn(glucoseData);
    when(body.getHydrationData()).thenReturn(hydrationData);
    when(body.getMeasurementsData()).thenReturn(measurementsData);
    when(body.getOxygenData()).thenReturn(null);
    when(body.getTemperatureData()).thenReturn(temperatureData);

    bodyHandler.accept(payload);
    verify(bloodPressureDataExtractor).extract(any(), any());
    verify(glucoseDataExtractor).extract(any(), any());
    verify(hydrationDataExtractor).extract(any(), any());
    verify(measurementDataExtractor).extract(any(), any());
    verifyNoInteractions(oxygenDataExtractor);
    verify(temperatureDataExtractor).extract(any(), any());
    verify(repository).saveAll(anyList());
  }

  @Test
  void extractDataWoTemperatureData() {

    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Body.class))).thenReturn(List.of(body));
    when(body.getBloodPressureData()).thenReturn(bloodPressureData);
    when(body.getGlucoseData()).thenReturn(glucoseData);
    when(body.getHydrationData()).thenReturn(hydrationData);
    when(body.getMeasurementsData()).thenReturn(measurementsData);
    when(body.getOxygenData()).thenReturn(oxygenData);
    when(body.getTemperatureData()).thenReturn(null);

    bodyHandler.accept(payload);
    verify(bloodPressureDataExtractor).extract(any(), any());
    verify(glucoseDataExtractor).extract(any(), any());
    verify(hydrationDataExtractor).extract(any(), any());
    verify(measurementDataExtractor).extract(any(), any());
    verify(oxygenDataExtractor).extract(any(), any());
    verifyNoInteractions(temperatureDataExtractor);
    verify(repository).saveAll(anyList());
  }

  @Test
  void extractData() {

    ZonedDateTime startTime = ZonedDateTime.now();
    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Body.class))).thenReturn(List.of(body));
    when(body.getBloodPressureData()).thenReturn(bloodPressureData);
    when(body.getGlucoseData()).thenReturn(glucoseData);
    when(body.getHydrationData()).thenReturn(hydrationData);
    when(body.getMeasurementsData()).thenReturn(measurementsData);
    when(body.getOxygenData()).thenReturn(oxygenData);
    when(body.getTemperatureData()).thenReturn(temperatureData);

    bodyHandler.accept(payload);
    verify(bloodPressureDataExtractor).extract(any(), any());
    verify(glucoseDataExtractor).extract(any(), any());
    verify(hydrationDataExtractor).extract(any(), any());
    verify(measurementDataExtractor).extract(any(), any());
    verify(oxygenDataExtractor).extract(any(), any());
    verify(temperatureDataExtractor).extract(any(), any());
    verify(repository).saveAll(bodyFitnessHealthDataArgumentCaptor.capture());

    List<BodyFitnessHealthData> bodyFitnessHealthDataList = bodyFitnessHealthDataArgumentCaptor.getValue();
    assertNotNull(bodyFitnessHealthDataList);
    assertEquals(1, bodyFitnessHealthDataList.size());
    BodyFitnessHealthData bodyFitnessHealthData = bodyFitnessHealthDataList.get(0);
    assertEquals(CLIENT_ID, bodyFitnessHealthData.getClientId());
    assertEquals(USER_ID, bodyFitnessHealthData.getUserId());
    assertEquals(DEVICE_ID, bodyFitnessHealthData.getDeviceId());
    assertEquals(GARMIN, bodyFitnessHealthData.getProvider());
    assertTrue(bodyFitnessHealthData.getCreatedAt().isAfter(startTime));
  }

  private void mockProvider() {

    when(payloadParser.parseProvider(any())).thenReturn(GARMIN);
  }

  private void mockReference() {

    when(payloadParser.parseReference(any())).thenReturn(new Reference(CLIENT_ID, USER_ID, DEVICE_ID));
  }
}