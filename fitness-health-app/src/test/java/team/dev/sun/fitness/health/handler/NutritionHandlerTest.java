package team.dev.sun.fitness.health.handler;

import static team.dev.sun.fitness.health.api.model.Provider.VIRTUAGYM;
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

import co.tryterra.terraclient.models.v2.common.Macros;
import co.tryterra.terraclient.models.v2.common.Micros;
import co.tryterra.terraclient.models.v2.nutrition.Nutrition;
import co.tryterra.terraclient.models.v2.nutrition.Nutrition.Summary;
import team.dev.sun.fitness.health.dlq.DeadLetterQueue;
import team.dev.sun.fitness.health.extractor.nutrition.NutritionMacrosDataExtractor;
import team.dev.sun.fitness.health.extractor.nutrition.NutritionMicrosDataExtractor;
import team.dev.sun.fitness.health.model.Reference;
import team.dev.sun.fitness.health.model.WebhookPayload;
import team.dev.sun.fitness.health.model.nutrition.NutritionFitnessHealthData;
import team.dev.sun.fitness.health.parser.WebhookPayloadParser;
import team.dev.sun.fitness.health.persistence.NutritionFitnessHealthDataRepository;
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
class NutritionHandlerTest {

  private static final Long CLIENT_ID = 13L;

  private static final Long USER_ID = 45L;

  private static final String DEVICE_ID = "iPhone devide id";

  private static final Double WHATER_ML = 2.3d;

  @Mock
  private DeadLetterQueue deadLetterQueue;

  @Mock
  private NutritionMacrosDataExtractor nutritionMacrosDataExtractor;

  @Mock
  private NutritionMicrosDataExtractor nutritionMicrosDataExtractor;

  @Mock
  private NutritionFitnessHealthDataRepository repository;

  @Mock
  private WebhookPayloadParser payloadParser;

  @InjectMocks
  private NutritionHandler nutritionHandler;

  @Mock
  private WebhookPayload payload;

  @Mock
  private Nutrition nutrition;

  @Mock
  private Summary summary;

  @Mock
  private Macros macros;

  @Mock
  private Micros micros;

  @Captor
  private ArgumentCaptor<List<NutritionFitnessHealthData>> nutritionFitnessHealthDataArgumentCaptor;

  @Test
  void nutritionListIsEmpty() {

    when(payloadParser.parseAllData(any(), eq(Nutrition.class))).thenReturn(emptyList());
    nutritionHandler.accept(payload);
    verifyNoInteractions(nutritionMacrosDataExtractor);
    verifyNoInteractions(nutritionMicrosDataExtractor);
    verify(repository).saveAll(eq(emptyList()));
  }

  @Test
  void extractDataWoMacros() {

    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Nutrition.class))).thenReturn(List.of(nutrition));
    when(nutrition.getSummary()).thenReturn(summary);
    when(summary.getMacros()).thenReturn(null);
    when(summary.getMicros()).thenReturn(micros);
    when(summary.getWaterMl()).thenReturn(WHATER_ML);

    nutritionHandler.accept(payload);

    verifyNoInteractions(nutritionMacrosDataExtractor);
    verify(nutritionMicrosDataExtractor).extract(any(), any());
    verify(repository).saveAll(anyList());
  }

  @Test
  void extractDataWoMicros() {

    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Nutrition.class))).thenReturn(List.of(nutrition));
    when(nutrition.getSummary()).thenReturn(summary);
    when(summary.getMacros()).thenReturn(macros);
    when(summary.getMicros()).thenReturn(null);
    when(summary.getWaterMl()).thenReturn(WHATER_ML);

    nutritionHandler.accept(payload);

    verify(nutritionMacrosDataExtractor).extract(any(), any());
    verifyNoInteractions(nutritionMicrosDataExtractor);
    verify(repository).saveAll(anyList());
  }

  @Test
  void extractData() {

    ZonedDateTime startTime = ZonedDateTime.now();
    mockProvider();
    mockReference();
    when(payloadParser.parseAllData(any(), eq(Nutrition.class))).thenReturn(List.of(nutrition));
    when(nutrition.getSummary()).thenReturn(summary);
    when(summary.getMacros()).thenReturn(macros);
    when(summary.getMicros()).thenReturn(micros);
    when(summary.getWaterMl()).thenReturn(WHATER_ML);

    nutritionHandler.accept(payload);

    verify(nutritionMacrosDataExtractor).extract(any(), any());
    verify(nutritionMicrosDataExtractor).extract(any(), any());
    verify(repository).saveAll(nutritionFitnessHealthDataArgumentCaptor.capture());

    List<NutritionFitnessHealthData> nutritionFitnessHealthDataList = nutritionFitnessHealthDataArgumentCaptor.getValue();
    assertNotNull(nutritionFitnessHealthDataList);
    assertEquals(1, nutritionFitnessHealthDataList.size());
    NutritionFitnessHealthData nutritionFitnessHealthData = nutritionFitnessHealthDataList.get(0);
    assertEquals(CLIENT_ID, nutritionFitnessHealthData.getClientId());
    assertEquals(USER_ID, nutritionFitnessHealthData.getUserId());
    assertEquals(DEVICE_ID, nutritionFitnessHealthData.getDeviceId());
    assertEquals(VIRTUAGYM, nutritionFitnessHealthData.getProvider());
    assertEquals(WHATER_ML, nutritionFitnessHealthData.getWaterMl());
    assertTrue(nutritionFitnessHealthData.getCreatedAt().isAfter(startTime));
  }

  private void mockProvider() {

    when(payloadParser.parseProvider(any())).thenReturn(VIRTUAGYM);
  }

  private void mockReference() {

    when(payloadParser.parseReference(any())).thenReturn(new Reference(CLIENT_ID, USER_ID, DEVICE_ID));
  }
}