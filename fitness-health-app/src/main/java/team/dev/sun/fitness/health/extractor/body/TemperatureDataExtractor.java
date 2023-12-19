package team.dev.sun.fitness.health.extractor.body;

import static team.dev.sun.fitness.health.api.model.TemperatureType.AMBIENT;
import static team.dev.sun.fitness.health.api.model.TemperatureType.BODY;
import static team.dev.sun.fitness.health.api.model.TemperatureType.SKIN;
import static team.dev.sun.fitness.health.util.DateUtil.parseDate;
import static java.util.Collections.emptyList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

import co.tryterra.terraclient.models.v2.body.TemperatureData;
import co.tryterra.terraclient.models.v2.samples.TemperatureSample;
import team.dev.sun.fitness.health.api.model.TemperatureType;
import team.dev.sun.fitness.health.extractor.AbstractDataExtractor;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import team.dev.sun.fitness.health.model.body.Temperature;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TemperatureDataExtractor extends AbstractDataExtractor<TemperatureData, BodyFitnessHealthData> {

  @Override
  protected void extractData(final TemperatureData source, final BodyFitnessHealthData entity) {

    List<Temperature> temperatures = new ArrayList<>();
    temperatures.addAll(createTemperatures(source.getAmbientTemperatureSamples(), AMBIENT, entity));
    temperatures.addAll(createTemperatures(source.getBodyTemperatureSamples(), BODY, entity));
    temperatures.addAll(createTemperatures(source.getSkinTemperatureSamples(), SKIN, entity));
    entity.setTemperatures(temperatures);
  }

  private List<Temperature> createTemperatures(List<TemperatureSample> samples,
                                               TemperatureType type,
                                               BodyFitnessHealthData bodyFitnessHealthData) {

    if (isEmpty(samples)) {
      return emptyList();
    }
    return samples.stream()
                  .map(sample -> {
                    Temperature temperature = new Temperature();
                    temperature.setType(type);
                    temperature.setTemperatureCelsius(sample.getTemperatureCelsius());
                    temperature.setTimestamp(parseDate(sample.getTimestamp()));
                    temperature.setBodyFitnessHealthData(bodyFitnessHealthData);
                    return temperature;
                  })
                  .toList();
  }
}
