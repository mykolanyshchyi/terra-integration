package team.dev.sun.fitness.health.handler;

import static team.dev.sun.fitness.health.api.model.DataType.NUTRITION;

import co.tryterra.terraclient.models.v2.common.Macros;
import co.tryterra.terraclient.models.v2.common.Metadata;
import co.tryterra.terraclient.models.v2.common.Micros;
import co.tryterra.terraclient.models.v2.nutrition.Nutrition;
import co.tryterra.terraclient.models.v2.nutrition.Nutrition.Summary;
import team.dev.sun.fitness.health.dlq.DeadLetterQueue;
import team.dev.sun.fitness.health.extractor.nutrition.NutritionMacrosDataExtractor;
import team.dev.sun.fitness.health.extractor.nutrition.NutritionMicrosDataExtractor;
import team.dev.sun.fitness.health.model.nutrition.NutritionFitnessHealthData;
import team.dev.sun.fitness.health.parser.WebhookPayloadParser;
import team.dev.sun.fitness.health.persistence.NutritionFitnessHealthDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NutritionHandler extends
                              AbstractHandler<Nutrition, NutritionFitnessHealthData, NutritionFitnessHealthDataRepository> {

  private final NutritionMacrosDataExtractor nutritionMacrosDataExtractor;

  private final NutritionMicrosDataExtractor nutritionMicrosDataExtractor;

  @Autowired
  public NutritionHandler(final DeadLetterQueue deadLetterQueue, final NutritionMacrosDataExtractor nutritionMacrosDataExtractor,
                          final NutritionMicrosDataExtractor nutritionMicrosDataExtractor,
                          final NutritionFitnessHealthDataRepository repository, final WebhookPayloadParser payloadParser) {

    super(deadLetterQueue, payloadParser, repository, Nutrition.class);
    this.nutritionMacrosDataExtractor = nutritionMacrosDataExtractor;
    this.nutritionMicrosDataExtractor = nutritionMicrosDataExtractor;
  }

  @Override
  protected NutritionFitnessHealthData extractData(Nutrition nutrition) {

    NutritionFitnessHealthData nutritionFitnessHealthData = new NutritionFitnessHealthData();
    nutritionFitnessHealthData.setDataType(NUTRITION);
    if (nutrition.getSummary() != null) {
      Summary summary = nutrition.getSummary();
      nutritionFitnessHealthData.setWaterMl(summary.getWaterMl());
      extractNutritionMacros(nutritionFitnessHealthData, summary.getMacros());
      extractNutritionMicros(nutritionFitnessHealthData, summary.getMicros());
    }
    return nutritionFitnessHealthData;
  }

  @Override
  protected void setMetadata(final Nutrition source, final NutritionFitnessHealthData entity) {

    Metadata metadata = source.getMetadata();
    if (metadata != null) {
      createAndsetMetadata(entity, metadata.getStartTime(), metadata.getEndTime());
    }
  }

  private void extractNutritionMacros(final NutritionFitnessHealthData entity, final Macros macros) {

    if (macros != null) {
      nutritionMacrosDataExtractor.extract(macros, entity);
    }
  }

  private void extractNutritionMicros(final NutritionFitnessHealthData entity, final Micros micros) {

    if (micros != null) {
      nutritionMicrosDataExtractor.extract(micros, entity);
    }
  }
}
