package team.dev.sun.fitness.health.model.body;

import static jakarta.persistence.CascadeType.ALL;

import team.dev.sun.fitness.health.model.FitnessHealthData;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "body_fitness_health_data")
public class BodyFitnessHealthData extends FitnessHealthData {

  @OneToMany(mappedBy = "bodyFitnessHealthData", cascade = ALL)
  private List<BloodPressure> bloodPressures;

  @OneToOne(cascade = ALL)
  @JoinColumn(name = "glucose_data_id")
  private Glucose glucose;

  @OneToOne(cascade = ALL)
  @JoinColumn(name = "hydration_data_id")
  private Hydration hydration;

  @OneToMany(mappedBy = "bodyFitnessHealthData", cascade = ALL)
  private List<Measurement> measurements;

  @OneToOne(cascade = ALL)
  @JoinColumn(name = "oxygen_data_id")
  private Oxygen oxygen;

  @OneToMany(mappedBy = "bodyFitnessHealthData", cascade = ALL)
  private List<Temperature> temperatures;
}
