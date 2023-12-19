package team.dev.sun.fitness.health.service;

public interface DeadLetterService {

  int reprocessUnprocessed();

  boolean reprocessMessage(Long deadLeatterId);
}
