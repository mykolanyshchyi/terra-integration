package team.dev.sun.fitness.health.exception;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {

  private final MessageSourceAccessor messageSource;

  @ExceptionHandler(InvalidSignatureException.class)
  @ResponseStatus(UNAUTHORIZED)
  public ResponseEntity<ErrorMessage> handle(InvalidSignatureException exception) {
    String code = exception.getMessage();
    String message = getMessage(exception);
    log.error("{}: {}", exception.getClass().getSimpleName(), message);
    return ResponseEntity.status(UNAUTHORIZED).body(new ErrorMessage(code, message));
  }

  private String getMessage(Exception exception) {
    String messageKey = exception.getMessage();
    if (exception instanceof ExceptionMessageWithArguments exceptionMessageWithArguments) {
      return messageSource.getMessage(messageKey, exceptionMessageWithArguments.getArguments(), messageKey);
    } else {
      return messageSource.getMessage(messageKey, messageKey);
    }
  }

  @Data
  @RequiredArgsConstructor
  private static class ErrorMessage {
    private final String type = "error";
    private final String code;
    private final String message;
  }
}
