package team.dev.sun.fitness.health.exception;

public class PayloadParseException extends RuntimeException implements ExceptionMessageWithArguments {

  private static final String MESSAGE_KEY = "exception.terra.cannot-parse-payload";

  private final String targetClassName;

  public PayloadParseException(final String targetClassName, final Throwable cause) {

    super(MESSAGE_KEY, cause);
    this.targetClassName = targetClassName;
  }

  @Override
  public Object[] getArguments() {

    return new Object[]{targetClassName};
  }
}
