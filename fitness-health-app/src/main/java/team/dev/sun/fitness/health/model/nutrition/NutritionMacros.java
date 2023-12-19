package team.dev.sun.fitness.health.model.nutrition;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "nutrition_macros")
public class NutritionMacros implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "fat_g")
  private Double fatG;

  @Column(name = "trans_fat_g")
  private Double transFatG;

  @Column(name = "fiber_g")
  private Double fiberG;

  @Column(name = "carbohydrates_g")
  private Double carbohydratesG;

  @Column(name = "protein_g")
  private Double proteinG;

  @Column(name = "cholesterol_mg")
  private Double cholesterolMg;

  @Column(name = "sodium_mg")
  private Double sodiumMg;

  @Column(name = "calories")
  private Double calories;

  @Column(name = "sugar_g")
  private Double sugarG;

  @Column(name = "alcohol_g")
  private Double alcoholG;
}
