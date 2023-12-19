package team.dev.sun.fitness.health.controller;

import static team.dev.sun.fitness.health.api.ApiUrls.CREATE_FITNESS_HEALTH_DATA;
import static team.dev.sun.fitness.health.api.ApiUrls.SEARCH_FITNESS_HEALTH_DATA;
import static team.dev.sun.fitness.health.controller.FitnessHealthDataAssertions.assertEqualsFitnessHealthData;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.FitnessHealthApplication;
import team.dev.sun.fitness.health.api.dto.FitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.activity.ActiveDurationDTO;
import team.dev.sun.fitness.health.api.dto.activity.ActivityFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.activity.ActivityLevelDTO;
import team.dev.sun.fitness.health.api.dto.body.BodyFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.body.GlucoseDTO;
import team.dev.sun.fitness.health.api.dto.body.HydrationDTO;
import team.dev.sun.fitness.health.api.dto.body.OxygenDTO;
import team.dev.sun.fitness.health.api.dto.daily.DailyFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.daily.StressDTO;
import team.dev.sun.fitness.health.api.dto.nutrition.NutritionFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.sleep.SleepFitnessHealthDataDTO;
import team.dev.sun.fitness.health.dlq.DeadLetterQueue;
import team.dev.sun.fitness.health.event.ReprocessUnprocessedDeadLetterListener;
import team.dev.sun.fitness.health.handler.WebhookHandler;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.jose4j.jwk.RsaJsonWebKey;
import org.junit.AfterClass;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.testcontainers.containers.MSSQLServerContainer;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@TestPropertySource(locations = "classpath:application-integration-test.yml")
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = FitnessHealthApplication.class)
@ActiveProfiles(value = {"default", "integration-test"})
class FitnessHealthControllerTest extends AbstractIntegrationTest {

  @ClassRule
  public static MSSQLServerContainer sqlserver = new MSSQLServerContainer("mcr.microsoft.com/mssql/server:2017-CU12")
      .acceptLicense();

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private AuthenticationSupport authenticationSupport;

  @Autowired
  private DeadLetterQueue deadLetterQueue;

  @Autowired
  private ReprocessUnprocessedDeadLetterListener reprocessUnprocessedDeadLetterListener;

  @SpyBean
  private WebhookHandler webhookHandler;

  @Autowired
  public FitnessHealthControllerTest(final RsaJsonWebKey rsaJsonWebKey) {

    super(rsaJsonWebKey);
  }

  @DynamicPropertySource
  static void sqlserverProperties(DynamicPropertyRegistry registry) {

    sqlserver.start();
    registry.add("spring.datasource.url", sqlserver::getJdbcUrl);
    registry.add("spring.datasource.username", sqlserver::getUsername);
    registry.add("spring.datasource.password", sqlserver::getPassword);
  }

  @BeforeEach
  public void setUp() throws Exception {

    when(webhookHandler.isSignatureValid(anyString(), anyString())).thenReturn(true);
  }

  @AfterClass
  public static void afterClass() throws Exception {

    sqlserver.stop();
  }

  @Test
  void searchFitnessHealthDataActivity() throws Exception {

    createFitnessHealthData("activityData.json");
    LinkedMultiValueMap<String, String> filters = new LinkedMultiValueMap<>();
    filters.add("clientId", "1");
    filters.add("userId", "5");
    filters.add("deviceId", "8c47283bd0fa");
    filters.add("dataTypes", "ACTIVITY");

    await().atMost(3, SECONDS).until(() -> {
      FitnessHealthDataDTO result = searchFitnessHealthData(filters);
      assertNotNull(result);
      assertFalse(result.getActivityData().isEmpty());
      assertTrue(result.getDailyData().isEmpty());
      assertTrue(result.getBodyData().isEmpty());
      assertTrue(result.getNutritionData().isEmpty());
      assertTrue(result.getSleepData().isEmpty());
      return true;
    });
  }

