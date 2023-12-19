package team.dev.sun.fitness.health.exception;

public class InvalidUserReferenceException extends RuntimeException implements ExceptionMessageWithArguments {

  private static final String MESSAGE_KEY = "exception.terra.invalid-reference";

  private final String referenceId;

  public InvalidUserReferenceException(final String referenceId) {

    super(MESSAGE_KEY);
    this.referenceId = referenceId;
  }

  @Override
  public Object[] getArguments() {

    return new Object[] {referenceId == null ? "null" : referenceId};
  }
}
