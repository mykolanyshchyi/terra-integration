package team.dev.sun.fitness.health.model.daily;

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
@Table(name = "daily_fitness_health_data")
public class DailyFitnessHealthData extends FitnessHealthData {

  @OneToOne(cascade = ALL)
  @JoinColumn(name = "heart_rate_data_id")
  private HeartRate heartRate;

  @OneToOne(cascade = ALL)
  @JoinColumn(name = "daily_score_id")
  private Score score;

  @OneToOne(cascade = ALL)
  @JoinColumn(name = "stress_data_id")
  private Stress stress;
}