  @Test
  void searchFitnessHealthDataBody() throws Exception {

    createFitnessHealthData("bodyData.json");

    LinkedMultiValueMap<String, String> filters = new LinkedMultiValueMap<>();
    filters.add("clientId", "1");
    filters.add("userId", "10");
    filters.add("deviceId", "000ee535");
    filters.add("dataTypes", "BODY");

    await().atMost(3, SECONDS).until(() -> {
      FitnessHealthDataDTO result = searchFitnessHealthData(filters);
      assertNotNull(result);
      assertTrue(result.getActivityData().isEmpty());
      assertTrue(result.getDailyData().isEmpty());
      assertFalse(result.getBodyData().isEmpty());
      assertTrue(result.getNutritionData().isEmpty());
      assertTrue(result.getSleepData().isEmpty());
      return true;
    });
  }

  @Test
  void searchFitnessHealthDataDaily() throws Exception {

    createFitnessHealthData("dailyData.json");

    LinkedMultiValueMap<String, String> filters = new LinkedMultiValueMap<>();
    filters.add("clientId", "1");
    filters.add("userId", "15");
    filters.add("deviceId", "362d-4aab-a38a");
    filters.add("dataTypes", "DAILY");

    await().atMost(3, SECONDS).until(() -> {
      FitnessHealthDataDTO result = searchFitnessHealthData(filters);
      assertNotNull(result);
      assertTrue(result.getActivityData().isEmpty());
      assertFalse(result.getDailyData().isEmpty());
      assertTrue(result.getBodyData().isEmpty());
      assertTrue(result.getNutritionData().isEmpty());
      assertTrue(result.getSleepData().isEmpty());
      return true;
    });
  }

  @Test
  void searchFitnessHealthDataNutrition() throws Exception {

    createFitnessHealthData("nutritionData.json");

    LinkedMultiValueMap<String, String> filters = new LinkedMultiValueMap<>();
    filters.add("clientId", "1");
    filters.add("userId", "20");
    filters.add("deviceId", "b9ff-42ca-b50e-326f6de");
    filters.add("dataTypes", "NUTRITION");

    await().atMost(3, SECONDS).until(() -> {
      FitnessHealthDataDTO result = searchFitnessHealthData(filters);
      assertNotNull(result);
      assertTrue(result.getActivityData().isEmpty());
      assertTrue(result.getDailyData().isEmpty());
      assertTrue(result.getBodyData().isEmpty());
      assertFalse(result.getNutritionData().isEmpty());
      assertTrue(result.getSleepData().isEmpty());
      return true;
    });
  }

  @Test
  void searchFitnessHealthDataSleep() throws Exception {

    createFitnessHealthData("sleepData.json");

    LinkedMultiValueMap<String, String> filters = new LinkedMultiValueMap<>();
    filters.add("clientId", "1");
    filters.add("userId", "25");
    filters.add("deviceId", "64e8f-74bb-4899-8035-c3e893");
    filters.add("dataTypes", "SLEEP");

    await().atMost(3, SECONDS).until(() -> {
      FitnessHealthDataDTO result = searchFitnessHealthData(filters);
      assertNotNull(result);
      assertTrue(result.getActivityData().isEmpty());
      assertTrue(result.getDailyData().isEmpty());
      assertTrue(result.getBodyData().isEmpty());
      assertTrue(result.getNutritionData().isEmpty());
      assertFalse(result.getSleepData().isEmpty());
      return true;
    });
  }

  private void createFitnessHealthData(String filename) throws Exception {

    String requestPayload = getRequestPayload(filename);
    HttpHeaders headers = new HttpHeaders();
    headers.add("terra-signature", "signatureHeader");

    MockHttpServletRequestBuilder authentication = authenticationSupport.authenticate(POST, CREATE_FITNESS_HEALTH_DATA);

    mvc.perform(authentication.content(requestPayload).headers(headers))
       .andExpect(status().isOk());
  }

  @SneakyThrows
  private String getRequestPayload(String filename) {

    return IOUtils.resourceToString("integration-test-data/" + filename, UTF_8, this.getClass().getClassLoader());
  }

