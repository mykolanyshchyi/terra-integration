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
public class NutritionMacrosDTO implements Serializable {

  private Long id;

  private Double fatG;

  private Double transFatG;

  private Double fiberG;

  private Double carbohydratesG;

  private Double proteinG;

  private Double cholesterolMg;

  private Double sodiumMg;

  private Double calories;

  private Double sugarG;

  private Double alcoholG;
}
