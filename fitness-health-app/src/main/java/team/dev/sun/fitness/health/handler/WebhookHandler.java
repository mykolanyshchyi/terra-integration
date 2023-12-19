package team.dev.sun.fitness.health.handler;

import team.dev.sun.fitness.health.model.WebhookPayload;
import java.util.concurrent.Future;

public interface WebhookHandler {

  boolean isSignatureValid(String headerSignatureValue, String payload);

  Future<Boolean> submitPayload(WebhookPayload payload);
}