  private FitnessHealthDataDTO searchFitnessHealthData(LinkedMultiValueMap<String, String> searchFilters) throws Exception {

    MockHttpServletRequestBuilder authentication = authenticationSupport.authenticate(GET, SEARCH_FITNESS_HEALTH_DATA);
    String content = mvc.perform(authentication.params(searchFilters))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse().getContentAsString();

    FitnessHealthDataDTO result = objectMapper.readValue(content, FitnessHealthDataDTO.class);
    removeAllIDs(result);
    return result;
  }

  @SneakyThrows
  private FitnessHealthDataDTO getExpected(String filename) {

    return objectMapper.readValue(getResource(filename), FitnessHealthDataDTO.class);
  }

  @SneakyThrows
  private String getResource(String filename) {

    return IOUtils.resourceToString("integration-test-data/" + filename, UTF_8, this.getClass().getClassLoader());
  }

  @Test
  void searchFitnessHealthDataActivity15() throws Exception {

    createFitnessHealthData("activityData15.json");
    FitnessHealthDataDTO expected = getExpected("activityData15-expected.json");
    LinkedMultiValueMap<String, String> filters = new LinkedMultiValueMap<>();
    filters.add("clientId", "1");
    filters.add("userId", "150");
    filters.add("deviceId", "8c47283bd0fa");
    filters.add("dataTypes", "ACTIVITY");

    await().atMost(5, SECONDS).until(() -> {
      FitnessHealthDataDTO actual = searchFitnessHealthData(filters);
      assertEqualsFitnessHealthData(expected, actual);
      return true;
    });
  }

  @Test
  void searchFitnessHealthDataBody10() throws Exception {

    createFitnessHealthData("bodyData10.json");
    FitnessHealthDataDTO expected = getExpected("bodyData10-expected.json");

    LinkedMultiValueMap<String, String> filters = new LinkedMultiValueMap<>();
    filters.add("clientId", "1");
    filters.add("userId", "100");
    filters.add("deviceId", "000ee535");
    filters.add("dataTypes", "BODY");

    final AtomicInteger counter = new AtomicInteger();

    await().atMost(200, SECONDS)
           .until(() -> {
             FitnessHealthDataDTO actual = searchFitnessHealthData(filters);
             assertEqualsFitnessHealthData(expected, actual);
             return true;
           });
  }

  @Test
  void searchFitnessHealthDataDaily20() throws Exception {

    createFitnessHealthData("dailyData20.json");
    FitnessHealthDataDTO expected = getExpected("dailyData20-expected.json");

    LinkedMultiValueMap<String, String> filters = new LinkedMultiValueMap<>();
    filters.add("clientId", "1");
    filters.add("userId", "200");
    filters.add("deviceId", "362d-4aab-a38a");
    filters.add("dataTypes", "DAILY");

    await().atMost(3, SECONDS).until(() -> {
      FitnessHealthDataDTO actual = searchFitnessHealthData(filters);
      assertEqualsFitnessHealthData(expected, actual);
      return true;
    });
  }

  @Test
  void searchFitnessHealthDataNutrition50() throws Exception {

    createFitnessHealthData("nutritionData50.json");
    FitnessHealthDataDTO expected = getExpected("nutritionData50-expected.json");

    LinkedMultiValueMap<String, String> filters = new LinkedMultiValueMap<>();
    filters.add("clientId", "1");
    filters.add("userId", "500");
    filters.add("deviceId", "b9ff-42ca-b50e-326f6de");
    filters.add("dataTypes", "NUTRITION");

    await().atMost(3, SECONDS).until(() -> {
      FitnessHealthDataDTO actual = searchFitnessHealthData(filters);
      assertEqualsFitnessHealthData(expected, actual);
      return true;
    });
  }

