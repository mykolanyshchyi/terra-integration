package team.dev.sun.fitness.health.dlq;

import com.fasterxml.jackson.databind.JsonNode;

public interface DeadLetterQueue {

    void createDeadLetter(JsonNode data, Throwable throwable);
}
