package team.dev.sun.fitness.health.extractor.nutrition;

import co.tryterra.terraclient.models.v2.common.Micros;
import team.dev.sun.fitness.health.extractor.AbstractDataExtractor;
import team.dev.sun.fitness.health.model.nutrition.NutritionFitnessHealthData;
import team.dev.sun.fitness.health.model.nutrition.NutritionMicros;
import org.springframework.stereotype.Component;

@Component
public class NutritionMicrosDataExtractor extends AbstractDataExtractor<Micros, NutritionFitnessHealthData> {

  @Override
  protected void extractData(final Micros source, final NutritionFitnessHealthData entity) {

    NutritionMicros nutritionMicros = new NutritionMicros();
    nutritionMicros.setSeleniumMg(source.getSeleniumMg());
    nutritionMicros.setNiacinMg(source.getNiacinMg());
    nutritionMicros.setMagnesiumMg(source.getMagnesiumMg());
    nutritionMicros.setCopperMg(source.getCopperMg());
    nutritionMicros.setVitaminB12Mg(source.getVitaminB12Mg());
    nutritionMicros.setVitaminB6Mg(source.getVitaminB6Mg());
    nutritionMicros.setVitaminCMg(source.getVitaminCMg());
    nutritionMicros.setZincMg(source.getZincMg());
    nutritionMicros.setVitaminEMg(source.getVitaminEMg());
    nutritionMicros.setManganeseMg(source.getManganeseMg());
    nutritionMicros.setVitaminDMg(source.getVitaminDMg());
    nutritionMicros.setIodineMg(source.getIodineMg());
    nutritionMicros.setChlorideMg(source.getChlorideMg());
    nutritionMicros.setFolateMg(source.getFolateMg());
    nutritionMicros.setCalciumMg(source.getCalciumMg());
    nutritionMicros.setMolybdenumMg(source.getMolybdenumMg());
    nutritionMicros.setVitaminAMg(source.getVitaminAMg());
    nutritionMicros.setRiboflavinMg(source.getRiboflavinMg());
    nutritionMicros.setFolicAcidMg(source.getFolicAcidMg());
    nutritionMicros.setIronMg(source.getIronMg());
    nutritionMicros.setThiaminMg(source.getThiaminMg());
    nutritionMicros.setPantothenicAcidMg(source.getPantothenicAcid_mg());
    nutritionMicros.setCaffeineMg(source.getCaffeineMg());
    nutritionMicros.setVitaminKMg(source.getVitaminKMg());
    nutritionMicros.setChromiumMg(source.getChromiumMg());
    nutritionMicros.setPotassiumMg(source.getPotassiumMg());
    nutritionMicros.setBiotinMg(source.getBiotinMg());
    nutritionMicros.setPhosphorusMg(source.getPhosphorusMg());
    entity.setMicros(nutritionMicros);
  }
}