  @Test
  void searchFitnessHealthDataSleep100() throws Exception {

    createFitnessHealthData("sleepData100.json");
    FitnessHealthDataDTO expected = getExpected("sleepData100-expected.json");

    LinkedMultiValueMap<String, String> filters = new LinkedMultiValueMap<>();
    filters.add("clientId", "1");
    filters.add("userId", "1000");
    filters.add("deviceId", "64e8f-74bb-4899-8035-c3e893");
    filters.add("dataTypes", "SLEEP");

    await().atMost(3, SECONDS).until(() -> {
      FitnessHealthDataDTO actual = searchFitnessHealthData(filters);
      assertEqualsFitnessHealthData(expected, actual);
      return true;
    });
  }

  @Test
  void reprocessUnprocessedDeadLetter() throws Exception {

    LinkedMultiValueMap<String, String> filters = new LinkedMultiValueMap<>();
    filters.add("clientId", "1");
    filters.add("userId", "999");
    filters.add("dataTypes", "ACTIVITY");
    FitnessHealthDataDTO actual = searchFitnessHealthData(filters);
    assertNotNull(actual);
    assertTrue(actual.getActivityData().isEmpty());

    String resource = getResource("activityDataReprocess.json");
    JsonNode data = objectMapper.readTree(resource);
    deadLetterQueue.createDeadLetter(data, new RuntimeException("some test exception"));

    reprocessUnprocessedDeadLetterListener.onApplicationEvent(null);

    await().atMost(3, SECONDS).until(() -> {
      FitnessHealthDataDTO fitnessHealthData = searchFitnessHealthData(filters);
      assertNotNull(fitnessHealthData);
      assertFalse(fitnessHealthData.getActivityData().isEmpty());
      assertEquals(5, fitnessHealthData.getActivityData().size());
      return true;
    });
  }

  @Test
  void invalidSignature() throws Exception {
    when(webhookHandler.isSignatureValid(anyString(), anyString())).thenReturn(false);
    String requestPayload = getRequestPayload("activityData.json");
    HttpHeaders headers = new HttpHeaders();
    headers.add("terra-signature", "invalidSignatureHeaderValue");

    MockHttpServletRequestBuilder authentication = authenticationSupport.authenticate(POST, CREATE_FITNESS_HEALTH_DATA);

    mvc.perform(authentication.content(requestPayload).headers(headers))
       .andExpect(status().isUnauthorized())
       .andExpect(jsonPath("type",  equalTo("error")))
       .andExpect(jsonPath("code",  equalTo("exception.terra.invalid-signature")))
       .andExpect(jsonPath("message",  equalTo("Invalid signature: invalidSignatureHeaderValue")));
  }

  private void removeAllIDs(FitnessHealthDataDTO fitnessHealthData) {

    removeAllIDsActivityData(fitnessHealthData.getActivityData());
    removeAllIDsBodyData(fitnessHealthData.getBodyData());
    removeAllIDsDailyData(fitnessHealthData.getDailyData());
    removeAllIDsNutritionData(fitnessHealthData.getNutritionData());
    removeAllIDsSleepData(fitnessHealthData.getSleepData());
  }

  private void removeAllIDsActivityData(List<ActivityFitnessHealthDataDTO> activityFitnessHealthData) {

    if (isNotEmpty(activityFitnessHealthData)) {
      activityFitnessHealthData
          .forEach(activityData -> {
            activityData.setId(null);

            ActiveDurationDTO activeDuration = activityData.getActiveDuration();

            if (activeDuration != null) {
              activeDuration.setId(null);
              List<ActivityLevelDTO> activityLevels = activeDuration.getActivityLevels();
              if (isNotEmpty(activityLevels)) {
                activityLevels.forEach(level -> level.setId(null));
              }
            }

            if (activityData.getCalories() != null) {
              activityData.getCalories().setId(null);
            }

            if (activityData.getDistance() != null) {
              activityData.getDistance().setId(null);
            }

            if (activityData.getMetadata() != null) {
              activityData.getMetadata().setId(null);
            }
          });
    }
  }

