package team.dev.sun.fitness.health.mapper.nutrition;

import static org.junit.jupiter.api.Assertions.assertEquals;

import team.dev.sun.fitness.health.api.dto.nutrition.NutritionMacrosDTO;
import team.dev.sun.fitness.health.model.nutrition.NutritionMacros;
import org.junit.jupiter.api.Test;

class NutritionMacrosMapperTest {

  private static final Long ID = 1L;

  private static final Double FAT_G = 2.2d;

  private static final Double TRANSFAT_G = 3.3d;

  private static final Double FIBER_G = 4.4d;

  private static final Double CARBOHYDRATES_G = 5.5d;

  private static final Double PROTEIN_G = 6.6d;

  private static final Double CHOLESTEROL_MG = 7.7d;

  private static final Double SODIUMM_G = 8.8d;

  private static final Double CALORIES = 9.9d;

  private static final Double SUGAR_G = 10.1d;

  private static final Double ALCOHOL_G = 11.3d;

  @Test
  void mapNutritionMacros() {

    NutritionMacrosDTO expected = getDto();
    NutritionMacrosMapper macrosMapper = new NutritionMacrosMapper();
    NutritionMacrosDTO actual = macrosMapper.map(getEntity());
    assertEquals(expected, actual);
  }

  private NutritionMacros getEntity() {

    NutritionMacros nutritionMacros = new NutritionMacros();
    nutritionMacros.setId(ID);
    nutritionMacros.setFatG(FAT_G);
    nutritionMacros.setTransFatG(TRANSFAT_G);
    nutritionMacros.setFiberG(FIBER_G);
    nutritionMacros.setCarbohydratesG(CARBOHYDRATES_G);
    nutritionMacros.setProteinG(PROTEIN_G);
    nutritionMacros.setCholesterolMg(CHOLESTEROL_MG);
    nutritionMacros.setSodiumMg(SODIUMM_G);
    nutritionMacros.setCalories(CALORIES);
    nutritionMacros.setSugarG(SUGAR_G);
    nutritionMacros.setAlcoholG(ALCOHOL_G);
    return nutritionMacros;
  }

  private NutritionMacrosDTO getDto() {

    return NutritionMacrosDTO.builder()
                             .id(ID)
                             .fatG(FAT_G)
                             .transFatG(TRANSFAT_G)
                             .fiberG(FIBER_G)
                             .carbohydratesG(CARBOHYDRATES_G)
                             .proteinG(PROTEIN_G)
                             .cholesterolMg(CHOLESTEROL_MG)
                             .sodiumMg(SODIUMM_G)
                             .calories(CALORIES)
                             .sugarG(SUGAR_G)
                             .alcoholG(ALCOHOL_G)
                             .build();
  }
}