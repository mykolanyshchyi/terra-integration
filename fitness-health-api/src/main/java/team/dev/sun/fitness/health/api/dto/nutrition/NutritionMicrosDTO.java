package team.dev.sun.fitness.health.api.dto.nutrition;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NutritionMicrosDTO implements Serializable {

  private Long id;

  private Double seleniumMg;

  private Double niacinMg;

  private Double magnesiumMg;

  private Double copperMg;

  private Double vitaminB12Mg;

  private Double vitaminB6Mg;

  private Double vitaminCMg;

  private Double zincMg;

  private Double vitaminEMg;

  private Double manganeseMg;

  private Double vitaminDMg;

  private Double iodineMg;

  private Double chlorideMg;

  private Double folateMg;

  private Double calciumMg;

  private Double molybdenumMg;

  private Double vitaminAMg;

  private Double riboflavinMg;

  private Double folicAcidMg;

  private Double ironMg;

  private Double thiaminMg;

  private Double pantothenicAcidMg;

  private Double caffeineMg;

  private Double vitaminKMg;

  private Double chromiumMg;

  private Double potassiumMg;

  private Double biotinMg;

  private Double phosphorusMg;
}