  private void removeAllIDsBodyData(List<BodyFitnessHealthDataDTO> bodyFitnessHealthData) {

    if (isNotEmpty(bodyFitnessHealthData)) {
      bodyFitnessHealthData
          .forEach(bodyData -> {
            bodyData.setId(null);

            if (isNotEmpty(bodyData.getBloodPressures())) {
              bodyData.getBloodPressures().forEach(bloodPressure -> bloodPressure.setId(null));
            }

            GlucoseDTO glucose = bodyData.getGlucose();
            if (glucose != null) {
              glucose.setId(null);
              if (isNotEmpty(glucose.getSamples())) {
                glucose.getSamples().forEach(sample -> sample.setId(null));
              }
            }

            HydrationDTO hydration = bodyData.getHydration();
            if (hydration != null) {
              hydration.setId(null);
              if (isNotEmpty(hydration.getHydrationLevels())) {
                hydration.getHydrationLevels().forEach(level -> level.setId(null));
              }
              if (isNotEmpty(hydration.getHydrationMeasurements())) {
                hydration.getHydrationMeasurements().forEach(measurement -> measurement.setId(null));
              }
            }

            if (isNotEmpty(bodyData.getMeasurements())) {
              bodyData.getMeasurements().forEach(measurement -> measurement.setId(null));
            }

            OxygenDTO oxygen = bodyData.getOxygen();
            if (oxygen != null) {
              oxygen.setId(null);
              if (isNotEmpty(oxygen.getOxygenSaturations())) {
                oxygen.getOxygenSaturations().forEach(saturation -> saturation.setId(null));
              }
              if (isNotEmpty(oxygen.getOxygenVo2s())) {
                oxygen.getOxygenVo2s().forEach(vo2 -> vo2.setId(null));
              }
            }

            if (isNotEmpty(bodyData.getTemperatures())) {
              bodyData.getTemperatures().forEach(temperature -> temperature.setId(null));
            }

            if (bodyData.getMetadata() != null) {
              bodyData.getMetadata().setId(null);
            }
          });
    }
  }

  private void removeAllIDsDailyData(List<DailyFitnessHealthDataDTO> dailyFitnessHealthData) {

    if (isNotEmpty(dailyFitnessHealthData)) {
      dailyFitnessHealthData
          .forEach(dailyData -> {
            dailyData.setId(null);
            if (dailyData.getHeartRate() != null) {
              dailyData.getHeartRate().setId(null);
            }
            if (dailyData.getScore() != null) {
              dailyData.getScore().setId(null);
            }
            StressDTO stress = dailyData.getStress();
            if (stress != null) {
              stress.setId(null);
              if (isNotEmpty(stress.getSamples())) {
                stress.getSamples().forEach(sample -> sample.setId(null));
              }
            }
            if (dailyData.getMetadata() != null) {
              dailyData.getMetadata().setId(null);
            }
          });
    }
  }

  private void removeAllIDsNutritionData(List<NutritionFitnessHealthDataDTO> nutritionFitnessHealthData) {

    if (isNotEmpty(nutritionFitnessHealthData)) {
      nutritionFitnessHealthData
          .forEach(nutritionData -> {
            nutritionData.setId(null);
            if (nutritionData.getMacros() != null) {
              nutritionData.getMacros().setId(null);
            }
            if (nutritionData.getMicros() != null) {
              nutritionData.getMicros().setId(null);
            }
            if (nutritionData.getMetadata() != null) {
              nutritionData.getMetadata().setId(null);
            }
          });
    }
  }

  private void removeAllIDsSleepData(List<SleepFitnessHealthDataDTO> sleepFitnessHealthData) {

    if (isNotEmpty(sleepFitnessHealthData)) {
      sleepFitnessHealthData
          .forEach(sleepData -> {
            sleepData.setId(null);
            if (sleepData.getAsleepDuration() != null) {
              sleepData.getAsleepDuration().setId(null);
            }
            if (sleepData.getAwakeDuration() != null) {
              sleepData.getAwakeDuration().setId(null);
            }
            if (sleepData.getMetadata() != null) {
              sleepData.getMetadata().setId(null);
            }
          });
    }
  }
}