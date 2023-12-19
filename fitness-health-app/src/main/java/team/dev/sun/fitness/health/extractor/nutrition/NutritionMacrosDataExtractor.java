package team.dev.sun.fitness.health.extractor.nutrition;

import co.tryterra.terraclient.models.v2.common.Macros;
import team.dev.sun.fitness.health.extractor.AbstractDataExtractor;
import team.dev.sun.fitness.health.model.nutrition.NutritionFitnessHealthData;
import team.dev.sun.fitness.health.model.nutrition.NutritionMacros;
import org.springframework.stereotype.Component;

@Component
public class NutritionMacrosDataExtractor extends AbstractDataExtractor<Macros, NutritionFitnessHealthData> {

  @Override
  protected void extractData(final Macros source, final NutritionFitnessHealthData entity) {

    NutritionMacros nutritionMacros = new NutritionMacros();
    nutritionMacros.setFatG(source.getFatG());
    nutritionMacros.setTransFatG(source.getTransFatG());
    nutritionMacros.setFiberG(source.getFiberG());
    nutritionMacros.setCarbohydratesG(source.getCarbohydratesG());
    nutritionMacros.setProteinG(source.getProteinG());
    nutritionMacros.setCholesterolMg(source.getCholesterolMg());
    nutritionMacros.setSodiumMg(source.getSodiumMg());
    nutritionMacros.setCalories(source.getCalories());
    nutritionMacros.setSugarG(source.getSugarG());
    nutritionMacros.setAlcoholG(source.getAlcoholG());
    entity.setMacros(nutritionMacros);
  }
}
