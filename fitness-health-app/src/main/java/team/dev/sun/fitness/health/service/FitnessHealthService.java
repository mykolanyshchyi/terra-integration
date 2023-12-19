package team.dev.sun.fitness.health.service;

import team.dev.sun.fitness.health.api.dto.FitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.model.FitnessHealthSearchFilters;

public interface FitnessHealthService {

  void processFitnessHealthData(String signatureHeader, String payload);

  FitnessHealthDataDTO searchFitnessHealthData(FitnessHealthSearchFilters searchFilters);
}
