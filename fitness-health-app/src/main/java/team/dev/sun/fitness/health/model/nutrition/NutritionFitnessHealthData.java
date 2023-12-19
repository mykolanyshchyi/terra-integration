package team.dev.sun.fitness.health.model.nutrition;

import static jakarta.persistence.CascadeType.ALL;

import team.dev.sun.fitness.health.model.FitnessHealthData;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "nutrition_fitness_health_data")
public class NutritionFitnessHealthData extends FitnessHealthData {

  @Column(name = "water_ml")
  private Double waterMl;

  @OneToOne(cascade = ALL)
  @JoinColumn(name = "nutrition_macros_id")
  private NutritionMacros macros;

  @OneToOne(cascade = ALL)
  @JoinColumn(name = "nutrition_micros_id")
  private NutritionMicros micros;
}
