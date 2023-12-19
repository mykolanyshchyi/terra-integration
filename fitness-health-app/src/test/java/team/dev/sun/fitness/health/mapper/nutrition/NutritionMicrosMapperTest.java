package team.dev.sun.fitness.health.mapper.nutrition;

import static org.junit.jupiter.api.Assertions.assertEquals;

import team.dev.sun.fitness.health.api.dto.nutrition.NutritionMicrosDTO;
import team.dev.sun.fitness.health.model.nutrition.NutritionMicros;
import org.junit.jupiter.api.Test;

class NutritionMicrosMapperTest {

  private static final Long ID = 1L;

  private static final Double SELENIUM_MG = 2d;

  private static final Double NIACIN_MG = 3d;

  private static final Double MAGNESIUM_MG = 4d;

  private static final Double COPPER_MG = 5d;

  private static final Double VITAMIN_B12_MG = 6d;

  private static final Double VITAMIN_B6_MG = 7d;

  private static final Double VITAMIN_C_MG = 8d;

  private static final Double ZINC_MG = 9d;

  private static final Double VITAMIN_E_MG = 10d;

  private static final Double MANGANESE_MG = 11d;

  private static final Double VITAMIN_D_MG = 12d;

  private static final Double IODINE_MG = 13d;

  private static final Double CHLORIDE_MG = 14d;

  private static final Double FOLATE_MG = 15d;

  private static final Double CALCIUM_MG = 16d;

  private static final Double MOLYBDENUM_MG = 17d;

  private static final Double VITAMIN_A_MG = 18d;

  private static final Double RIBO_FLAVIN_MG = 19d;

  private static final Double FOLIC_ACID_MG = 20d;

  private static final Double IRON_MG = 21d;

  private static final Double THIAMIN_MG = 22d;

  private static final Double PANTOTHENIC_ACID_MG = 23d;

  private static final Double CAFFEINE_MG = 24d;

  private static final Double VITAMIN_K_MG = 25d;

  private static final Double CHROMIUM_MG = 26d;

  private static final Double POTASSIUM_MG = 27d;

  private static final Double BIOTIN_MG = 28d;

  private static final Double PHOSPHORUS_MG = 29d;

  @Test
  void mapNutritionMicros() {

    NutritionMicrosDTO expected = getDto();
    NutritionMicrosMapper microsMapper = new NutritionMicrosMapper();
    NutritionMicrosDTO actual = microsMapper.map(getEntity());
    assertEquals(expected, actual);
  }

  private NutritionMicros getEntity() {

    NutritionMicros nutritionMicros = new NutritionMicros();
    nutritionMicros.setId(ID);
    nutritionMicros.setSeleniumMg(SELENIUM_MG);
    nutritionMicros.setNiacinMg(NIACIN_MG);
    nutritionMicros.setMagnesiumMg(MAGNESIUM_MG);
    nutritionMicros.setCopperMg(COPPER_MG);
    nutritionMicros.setVitaminB12Mg(VITAMIN_B12_MG);
    nutritionMicros.setVitaminB6Mg(VITAMIN_B6_MG);
    nutritionMicros.setVitaminCMg(VITAMIN_C_MG);
    nutritionMicros.setZincMg(ZINC_MG);
    nutritionMicros.setVitaminEMg(VITAMIN_E_MG);
    nutritionMicros.setManganeseMg(MANGANESE_MG);
    nutritionMicros.setVitaminDMg(VITAMIN_D_MG);
    nutritionMicros.setIodineMg(IODINE_MG);
    nutritionMicros.setChlorideMg(CHLORIDE_MG);
    nutritionMicros.setFolateMg(FOLATE_MG);
    nutritionMicros.setCalciumMg(CALCIUM_MG);
    nutritionMicros.setMolybdenumMg(MOLYBDENUM_MG);
    nutritionMicros.setVitaminAMg(VITAMIN_A_MG);
    nutritionMicros.setRiboflavinMg(RIBO_FLAVIN_MG);
    nutritionMicros.setFolicAcidMg(FOLIC_ACID_MG);
    nutritionMicros.setIronMg(IRON_MG);
    nutritionMicros.setThiaminMg(THIAMIN_MG);
    nutritionMicros.setPantothenicAcidMg(PANTOTHENIC_ACID_MG);
    nutritionMicros.setCaffeineMg(CAFFEINE_MG);
    nutritionMicros.setVitaminKMg(VITAMIN_K_MG);
    nutritionMicros.setChromiumMg(CHROMIUM_MG);
    nutritionMicros.setPotassiumMg(POTASSIUM_MG);
    nutritionMicros.setBiotinMg(BIOTIN_MG);
    nutritionMicros.setPhosphorusMg(PHOSPHORUS_MG);
    return nutritionMicros;
  }

  private NutritionMicrosDTO getDto() {

    return NutritionMicrosDTO.builder()
                             .id(ID)
                             .seleniumMg(SELENIUM_MG)
                             .niacinMg(NIACIN_MG)
                             .magnesiumMg(MAGNESIUM_MG)
                             .copperMg(COPPER_MG)
                             .vitaminB12Mg(VITAMIN_B12_MG)
                             .vitaminB6Mg(VITAMIN_B6_MG)
                             .vitaminCMg(VITAMIN_C_MG)
                             .zincMg(ZINC_MG)
                             .vitaminEMg(VITAMIN_E_MG)
                             .manganeseMg(MANGANESE_MG)
                             .vitaminDMg(VITAMIN_D_MG)
                             .iodineMg(IODINE_MG)
                             .chlorideMg(CHLORIDE_MG)
                             .folateMg(FOLATE_MG)
                             .calciumMg(CALCIUM_MG)
                             .molybdenumMg(MOLYBDENUM_MG)
                             .vitaminAMg(VITAMIN_A_MG)
                             .riboflavinMg(RIBO_FLAVIN_MG)
                             .folicAcidMg(FOLIC_ACID_MG)
                             .ironMg(IRON_MG)
                             .thiaminMg(THIAMIN_MG)
                             .pantothenicAcidMg(PANTOTHENIC_ACID_MG)
                             .caffeineMg(CAFFEINE_MG)
                             .vitaminKMg(VITAMIN_K_MG)
                             .chromiumMg(CHROMIUM_MG)
                             .potassiumMg(POTASSIUM_MG)
                             .biotinMg(BIOTIN_MG)
                             .phosphorusMg(PHOSPHORUS_MG)
                             .build();
  }
}