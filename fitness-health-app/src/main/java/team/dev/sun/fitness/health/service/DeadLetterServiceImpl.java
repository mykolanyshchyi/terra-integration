package team.dev.sun.fitness.health.service;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import team.dev.sun.fitness.health.handler.WebhookHandler;
import team.dev.sun.fitness.health.model.DeadLetter;
import team.dev.sun.fitness.health.model.WebhookPayload;
import team.dev.sun.fitness.health.parser.WebhookPayloadParser;
import team.dev.sun.fitness.health.persistence.DeadLetterRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class DeadLetterServiceImpl implements DeadLetterService {

  private final DeadLetterRepository deadLetterRepository;

  private final WebhookHandler webhookHandler;

  private final WebhookPayloadParser payloadParser;

  @Override
  @Transactional
  public int reprocessUnprocessed() {
    log.debug("Reprocessing dead-letters...");
    List<Long> unprocessedDeadLettersIds = deadLetterRepository.getUnprocessedIds();
    final AtomicInteger processedCount = new AtomicInteger();

    if (isNotEmpty(unprocessedDeadLettersIds)) {
      unprocessedDeadLettersIds.forEach(deadLeatterId -> {
        boolean reprocessedSuccessfuly = this.reprocessMessage(deadLeatterId);
        if (reprocessedSuccessfuly) {
          processedCount.incrementAndGet();
        }
      });
    }
    log.debug("Reprocessing dead-letters finished. There were processed {} letters", processedCount.get());
    return processedCount.get();
  }

  @Override
  @Transactional(propagation = REQUIRES_NEW)
  public boolean reprocessMessage(Long deadLeatterId) {
    DeadLetter deadLetter = deadLetterRepository.getReferenceById(deadLeatterId);
    WebhookPayload webhookPayload = payloadParser.toWebhookPayload(deadLetter.getData());
    Future<Boolean> futureResult = webhookHandler.submitPayload(webhookPayload);
    boolean processedSuccessfully = waitUntillDone(futureResult);
    if (futureResult.isDone() && processedSuccessfully) {
      deadLetter.setProcessed(true);
      deadLetter.setProcessedAt(ZonedDateTime.now());
      deadLetterRepository.save(deadLetter);
      log.debug("Reprocessed dead-letter with id: {}", deadLeatterId);
    }
    return processedSuccessfully;
  }

  private boolean waitUntillDone(Future<Boolean> future) {

    try {
      return future.get();
    } catch (ExecutionException | InterruptedException e) {
      log.error("Error occured while waiting for message processing", e);
      return false;
    }
  }
}
