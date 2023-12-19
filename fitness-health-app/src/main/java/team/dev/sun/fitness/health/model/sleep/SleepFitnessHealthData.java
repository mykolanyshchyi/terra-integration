package team.dev.sun.fitness.health.model.sleep;

import static jakarta.persistence.CascadeType.ALL;

import team.dev.sun.fitness.health.model.FitnessHealthData;
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
@Table(name = "sleep_fitness_health_data")
public class SleepFitnessHealthData extends FitnessHealthData {

  @OneToOne(cascade = ALL)
  @JoinColumn(name = "awake_duration_id")
  private AwakeDuration awakeDuration;

  @OneToOne(cascade = ALL)
  @JoinColumn(name = "asleep_duration_id")
  private AsleepDuration asleepDuration;
}
