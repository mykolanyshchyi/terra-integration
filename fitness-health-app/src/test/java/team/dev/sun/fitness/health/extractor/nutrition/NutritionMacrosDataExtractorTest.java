package team.dev.sun.fitness.health.extractor.nutrition;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import co.tryterra.terraclient.models.v2.common.Macros;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.model.nutrition.NutritionFitnessHealthData;
import team.dev.sun.fitness.health.model.nutrition.NutritionMacros;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

class NutritionMacrosDataExtractorTest {

  @Test
  void extractData() {

    NutritionMacros expected = getExpected();
    NutritionMacrosDataExtractor macrosDataExtractor = new NutritionMacrosDataExtractor();
    NutritionFitnessHealthData nutritionFitnessHealthData = new NutritionFitnessHealthData();
    macrosDataExtractor.extractData(getMacrosData(), nutritionFitnessHealthData);
    NutritionMacros actual = nutritionFitnessHealthData.getMacros();
    assertNotNull(actual);
    assertNutritionMacros(expected, actual);
  }

  private void assertNutritionMacros(NutritionMacros expected, NutritionMacros actual) {

    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getFatG(), actual.getFatG());
    assertEquals(expected.getTransFatG(), actual.getTransFatG());
    assertEquals(expected.getFiberG(), actual.getFiberG());
    assertEquals(expected.getCarbohydratesG(), actual.getCarbohydratesG());
    assertEquals(expected.getProteinG(), actual.getProteinG());
    assertEquals(expected.getCholesterolMg(), actual.getCholesterolMg());
    assertEquals(expected.getSodiumMg(), actual.getSodiumMg());
    assertEquals(expected.getCalories(), actual.getCalories());
    assertEquals(expected.getSugarG(), actual.getSugarG());
    assertEquals(expected.getAlcoholG(), actual.getAlcoholG());
  }

  private NutritionMacros getExpected() {

    NutritionMacros macros = new NutritionMacros();
    macros.setFatG(71d);
    macros.setTransFatG(1d);
    macros.setFiberG(58d);
    macros.setCarbohydratesG(446d);
    macros.setProteinG(74d);
    macros.setCholesterolMg(329d);
    macros.setSodiumMg(2122d);
    macros.setCalories(1691d);
    macros.setSugarG(143d);
    macros.setAlcoholG(0d);
    return macros;
  }

  @SneakyThrows
  private Macros getMacrosData() {

    String data = IOUtils.resourceToString("unit-test-data/nutrition_macros_data.json", UTF_8, this.getClass().getClassLoader());
    return new ObjectMapper().readValue(data, Macros.class);
  }
}