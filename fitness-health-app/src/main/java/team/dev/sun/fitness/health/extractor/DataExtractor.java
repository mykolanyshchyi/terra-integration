package team.dev.sun.fitness.health.extractor;

import team.dev.sun.fitness.health.model.FitnessHealthData;

public interface DataExtractor<S, T extends FitnessHealthData> {

  void extract(S source, T entity);

  default boolean isApplicable() {

    return true;
  }
}
