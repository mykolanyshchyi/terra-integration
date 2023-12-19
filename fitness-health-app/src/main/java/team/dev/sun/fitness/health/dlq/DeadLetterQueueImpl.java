package team.dev.sun.fitness.health.dlq;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseStackTrace;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackFrames;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.exception.ExceptionMessageWithArguments;
import team.dev.sun.fitness.health.model.DeadLetter;
import team.dev.sun.fitness.health.persistence.DeadLetterRepository;
import java.util.Arrays;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class DeadLetterQueueImpl implements DeadLetterQueue {

  private final DeadLetterRepository deadLetterRepository;

  private final ObjectMapper objectMapper;

  private final MessageSourceAccessor messageSource;

  @Override
  @Transactional
  public void createDeadLetter(JsonNode data, Throwable throwable) {

    String errorMessage = getMessage(throwable);
    log.error("New dead letter: {}", errorMessage, throwable);
    DeadLetter deadLetter = new DeadLetter();
    deadLetter.setData(toJson(data));
    deadLetter.setError(errorMessage);
    deadLetter.setStackTrace(getStackTrace(throwable));
    deadLetterRepository.save(deadLetter);
  }

  private String getMessage(Throwable throwable) {

    String messageCode = throwable.getMessage();
    if (throwable instanceof ExceptionMessageWithArguments exceptionWithArguments) {
      return messageSource.getMessage(messageCode, exceptionWithArguments.getArguments(), messageCode);
    }
    return messageSource.getMessage(messageCode, messageCode);
  }

  private String toJson(JsonNode data) {

    try {
      return objectMapper.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      log.error("Cannot desirialize JsonNode to json", e);
      return "{}";
    }
  }

  private String getStackTrace(Throwable throwable) {

    return Stream.of(getStackFrames(throwable), getRootCauseStackTrace(throwable))
                 .flatMap(Arrays::stream)
                 .collect(joining(" "));
  }
}
