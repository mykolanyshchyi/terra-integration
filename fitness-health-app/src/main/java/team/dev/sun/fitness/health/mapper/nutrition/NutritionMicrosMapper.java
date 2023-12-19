package team.dev.sun.fitness.health.mapper.nutrition;

import team.dev.sun.fitness.health.api.dto.nutrition.NutritionMicrosDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.nutrition.NutritionMicros;
import org.springframework.stereotype.Component;

@Component
public class NutritionMicrosMapper extends AbstractMapper<NutritionMicros, NutritionMicrosDTO> {

  @Override
  public NutritionMicrosDTO map(final NutritionMicros source) {

    return NutritionMicrosDTO.builder()
                             .id(source.getId())
                             .seleniumMg(source.getSeleniumMg())
                             .niacinMg(source.getNiacinMg())
                             .magnesiumMg(source.getMagnesiumMg())
                             .copperMg(source.getCopperMg())
                             .vitaminB12Mg(source.getVitaminB12Mg())
                             .vitaminB6Mg(source.getVitaminB6Mg())
                             .vitaminCMg(source.getVitaminCMg())
                             .zincMg(source.getZincMg())
                             .vitaminEMg(source.getVitaminEMg())
                             .manganeseMg(source.getManganeseMg())
                             .vitaminDMg(source.getVitaminDMg())
                             .iodineMg(source.getIodineMg())
                             .chlorideMg(source.getChlorideMg())
                             .folateMg(source.getFolateMg())
                             .calciumMg(source.getCalciumMg())
                             .molybdenumMg(source.getMolybdenumMg())
                             .vitaminAMg(source.getVitaminAMg())
                             .riboflavinMg(source.getRiboflavinMg())
                             .folicAcidMg(source.getFolicAcidMg())
                             .ironMg(source.getIronMg())
                             .thiaminMg(source.getThiaminMg())
                             .pantothenicAcidMg(source.getPantothenicAcidMg())
                             .caffeineMg(source.getCaffeineMg())
                             .vitaminKMg(source.getVitaminKMg())
                             .chromiumMg(source.getChromiumMg())
                             .potassiumMg(source.getPotassiumMg())
                             .biotinMg(source.getBiotinMg())
                             .phosphorusMg(source.getPhosphorusMg())
                             .build();
  }
}
