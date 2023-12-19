package team.dev.sun.fitness.health.controller;

import static team.dev.sun.fitness.health.api.ApiUrls.REPROCESS_MESSAGES_FROM_DLQ;

import team.dev.sun.fitness.health.service.DeadLetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeadLetterController {

  private final DeadLetterService deadLetterService;

  @PostMapping(REPROCESS_MESSAGES_FROM_DLQ)
  public int reprocessUnprocessed() {
    return deadLetterService.reprocessUnprocessed();
  }
}
