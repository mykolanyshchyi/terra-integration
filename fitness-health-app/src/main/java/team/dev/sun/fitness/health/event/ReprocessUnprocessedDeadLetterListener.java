package team.dev.sun.fitness.health.event;

import team.dev.sun.fitness.health.service.DeadLetterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "dlq.reprocess-unprocessed")
public class ReprocessUnprocessedDeadLetterListener implements ApplicationListener<ContextRefreshedEvent> {

  private final DeadLetterService deadLetterService;

  @Override
  @Transactional
  public void onApplicationEvent(final ContextRefreshedEvent event) {
    deadLetterService.reprocessUnprocessed();
  }
}
