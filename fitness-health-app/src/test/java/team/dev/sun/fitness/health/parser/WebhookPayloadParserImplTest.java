package team.dev.sun.fitness.health.parser;

import static team.dev.sun.fitness.health.api.model.Provider.APPLE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import co.tryterra.terraclient.models.v2.activity.Activity;
import co.tryterra.terraclient.models.v2.sleep.Sleep;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.api.model.Provider;
import team.dev.sun.fitness.health.exception.InvalidUserDataException;
import team.dev.sun.fitness.health.exception.InvalidUserReferenceException;
import team.dev.sun.fitness.health.exception.PayloadParseException;
import team.dev.sun.fitness.health.model.Reference;
import team.dev.sun.fitness.health.model.WebhookPayload;
import java.io.IOException;
import java.util.List;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WebhookPayloadParserImplTest {

  private static final String PAYLOAD = """
      {
        "type": "activity",
        "user": {
          "reference_id": "1:5:8c47283bd0fa",
          "last_webhook_update": null,
          "user_id": "b1dbd691-8140-4071-9df1-8c47283bd0fa",
          "provider": "APPLE",
          "scopes": null
        }
      }
      """;

  @Spy
  private ObjectMapper objectMapper;

  @InjectMocks
  private WebhookPayloadParserImpl payloadParser;

  @Test
  void parseProvider() {

    WebhookPayload webhookPayload = toWebhookPayload(PAYLOAD);
    Provider actual = payloadParser.parseProvider(webhookPayload);
    assertEquals(APPLE, actual);
  }

  @SneakyThrows
  private WebhookPayload toWebhookPayload(String payload) {

    JsonNode jsonNode = objectMapper.readTree(payload);
    String type = jsonNode.get("type").asText();
    return new WebhookPayload(type, jsonNode);
  }

  @Nested
  class ParseAllData {

    @Test
    void returnEmptyListAsDataIsNull() {

      String payload = """
          {
             "type": "activity",
             "data": null
          }
          """;
      WebhookPayload webhookPayload = toWebhookPayload(payload);
      List<Activity> activities = payloadParser.parseAllData(webhookPayload, Activity.class);
      assertNotNull(activities);
      assertTrue(activities.isEmpty());
    }

    @Test
    void returnEmptyListAsDataIsSimpleObject() {

      String payload = """
          {
             "type": "activity",
             "data": "some data"
          }
          """;
      WebhookPayload webhookPayload = toWebhookPayload(payload);
      List<Activity> activities = payloadParser.parseAllData(webhookPayload, Activity.class);
      assertNotNull(activities);
      assertTrue(activities.isEmpty());
    }

    @Test
    void parseAllData() throws IOException {

      String payload = IOUtils.resourceToString(
          "integration-test-data/sleepData100.json", UTF_8, this.getClass().getClassLoader());
      WebhookPayload webhookPayload = toWebhookPayload(payload);
      List<Sleep> sleepDataList = payloadParser.parseAllData(webhookPayload, Sleep.class);
      assertNotNull(sleepDataList);
      assertEquals(100, sleepDataList.size());
    }
  }

  @Nested
  class ParseReference {

    @Test
    void cannotParseObject() {

      String payload = """
           {
              "type": "activity",
              "user": {
                  "firstName": "David",
                  "lastName": "Smith"
              }
           }
          """;
      WebhookPayload webhookPayload = toWebhookPayload(payload);
      PayloadParseException exception = assertThrows(
          PayloadParseException.class, () -> payloadParser.parseReference(webhookPayload));
      assertEquals("exception.terra.cannot-parse-payload", exception.getMessage());
      assertNotNull(exception.getArguments());
      assertEquals(1, exception.getArguments().length);
      assertEquals("UserImpl", exception.getArguments()[0]);
    }

    @Test
    void invalidUserData() {

      String payload = """
           {
              "type": "activity"
           }
          """;
      WebhookPayload webhookPayload = toWebhookPayload(payload);
      InvalidUserDataException exception = assertThrows(
          InvalidUserDataException.class, () -> payloadParser.parseReference(webhookPayload));
      assertEquals("exception.terra.invalid-user-data", exception.getMessage());
    }

    @Test
    void referenceIsNull() {

      String payload = """
           {
              "type": "activity",
              "user": {
                "reference_id": null,
                "last_webhook_update": null,
                "user_id": "b1dbd691-8140-4071-9df1-8c47283bd0fa",
                "provider": "APPLE",
                "scopes": null
              }
           }
          """;
      WebhookPayload webhookPayload = toWebhookPayload(payload);
      InvalidUserReferenceException exception = assertThrows(
          InvalidUserReferenceException.class, () -> payloadParser.parseReference(webhookPayload));
      assertEquals("exception.terra.invalid-reference", exception.getMessage());
    }

    @Test
    void invalidReference() {

      String payload = """
           {
              "type": "activity",
              "user": {
                "reference_id": "8c47283bd0fa",
                "last_webhook_update": null,
                "user_id": "b1dbd691-8140-4071-9df1-8c47283bd0fa",
                "provider": "APPLE",
                "scopes": null
              }
           }
          """;
      WebhookPayload webhookPayload = toWebhookPayload(payload);
      InvalidUserReferenceException exception = assertThrows(
          InvalidUserReferenceException.class, () -> payloadParser.parseReference(webhookPayload));
      assertEquals("exception.terra.invalid-reference", exception.getMessage());
    }

    @Test
    void parseReference() {

      Reference expected = new Reference(1L, 5L, "8c47283bd0fa");
      WebhookPayload webhookPayload = toWebhookPayload(PAYLOAD);
      Reference actual = payloadParser.parseReference(webhookPayload);
      assertEquals(expected, actual);
    }
  }
}