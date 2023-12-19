package team.dev.sun.fitness.health.model;

import com.fasterxml.jackson.databind.JsonNode;

public record WebhookPayload(String type, JsonNode payload) {
}
