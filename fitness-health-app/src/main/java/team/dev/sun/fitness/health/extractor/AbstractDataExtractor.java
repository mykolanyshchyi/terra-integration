package team.dev.sun.fitness.health.extractor;

import team.dev.sun.fitness.health.model.FitnessHealthData;

public abstract class AbstractDataExtractor<S, T extends FitnessHealthData> implements DataExtractor<S, T> {

  @Override
  public void extract(final S source, final T entity) {

    if (isApplicable()) {
      extractData(source, entity);
    }
  }

  protected abstract void extractData(S source, T entity);
}
