package team.dev.sun.fitness.health.exception;

public class InvalidSignatureException extends RuntimeException implements ExceptionMessageWithArguments {

  private static final String MESSAGE_KEY = "exception.terra.invalid-signature";

  private final String signature;

  public InvalidSignatureException(String signature) {

    super(MESSAGE_KEY);
    this.signature = signature;
  }

  @Override
  public Object[] getArguments() {

    return new Object[]{signature};
  }
}
