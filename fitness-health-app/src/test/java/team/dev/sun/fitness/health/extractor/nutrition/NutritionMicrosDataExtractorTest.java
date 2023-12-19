package team.dev.sun.fitness.health.extractor.nutrition;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import co.tryterra.terraclient.models.v2.common.Micros;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.dev.sun.fitness.health.model.nutrition.NutritionFitnessHealthData;
import team.dev.sun.fitness.health.model.nutrition.NutritionMicros;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

class NutritionMicrosDataExtractorTest {

  @Test
  void extractData() {

    NutritionMicros expected = getExpected();
    NutritionMicrosDataExtractor microsDataExtractor = new NutritionMicrosDataExtractor();
    NutritionFitnessHealthData nutritionFitnessHealthData = new NutritionFitnessHealthData();
    microsDataExtractor.extractData(getMicrosData(), nutritionFitnessHealthData);
    NutritionMicros actual = nutritionFitnessHealthData.getMicros();
    assertNotNull(actual);
    assertNutritionMicros(expected, actual);
  }

  private void assertNutritionMicros(NutritionMicros expected, NutritionMicros actual) {

    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getSeleniumMg(), actual.getSeleniumMg());
    assertEquals(expected.getNiacinMg(), actual.getNiacinMg());
    assertEquals(expected.getMagnesiumMg(), actual.getMagnesiumMg());
    assertEquals(expected.getCopperMg(), actual.getCopperMg());
    assertEquals(expected.getVitaminB12Mg(), actual.getVitaminB12Mg());
    assertEquals(expected.getVitaminB6Mg(), actual.getVitaminB6Mg());
    assertEquals(expected.getVitaminCMg(), actual.getVitaminCMg());
    assertEquals(expected.getZincMg(), actual.getZincMg());
    assertEquals(expected.getVitaminEMg(), actual.getVitaminEMg());
    assertEquals(expected.getManganeseMg(), actual.getManganeseMg());
    assertEquals(expected.getVitaminDMg(), actual.getVitaminDMg());
    assertEquals(expected.getIodineMg(), actual.getIodineMg());
    assertEquals(expected.getChlorideMg(), actual.getChlorideMg());
    assertEquals(expected.getFolateMg(), actual.getFolateMg());
    assertEquals(expected.getCalciumMg(), actual.getCalciumMg());
    assertEquals(expected.getMolybdenumMg(), actual.getMolybdenumMg());
    assertEquals(expected.getVitaminAMg(), actual.getVitaminAMg());
    assertEquals(expected.getRiboflavinMg(), actual.getRiboflavinMg());
    assertEquals(expected.getFolicAcidMg(), actual.getFolicAcidMg());
    assertEquals(expected.getIronMg(), actual.getIronMg());
    assertEquals(expected.getThiaminMg(), actual.getThiaminMg());
    assertEquals(expected.getPantothenicAcidMg(), actual.getPantothenicAcidMg());
    assertEquals(expected.getCaffeineMg(), actual.getCaffeineMg());
    assertEquals(expected.getVitaminKMg(), actual.getVitaminKMg());
    assertEquals(expected.getChromiumMg(), actual.getChromiumMg());
    assertEquals(expected.getPotassiumMg(), actual.getPotassiumMg());
    assertEquals(expected.getBiotinMg(), actual.getBiotinMg());
    assertEquals(expected.getPhosphorusMg(), actual.getPhosphorusMg());
  }

  private NutritionMicros getExpected() {

    NutritionMicros micros = new NutritionMicros();
    micros.setSeleniumMg(717d);
    micros.setNiacinMg(462d);
    micros.setMagnesiumMg(555d);
    micros.setCopperMg(371d);
    micros.setVitaminB12Mg(345d);
    micros.setVitaminB6Mg(97d);
    micros.setVitaminCMg(334d);
    micros.setZincMg(628d);
    micros.setVitaminEMg(1694d);
    micros.setManganeseMg(738d);
    micros.setVitaminDMg(215d);
    micros.setIodineMg(87d);
    micros.setChlorideMg(65d);
    micros.setFolateMg(582d);
    micros.setCalciumMg(599d);
    micros.setMolybdenumMg(498d);
    micros.setVitaminAMg(825d);
    micros.setRiboflavinMg(502d);
    micros.setFolicAcidMg(78d);
    micros.setIronMg(774d);
    micros.setThiaminMg(258d);
    micros.setPantothenicAcidMg(547d);
    micros.setCaffeineMg(381d);
    micros.setVitaminKMg(696d);
    micros.setChromiumMg(21d);
    micros.setPotassiumMg(798d);
    micros.setBiotinMg(54d);
    micros.setPhosphorusMg(370d);
    return micros;
  }

  @SneakyThrows
  private Micros getMicrosData() {

    String data = IOUtils.resourceToString("unit-test-data/nutrition_micros_data.json", UTF_8, this.getClass().getClassLoader());
    return new ObjectMapper().readValue(data, Micros.class);
  }
}