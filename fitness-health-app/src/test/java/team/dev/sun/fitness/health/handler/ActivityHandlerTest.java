package team.dev.sun.fitness.health.handler;

import static team.dev.sun.fitness.health.api.model.Provider.FITBIT;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import co.tryterra.terraclient.models.v2.activity.ActiveDurationsData;
import co.tryterra.terraclient.models.v2.activity.Activity;
import co.tryterra.terraclient.models.v2.common.CaloriesData;
import co.tryterra.terraclient.models.v2.common.DistanceData;
import team.dev.sun.fitness.health.dlq.DeadLetterQueue;
import team.dev.sun.fitness.health.extractor.activity.ActiveDurationDataExtractor;
import team.dev.sun.fitness.health.extractor.activity.CaloriesDataExtractor;
import team.dev.sun.fitness.health.extractor.activity.DistanceDataExtractor;
import team.dev.sun.fitness.health.model.Reference;
import team.dev.sun.fitness.health.model.WebhookPayload;
import team.dev.sun.fitness.health.model.activity.ActivityFitnessHealthData;
import team.dev.sun.fitness.health.parser.WebhookPayloadParser;
import team.dev.sun.fitness.health.persistence.ActivityFitnessHealthDataRepository;
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
class ActivityHandlerTest {

  private static final Long CLIENT_ID = 1L;

  private static final Long USER_ID = 99L;

  private static final String DEVICE_ID = "iPhone devide id";

  @Mock
  private DeadLetterQueue deadLetterQueue;

  @Mock
  private ActiveDurationDataExtractor activeDurationDataExtractor;

  @Mock
  private DistanceDataExtractor distanceDataExtractor;

  @Mock
  private CaloriesDataExtractor caloriesDataExtractor;

  @Mock
  private ActivityFitnessHealthDataRepository repository;

  @Mock
  private WebhookPayloadParser payloadParser;

  @InjectMocks
  private ActivityHandler activityHandler;

  @Mock
  private WebhookPayload payload;

  @Mock
  private Activity activity;

  @Mock
  private ActiveDurationsData activeDurationsData;

  @Mock
  private CaloriesData caloriesData;

  @Mock
  private DistanceData distanceData;

  @Captor
  private ArgumentCaptor<List<ActivityFitnessHealthData>> activityFitnessHealthDataArgumentCaptor;

  @Test
  void activityListIsEmpty() {

    when(payloadParser.parseAllData(any(), eq(Activity.class))).thenReturn(emptyList());
    activityHandler.accept(payload);
    verifyNoInteractions(distanceDataExtractor);
    verifyNoInteractions(caloriesDataExtractor);
    verifyNoInteractions(activeDurationDataExtractor);
    verify(repository).saveAll(eq(emptyList()));
  }

  @Test
  void extractDataWoActiveDuration() {

    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Activity.class))).thenReturn(List.of(activity));
    when(activity.getActiveDurationsData()).thenReturn(null);
    when(activity.getCaloriesData()).thenReturn(caloriesData);
    when(activity.getDistanceData()).thenReturn(distanceData);
    doNothing().when(caloriesDataExtractor).extract(any(), any());
    doNothing().when(distanceDataExtractor).extract(any(), any());

    activityHandler.accept(payload);

    verifyNoInteractions(activeDurationDataExtractor);
    verify(caloriesDataExtractor).extract(any(), any());
    verify(distanceDataExtractor).extract(any(), any());
    verify(repository).saveAll(anyList());
  }

  @Test
  void extractDataWoDistance() {

    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Activity.class))).thenReturn(List.of(activity));
    when(activity.getActiveDurationsData()).thenReturn(activeDurationsData);
    when(activity.getCaloriesData()).thenReturn(caloriesData);
    when(activity.getDistanceData()).thenReturn(null);
    doNothing().when(caloriesDataExtractor).extract(any(), any());
    doNothing().when(activeDurationDataExtractor).extract(any(), any());

    activityHandler.accept(payload);

    verifyNoInteractions(distanceDataExtractor);
    verify(caloriesDataExtractor).extract(any(), any());
    verify(activeDurationDataExtractor).extract(any(), any());
    verify(repository).saveAll(anyList());
  }

  @Test
  void extractDataWoCalories() {

    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Activity.class))).thenReturn(List.of(activity));
    when(activity.getActiveDurationsData()).thenReturn(activeDurationsData);
    when(activity.getCaloriesData()).thenReturn(null);
    when(activity.getDistanceData()).thenReturn(distanceData);
    doNothing().when(activeDurationDataExtractor).extract(any(), any());
    doNothing().when(distanceDataExtractor).extract(any(), any());

    activityHandler.accept(payload);

    verifyNoInteractions(caloriesDataExtractor);
    verify(activeDurationDataExtractor).extract(any(), any());
    verify(distanceDataExtractor).extract(any(), any());
    verify(repository).saveAll(anyList());
  }

  @Test
  void extractData() {

    ZonedDateTime startTime = ZonedDateTime.now();
    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Activity.class))).thenReturn(List.of(activity));
    when(activity.getActiveDurationsData()).thenReturn(activeDurationsData);
    when(activity.getCaloriesData()).thenReturn(caloriesData);
    when(activity.getDistanceData()).thenReturn(distanceData);
    doNothing().when(activeDurationDataExtractor).extract(any(), any());
    doNothing().when(caloriesDataExtractor).extract(any(), any());
    doNothing().when(distanceDataExtractor).extract(any(), any());

    activityHandler.accept(payload);

    verify(activeDurationDataExtractor).extract(any(), any());
    verify(caloriesDataExtractor).extract(any(), any());
    verify(distanceDataExtractor).extract(any(), any());
    verify(repository).saveAll(activityFitnessHealthDataArgumentCaptor.capture());

    List<ActivityFitnessHealthData> activityFitnessHealthDataList = activityFitnessHealthDataArgumentCaptor.getValue();
    assertNotNull(activityFitnessHealthDataList);
    assertEquals(1, activityFitnessHealthDataList.size());
    ActivityFitnessHealthData activityFitnessHealthData = activityFitnessHealthDataList.get(0);
    assertEquals(CLIENT_ID, activityFitnessHealthData.getClientId());
    assertEquals(USER_ID, activityFitnessHealthData.getUserId());
    assertEquals(DEVICE_ID, activityFitnessHealthData.getDeviceId());
    assertEquals(FITBIT, activityFitnessHealthData.getProvider());
    assertTrue(activityFitnessHealthData.getCreatedAt().isAfter(startTime));
  }

  private void mockProvider() {

    when(payloadParser.parseProvider(any())).thenReturn(FITBIT);
  }

  private void mockReference() {

    when(payloadParser.parseReference(any())).thenReturn(new Reference(CLIENT_ID, USER_ID, DEVICE_ID));
  }
}