package team.dev.sun.fitness.health.handler;

import static team.dev.sun.fitness.health.api.model.Provider.MYFITNESSPAL;
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

import co.tryterra.terraclient.models.v2.sleep.Sleep;
import co.tryterra.terraclient.models.v2.sleep.SleepDurationsData;
import co.tryterra.terraclient.models.v2.sleep.SleepDurationsData.Asleep;
import co.tryterra.terraclient.models.v2.sleep.SleepDurationsData.Awake;
import team.dev.sun.fitness.health.dlq.DeadLetterQueue;
import team.dev.sun.fitness.health.extractor.sleep.AsleepDurationDataExtractor;
import team.dev.sun.fitness.health.extractor.sleep.AwakeDurationDataExtractor;
import team.dev.sun.fitness.health.model.Reference;
import team.dev.sun.fitness.health.model.WebhookPayload;
import team.dev.sun.fitness.health.model.sleep.SleepFitnessHealthData;
import team.dev.sun.fitness.health.parser.WebhookPayloadParser;
import team.dev.sun.fitness.health.persistence.SleepFitnessHealthDataRepository;
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
class SleepHandlerTest {

  private static final Long CLIENT_ID = 42L;

  private static final Long USER_ID = 953L;

  private static final String DEVICE_ID = "iPhone devide id";

  @Mock
  private DeadLetterQueue deadLetterQueue;

  @Mock
  private AwakeDurationDataExtractor awakeDurationDataExtractor;

  @Mock
  private AsleepDurationDataExtractor asleepDurationDataExtractor;

  @Mock
  private SleepFitnessHealthDataRepository repository;

  @Mock
  private WebhookPayloadParser payloadParser;

  @InjectMocks
  private SleepHandler sleepHandler;

  @Mock
  private Sleep sleep;

  @Mock
  private SleepDurationsData sleepDurationsData;

  @Mock
  private Awake awake;

  @Mock
  private Asleep asleep;

  @Mock
  private WebhookPayload payload;

  @Captor
  private ArgumentCaptor<List<SleepFitnessHealthData>> sleepFitnessHealthDataArgumentCaptor;

  @Test
  void sleepListIsEmpty() {

    when(payloadParser.parseAllData(any(), eq(Sleep.class))).thenReturn(emptyList());
    sleepHandler.accept(payload);
    verifyNoInteractions(awakeDurationDataExtractor);
    verifyNoInteractions(asleepDurationDataExtractor);
    verify(repository).saveAll(eq(emptyList()));
  }

  @Test
  void extractDataWoAwake() {

    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Sleep.class))).thenReturn(List.of(sleep));
    when(sleep.getSleepDurationsData()).thenReturn(sleepDurationsData);
    when(sleepDurationsData.getAwake()).thenReturn(null);
    when(sleepDurationsData.getAsleep()).thenReturn(asleep);

    sleepHandler.accept(payload);

    verifyNoInteractions(awakeDurationDataExtractor);
    verify(asleepDurationDataExtractor).extract(any(), any());
    verify(repository).saveAll(anyList());
  }

  @Test
  void extractDataWoAsleep() {

    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Sleep.class))).thenReturn(List.of(sleep));
    when(sleep.getSleepDurationsData()).thenReturn(sleepDurationsData);
    when(sleepDurationsData.getAwake()).thenReturn(awake);
    when(sleepDurationsData.getAsleep()).thenReturn(null);

    sleepHandler.accept(payload);

    verify(awakeDurationDataExtractor).extract(any(), any());
    verifyNoInteractions(asleepDurationDataExtractor);
    verify(repository).saveAll(anyList());
  }

  @Test
  void extractData() {

    ZonedDateTime startTime = ZonedDateTime.now();
    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Sleep.class))).thenReturn(List.of(sleep));
    when(sleep.getSleepDurationsData()).thenReturn(sleepDurationsData);
    when(sleepDurationsData.getAwake()).thenReturn(awake);
    when(sleepDurationsData.getAsleep()).thenReturn(asleep);

    sleepHandler.accept(payload);

    verify(awakeDurationDataExtractor).extract(any(), any());
    verify(asleepDurationDataExtractor).extract(any(), any());
    verify(repository).saveAll(sleepFitnessHealthDataArgumentCaptor.capture());

    List<SleepFitnessHealthData> sleepFitnessHealthDataList = sleepFitnessHealthDataArgumentCaptor.getValue();
    assertNotNull(sleepFitnessHealthDataList);
    assertEquals(1, sleepFitnessHealthDataList.size());
    SleepFitnessHealthData sleepFitnessHealthData = sleepFitnessHealthDataList.get(0);
    assertEquals(CLIENT_ID, sleepFitnessHealthData.getClientId());
    assertEquals(USER_ID, sleepFitnessHealthData.getUserId());
    assertEquals(DEVICE_ID, sleepFitnessHealthData.getDeviceId());
    assertEquals(MYFITNESSPAL, sleepFitnessHealthData.getProvider());
    assertTrue(sleepFitnessHealthData.getCreatedAt().isAfter(startTime));
  }

  private void mockProvider() {

    when(payloadParser.parseProvider(any())).thenReturn(MYFITNESSPAL);
  }

  private void mockReference() {

    when(payloadParser.parseReference(any())).thenReturn(new Reference(CLIENT_ID, USER_ID, DEVICE_ID));
  }
}