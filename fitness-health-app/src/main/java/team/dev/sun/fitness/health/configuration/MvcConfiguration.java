package team.dev.sun.fitness.health.configuration;

import com.slack.api.Slack;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MvcConfiguration {

  @Bean
  public Slack slack() {

    return Slack.getInstance();
  }
}
