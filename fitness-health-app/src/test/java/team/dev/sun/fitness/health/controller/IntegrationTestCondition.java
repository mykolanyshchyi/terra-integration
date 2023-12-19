package team.dev.sun.fitness.health.controller;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Profiles;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class IntegrationTestCondition implements Condition {

  private static final Profiles ENABLED_PROFILES = Profiles.of("integration-test");

  @Override
  public boolean matches(final ConditionContext context, final AnnotatedTypeMetadata metadata) {

    return context.getEnvironment().acceptsProfiles(ENABLED_PROFILES);
  }
}
