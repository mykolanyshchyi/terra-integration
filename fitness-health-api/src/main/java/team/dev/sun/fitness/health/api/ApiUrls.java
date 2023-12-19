package team.dev.sun.fitness.health.api;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiUrls {

  public static final String CREATE_FITNESS_HEALTH_DATA = "/api/v1/fitness-health";

  public static final String SEARCH_FITNESS_HEALTH_DATA = "/api/v1/fitness-health";

  public static final String REPROCESS_MESSAGES_FROM_DLQ = "/api/v1/fitness-health/dlq/reprocess";
}
