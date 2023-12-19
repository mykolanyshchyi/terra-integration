package team.dev.sun.fitness.health.handler;

import static team.dev.sun.fitness.health.api.model.Provider.DEXCOM;
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

import co.tryterra.terraclient.models.v2.common.HeartRateData;
import co.tryterra.terraclient.models.v2.daily.Daily;
import co.tryterra.terraclient.models.v2.daily.ScoresData;
import co.tryterra.terraclient.models.v2.daily.StressData;
import team.dev.sun.fitness.health.dlq.DeadLetterQueue;
import team.dev.sun.fitness.health.extractor.daily.HeartRateDataExtractor;
import team.dev.sun.fitness.health.extractor.daily.ScoreDataExtractor;
import team.dev.sun.fitness.health.extractor.daily.StressDataExtractor;
import team.dev.sun.fitness.health.model.Reference;
import team.dev.sun.fitness.health.model.WebhookPayload;
import team.dev.sun.fitness.health.model.daily.DailyFitnessHealthData;
import team.dev.sun.fitness.health.parser.WebhookPayloadParser;
import team.dev.sun.fitness.health.persistence.DailyFitnessHealthDataRepository;
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
class DailyHandlerTest {

  private static final Long CLIENT_ID = 50L;

  private static final Long USER_ID = 49593L;

  private static final String DEVICE_ID = "iPhone devide id";

  @Mock
  private DeadLetterQueue deadLetterQueue;

  @Mock
  private HeartRateDataExtractor heartRateDataExtractor;

  @Mock
  private ScoreDataExtractor scoreDataExtractor;

  @Mock
  private StressDataExtractor stressDataExtractor;

  @Mock
  private DailyFitnessHealthDataRepository repository;

  @Mock
  private WebhookPayloadParser payloadParser;

  @InjectMocks
  private DailyHandler dailyHandler;

  @Mock
  private WebhookPayload payload;

  @Mock
  private Daily daily;

  @Mock
  private HeartRateData heartRateData;

  @Mock
  private ScoresData scoresData;

  @Mock
  private StressData stressData;

  @Captor
  private ArgumentCaptor<List<DailyFitnessHealthData>> dailyFitnessHealthDataArgumentCaptor;

  @Test
  void dailyListIsEmpty() {

    when(payloadParser.parseAllData(any(), eq(Daily.class))).thenReturn(emptyList());
    dailyHandler.accept(payload);
    verifyNoInteractions(heartRateDataExtractor);
    verifyNoInteractions(scoreDataExtractor);
    verifyNoInteractions(stressDataExtractor);
    verify(repository).saveAll(eq(emptyList()));
  }

  @Test
  void extractDataWoHeartRateData() {

    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Daily.class))).thenReturn(List.of(daily));
    when(daily.getHeartRateData()).thenReturn(null);
    when(daily.getScores()).thenReturn(scoresData);
    when(daily.getStressData()).thenReturn(stressData);

    dailyHandler.accept(payload);

    verifyNoInteractions(heartRateDataExtractor);
    verify(scoreDataExtractor).extract(any(), any());
    verify(stressDataExtractor).extract(any(), any());
    verify(repository).saveAll(anyList());
  }

  @Test
  void extractDataWoScoresData() {

    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Daily.class))).thenReturn(List.of(daily));
    when(daily.getHeartRateData()).thenReturn(heartRateData);
    when(daily.getScores()).thenReturn(null);
    when(daily.getStressData()).thenReturn(stressData);

    dailyHandler.accept(payload);

    verify(heartRateDataExtractor).extract(any(), any());
    verifyNoInteractions(scoreDataExtractor);
    verify(stressDataExtractor).extract(any(), any());
    verify(repository).saveAll(anyList());
  }

  @Test
  void extractDataWoStressData() {

    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Daily.class))).thenReturn(List.of(daily));
    when(daily.getHeartRateData()).thenReturn(heartRateData);
    when(daily.getScores()).thenReturn(scoresData);
    when(daily.getStressData()).thenReturn(null);

    dailyHandler.accept(payload);

    verify(heartRateDataExtractor).extract(any(), any());
    verify(scoreDataExtractor).extract(any(), any());
    verifyNoInteractions(stressDataExtractor);
    verify(repository).saveAll(anyList());
  }

  @Test
  void extractData() {

    ZonedDateTime startTime = ZonedDateTime.now();
    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Daily.class))).thenReturn(List.of(daily));
    when(daily.getHeartRateData()).thenReturn(heartRateData);
    when(daily.getScores()).thenReturn(scoresData);
    when(daily.getStressData()).thenReturn(stressData);

    dailyHandler.accept(payload);

    verify(heartRateDataExtractor).extract(any(), any());
    verify(scoreDataExtractor).extract(any(), any());
    verify(stressDataExtractor).extract(any(), any());
    verify(repository).saveAll(dailyFitnessHealthDataArgumentCaptor.capture());

    List<DailyFitnessHealthData> dailyFitnessHealthDataList = dailyFitnessHealthDataArgumentCaptor.getValue();
    assertNotNull(dailyFitnessHealthDataList);
    assertEquals(1, dailyFitnessHealthDataList.size());
    DailyFitnessHealthData dailyFitnessHealthData = dailyFitnessHealthDataList.get(0);
    assertEquals(CLIENT_ID, dailyFitnessHealthData.getClientId());
    assertEquals(USER_ID, dailyFitnessHealthData.getUserId());
    assertEquals(DEVICE_ID, dailyFitnessHealthData.getDeviceId());
    assertEquals(DEXCOM, dailyFitnessHealthData.getProvider());
    assertTrue(dailyFitnessHealthData.getCreatedAt().isAfter(startTime));
  }

  private void mockProvider() {

    when(payloadParser.parseProvider(any())).thenReturn(DEXCOM);
  }

  private void mockReference() {

    when(payloadParser.parseReference(any())).thenReturn(new Reference(CLIENT_ID, USER_ID, DEVICE_ID));
  }
}