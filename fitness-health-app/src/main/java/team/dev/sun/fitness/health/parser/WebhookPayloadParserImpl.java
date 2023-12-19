package team.dev.sun.fitness.health.parser;

import static java.util.Collections.emptyList;

import co.tryterra.terraclient.api.User;
import co.tryterra.terraclient.impl.UserImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.api.model.Provider;
import team.dev.sun.fitness.health.exception.InvalidUserDataException;
import team.dev.sun.fitness.health.exception.InvalidUserReferenceException;
import team.dev.sun.fitness.health.exception.PayloadParseException;
import team.dev.sun.fitness.health.model.Reference;
import team.dev.sun.fitness.health.model.WebhookPayload;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebhookPayloadParserImpl implements WebhookPayloadParser {

  private final ObjectMapper objectMapper;

  @Override
  public <T> List<T> parseAllData(final WebhookPayload webhookPayload, Class<T> targetClass) {

    return parseDataAsList(webhookPayload.payload().get("data"), targetClass);
  }

  @Override
  public Reference parseReference(final WebhookPayload webhookPayload) {

    String referenceId = getUser(webhookPayload).getReferenceId();

    if (referenceId == null) {
      throw new InvalidUserReferenceException(referenceId);
    }

    String[] references = referenceId.split(":");

    if (references.length != 3) {
      throw new InvalidUserReferenceException(referenceId);
    }

    Long clientId = Long.valueOf(references[0]);
    Long userId = Long.valueOf(references[1]);
    String deviceId = references[2];

    return new Reference(clientId, userId, deviceId);
  }

  @Override
  public Provider parseProvider(final WebhookPayload webhookPayload) {

    User user = getUser(webhookPayload);
    return Provider.valueOf(user.getProvider());
  }

  @Override
  @SneakyThrows
  public WebhookPayload toWebhookPayload(final String payload) {

    JsonNode jsonNode = objectMapper.readTree(payload);
    String type = jsonNode.get("type").asText();
    return new WebhookPayload(type, jsonNode);
  }

  private <T> T jsonNodeToObject(JsonNode node, Class<T> target) {

    try {
      return objectMapper.treeToValue(node, target);
    } catch (JsonProcessingException ex) {
      throw new PayloadParseException(target.getSimpleName(), ex);
    }
  }

  private <T> List<T> parseDataAsList(JsonNode node, Class<T> target) {

    if (node == null || !node.isArray()) {
      return emptyList();
    }
    List<T> result = new ArrayList<>();
    node.forEach((n) -> result.add(jsonNodeToObject(n, target)));
    return result;
  }

  private User getUser(final WebhookPayload webhookPayload) {

    Optional<User> maybeUser = getOptionalUser(webhookPayload);

    if (maybeUser.isEmpty()) {
      throw new InvalidUserDataException();
    }

    return maybeUser.get();
  }

  private Optional<User> getOptionalUser(final WebhookPayload webhookPayload) {

    JsonNode rawUser = webhookPayload.payload().get("user");
    if (rawUser == null) {
      return Optional.empty();
    }
    return Optional.of(jsonNodeToObject(rawUser, UserImpl.class));
  }
}
