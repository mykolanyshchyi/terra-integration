package team.dev.sun.fitness.health.exception;

public class InvalidUserDataException extends RuntimeException {

  private static final String MESSAGE_KEY = "exception.terra.invalid-user-data";

  public InvalidUserDataException() {

    super(MESSAGE_KEY);
  }
}
