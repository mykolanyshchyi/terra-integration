package team.dev.sun.fitness.health.mapper.nutrition;

import team.dev.sun.fitness.health.api.dto.nutrition.NutritionMacrosDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.nutrition.NutritionMacros;
import org.springframework.stereotype.Component;

@Component
public class NutritionMacrosMapper extends AbstractMapper<NutritionMacros, NutritionMacrosDTO> {

  @Override
  public NutritionMacrosDTO map(final NutritionMacros source) {

    return NutritionMacrosDTO.builder()
                             .id(source.getId())
                             .fatG(source.getFatG())
                             .transFatG(source.getTransFatG())
                             .fiberG(source.getFiberG())
                             .carbohydratesG(source.getCarbohydratesG())
                             .proteinG(source.getProteinG())
                             .cholesterolMg(source.getCholesterolMg())
                             .sodiumMg(source.getSodiumMg())
                             .calories(source.getCalories())
                             .sugarG(source.getSugarG())
                             .alcoholG(source.getAlcoholG())
                             .build();
  }
}
