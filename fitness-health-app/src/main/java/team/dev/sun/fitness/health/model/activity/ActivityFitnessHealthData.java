package team.dev.sun.fitness.health.model.activity;

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
@Table(name = "activity_fitness_health_data")
public class ActivityFitnessHealthData extends FitnessHealthData {

  @OneToOne(cascade = ALL)
  @JoinColumn(name = "distance_id")
  private Distance distance;

  @OneToOne(cascade = ALL)
  @JoinColumn(name = "active_duration_id")
  private ActiveDuration activeDuration;

  @OneToOne(cascade = ALL)
  @JoinColumn(name = "calories_data_id")
  private Calories calories;
}
