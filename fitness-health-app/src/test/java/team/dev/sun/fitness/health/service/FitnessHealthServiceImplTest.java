package team.dev.sun.fitness.health.service;

import static team.dev.sun.fitness.health.api.model.DataType.ACTIVITY;
import static team.dev.sun.fitness.health.api.model.DataType.BODY;
import static team.dev.sun.fitness.health.api.model.DataType.DAILY;
import static team.dev.sun.fitness.health.api.model.DataType.SLEEP;
import static team.dev.sun.fitness.health.api.model.DateRange.LAST_90_DAYS;
import static team.dev.sun.fitness.health.api.model.Provider.GOOGLE;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import team.dev.sun.fitness.health.api.dto.FitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.activity.ActivityFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.body.BodyFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.daily.DailyFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.nutrition.NutritionFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.sleep.SleepFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.model.DataType;
import team.dev.sun.fitness.health.api.model.FitnessHealthSearchFilters;
import team.dev.sun.fitness.health.exception.InvalidSignatureException;
import team.dev.sun.fitness.health.handler.WebhookHandler;
import team.dev.sun.fitness.health.mapper.activity.ActivityFitnessHealthDataMapper;
import team.dev.sun.fitness.health.mapper.body.BodyFitnessHealthDataMapper;
import team.dev.sun.fitness.health.mapper.daily.DailyFitnessHealthDataMapper;
import team.dev.sun.fitness.health.mapper.nutrition.NutritionFitnessHealthDataMapper;
import team.dev.sun.fitness.health.mapper.sleep.SleepFitnessHealthDataMapper;
import team.dev.sun.fitness.health.model.WebhookPayload;
import team.dev.sun.fitness.health.model.activity.ActivityFitnessHealthData;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import team.dev.sun.fitness.health.model.daily.DailyFitnessHealthData;
import team.dev.sun.fitness.health.model.nutrition.NutritionFitnessHealthData;
import team.dev.sun.fitness.health.model.sleep.SleepFitnessHealthData;
import team.dev.sun.fitness.health.parser.WebhookPayloadParser;
import team.dev.sun.fitness.health.persistence.ActivityFitnessHealthDataRepository;
import team.dev.sun.fitness.health.persistence.BodyFitnessHealthDataRepository;
import team.dev.sun.fitness.health.persistence.DailyFitnessHealthDataRepository;
import team.dev.sun.fitness.health.persistence.NutritionFitnessHealthDataRepository;
import team.dev.sun.fitness.health.persistence.SleepFitnessHealthDataRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FitnessHealthServiceImplTest {

  private static final String SIGNATURE_HEADER = "signature from header";

  private static final String PAYLOAD = "some payload";

  @Mock
  private ActivityFitnessHealthDataRepository activityDataRepository;

  @Mock
  private BodyFitnessHealthDataRepository bodyDataRepository;

  @Mock
  private DailyFitnessHealthDataRepository dailyDataRepository;

  @Mock
  private NutritionFitnessHealthDataRepository nutritionDataRepository;

  @Mock
  private SleepFitnessHealthDataRepository sleepDataRepository;

  @Mock
  private WebhookHandler webhookHandler;

  @Mock
  private ActivityFitnessHealthDataMapper activityDataMapper;

  @Mock
  private BodyFitnessHealthDataMapper bodyDataMapper;

  @Mock
  private DailyFitnessHealthDataMapper dailyDataMapper;

  @Mock
  private NutritionFitnessHealthDataMapper nutritionDataMapper;

  @Mock
  private SleepFitnessHealthDataMapper sleepDataMapper;

  @Mock
  private WebhookPayloadParser payloadParser;

  @InjectMocks
  private FitnessHealthServiceImpl fitnessHealthService;

  @Mock
  private ActivityFitnessHealthData activityData;

  @Mock
  private BodyFitnessHealthData bodyData;

  @Mock
  private DailyFitnessHealthData dailyData;

  @Mock
  private NutritionFitnessHealthData nutritionData;

  @Mock
  private SleepFitnessHealthData sleepData;

  @Mock
  private ActivityFitnessHealthDataDTO activityDataDto;

  @Mock
  private BodyFitnessHealthDataDTO bodyDataDto;

  @Mock
  private DailyFitnessHealthDataDTO dailyDataDto;

  @Mock
  private NutritionFitnessHealthDataDTO nutritionDataDto;

  @Mock
  private SleepFitnessHealthDataDTO sleepDataDto;

  @Nested
  class ProcessFitnessHealthData {

    @Test
    void invalidSignature() {

      when(webhookHandler.isSignatureValid(anyString(), anyString())).thenReturn(false);
      InvalidSignatureException exception = assertThrows(
          InvalidSignatureException.class,
          () -> fitnessHealthService.processFitnessHealthData(SIGNATURE_HEADER, PAYLOAD)
      );
      verify(webhookHandler).isSignatureValid(anyString(), anyString());
      verifyNoMoreInteractions(webhookHandler);
      assertEquals("exception.terra.invalid-signature", exception.getMessage());
    }

    @Test
    void succesfullyProcessed() throws JsonProcessingException {

      WebhookPayload webhookPayload = mock(WebhookPayload.class);
      when(webhookHandler.isSignatureValid(anyString(), anyString())).thenReturn(true);
      when(payloadParser.toWebhookPayload(anyString())).thenReturn(webhookPayload);
      fitnessHealthService.processFitnessHealthData(SIGNATURE_HEADER, PAYLOAD);
      verify(webhookHandler).isSignatureValid(anyString(), anyString());
      verify(webhookHandler).submitPayload(any());
    }
  }

  @Nested
  class SearchFitnessHealthData {

    @Test
    void searchOnlyActivity() {

      FitnessHealthDataDTO expected = getDto(true, false, false, false, false);
      FitnessHealthSearchFilters searchFilters = getFilters(Set.of(ACTIVITY));

      when(activityDataRepository.findAll(anyLong(), anyLong(), anyString(), any(), any(), any()))
          .thenReturn(List.of(activityData));

      when(activityDataMapper.map(anyList())).thenReturn(List.of(activityDataDto));

      FitnessHealthDataDTO actual = fitnessHealthService.searchFitnessHealthData(searchFilters);

      verify(activityDataRepository).findAll(anyLong(), anyLong(), anyString(), any(), any(), any());
      verifyNoInteractions(bodyDataRepository);
      verifyNoInteractions(dailyDataRepository);
      verifyNoInteractions(nutritionDataRepository);
      verifyNoInteractions(sleepDataRepository);

      verify(activityDataMapper).map(anyList());
      verifyNoInteractions(bodyDataMapper);
      verifyNoInteractions(dailyDataMapper);
      verifyNoInteractions(nutritionDataMapper);
      verifyNoInteractions(sleepDataMapper);

      assertEquals(expected, actual);
    }

    @Test
    void searchBodyDailySleep() {

      FitnessHealthDataDTO expected = getDto(false, true, true, false, true);
      FitnessHealthSearchFilters searchFilters = getFilters(Set.of(BODY, DAILY, SLEEP));

      when(bodyDataRepository.findAll(anyLong(), anyLong(), anyString(), any(), any(), any()))
          .thenReturn(List.of(bodyData));
      when(dailyDataRepository.findAll(anyLong(), anyLong(), anyString(), any(), any(), any()))
          .thenReturn(List.of(dailyData));
      when(sleepDataRepository.findAll(anyLong(), anyLong(), anyString(), any(), any(), any()))
          .thenReturn(List.of(sleepData));

      when(bodyDataMapper.map(anyList())).thenReturn(List.of(bodyDataDto));
      when(dailyDataMapper.map(anyList())).thenReturn(List.of(dailyDataDto));
      when(sleepDataMapper.map(anyList())).thenReturn(List.of(sleepDataDto));

      FitnessHealthDataDTO actual = fitnessHealthService.searchFitnessHealthData(searchFilters);

      verifyNoInteractions(activityDataRepository);
      verify(bodyDataRepository).findAll(anyLong(), anyLong(), anyString(), any(), any(), any());
      verify(dailyDataRepository).findAll(anyLong(), anyLong(), anyString(), any(), any(), any());
      verifyNoInteractions(nutritionDataRepository);
      verify(sleepDataRepository).findAll(anyLong(), anyLong(), anyString(), any(), any(), any());

      verifyNoInteractions(activityDataMapper);
      verify(bodyDataMapper).map(anyList());
      verify(dailyDataMapper).map(anyList());
      verifyNoInteractions(nutritionDataMapper);
      verify(sleepDataMapper).map(anyList());

      assertEquals(expected, actual);
    }

    @Test
    void searchAllFitnessHealthDataTypes() {

      FitnessHealthDataDTO expected = getDto(true, true, true, true, true);
      FitnessHealthSearchFilters searchFilters = getFilters(Stream.of(DataType.values()).collect(Collectors.toSet()));

      when(activityDataRepository.findAll(anyLong(), anyLong(), anyString(), any(), any(), any()))
          .thenReturn(List.of(activityData));
      when(bodyDataRepository.findAll(anyLong(), anyLong(), anyString(), any(), any(), any()))
          .thenReturn(List.of(bodyData));
      when(dailyDataRepository.findAll(anyLong(), anyLong(), anyString(), any(), any(), any()))
          .thenReturn(List.of(dailyData));
      when(nutritionDataRepository.findAll(anyLong(), anyLong(), anyString(), any(), any(), any()))
          .thenReturn(List.of(nutritionData));
      when(sleepDataRepository.findAll(anyLong(), anyLong(), anyString(), any(), any(), any()))
          .thenReturn(List.of(sleepData));

      when(activityDataMapper.map(anyList())).thenReturn(List.of(activityDataDto));
      when(bodyDataMapper.map(anyList())).thenReturn(List.of(bodyDataDto));
      when(dailyDataMapper.map(anyList())).thenReturn(List.of(dailyDataDto));
      when(nutritionDataMapper.map(anyList())).thenReturn(List.of(nutritionDataDto));
      when(sleepDataMapper.map(anyList())).thenReturn(List.of(sleepDataDto));

      FitnessHealthDataDTO actual = fitnessHealthService.searchFitnessHealthData(searchFilters);

      verify(activityDataRepository).findAll(anyLong(), anyLong(), anyString(), any(), any(), any());
      verify(bodyDataRepository).findAll(anyLong(), anyLong(), anyString(), any(), any(), any());
      verify(dailyDataRepository).findAll(anyLong(), anyLong(), anyString(), any(), any(), any());
      verify(nutritionDataRepository).findAll(anyLong(), anyLong(), anyString(), any(), any(), any());
      verify(sleepDataRepository).findAll(anyLong(), anyLong(), anyString(), any(), any(), any());

      verify(activityDataMapper).map(anyList());
      verify(bodyDataMapper).map(anyList());
      verify(dailyDataMapper).map(anyList());
      verify(nutritionDataMapper).map(anyList());
      verify(sleepDataMapper).map(anyList());

      assertEquals(expected, actual);
    }

    private FitnessHealthSearchFilters getFilters(Set<DataType> dataTypes) {

      FitnessHealthSearchFilters searchFilters = new FitnessHealthSearchFilters();
      searchFilters.setClientId(1L);
      searchFilters.setUserId(2L);
      searchFilters.setDeviceId("some device id");
      searchFilters.setProvider(GOOGLE);
      searchFilters.setDataTypes(dataTypes);
      searchFilters.setDateRange(LAST_90_DAYS);
      return searchFilters;
    }

    private FitnessHealthDataDTO getDto(boolean activityData, boolean dailyData, boolean bodyData,
                                        boolean nutritionData, boolean sleepData) {

      FitnessHealthDataDTO dto = new FitnessHealthDataDTO();
      dto.setActivityData(activityData ? List.of(activityDataDto) : emptyList());
      dto.setDailyData(dailyData ? List.of(dailyDataDto) : emptyList());
      dto.setBodyData(bodyData ? List.of(bodyDataDto) : emptyList());
      dto.setSleepData(sleepData ? List.of(sleepDataDto) : emptyList());
      dto.setNutritionData(nutritionData ? List.of(nutritionDataDto) : emptyList());
      return dto;
    }
  }
}