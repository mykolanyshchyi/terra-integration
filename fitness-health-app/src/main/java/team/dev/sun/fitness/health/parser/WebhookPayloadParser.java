package team.dev.sun.fitness.health.parser;

import team.dev.sun.fitness.health.api.model.Provider;
import team.dev.sun.fitness.health.model.Reference;
import team.dev.sun.fitness.health.model.WebhookPayload;
import java.util.List;

public interface WebhookPayloadParser {

  <T> List<T> parseAllData(WebhookPayload payload, Class<T> targetClass);

  Reference parseReference(WebhookPayload payload);

  Provider parseProvider(WebhookPayload payload);

  WebhookPayload toWebhookPayload(String payload);
}
