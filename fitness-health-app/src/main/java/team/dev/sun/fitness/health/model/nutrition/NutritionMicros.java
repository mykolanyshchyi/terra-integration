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
@Table(name = "nutrition_micros")
public class NutritionMicros implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "selenium_mg")
  private Double seleniumMg;

  @Column(name = "niacin_mg")
  private Double niacinMg;

  @Column(name = "magnesium_mg")
  private Double magnesiumMg;

  @Column(name = "copper_mg")
  private Double copperMg;

  @Column(name = "vitamin_b12_mg")
  private Double vitaminB12Mg;

  @Column(name = "vitamin_b6_mg")
  private Double vitaminB6Mg;

  @Column(name = "vitamin_c_mg")
  private Double vitaminCMg;

  @Column(name = "zinc_mg")
  private Double zincMg;

  @Column(name = "vitamin_e_mg")
  private Double vitaminEMg;

  @Column(name = "manganese_mg")
  private Double manganeseMg;

  @Column(name = "vitamin_d_mg")
  private Double vitaminDMg;

  @Column(name = "iodine_mg")
  private Double iodineMg;

  @Column(name = "chloride_mg")
  private Double chlorideMg;

  @Column(name = "folate_mg")
  private Double folateMg;

  @Column(name = "calcium_mg")
  private Double calciumMg;

  @Column(name = "molybdenum_mg")
  private Double molybdenumMg;

  @Column(name = "vitamin_a_mg")
  private Double vitaminAMg;

  @Column(name = "riboflavin_mg")
  private Double riboflavinMg;

  @Column(name = "folic_acid_mg")
  private Double folicAcidMg;

  @Column(name = "iron_mg")
  private Double ironMg;

  @Column(name = "thiamin_mg")
  private Double thiaminMg;

  @Column(name = "pantothenic_acid_mg")
  private Double pantothenicAcidMg;

  @Column(name = "caffeine_mg")
  private Double caffeineMg;

  @Column(name = "vitamin_k_mg")
  private Double vitaminKMg;

  @Column(name = "chromium_mg")
  private Double chromiumMg;

  @Column(name = "potassium_mg")
  private Double potassiumMg;

  @Column(name = "biotin_mg")
  private Double biotinMg;

  @Column(name = "phosphorus_mg")
  private Double phosphorusMg;
}
