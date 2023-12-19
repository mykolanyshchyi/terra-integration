package team.dev.sun.fitness.health.model.sleep;

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
@Table(name = "sleep_durations_asleep")
public class AsleepDuration implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "duration_light_sleep_state_seconds")
  private Double lightSleepStateSeconds;

  @Column(name = "duration_asleep_state_seconds")
  private Double asleepStateSeconds;

  @Column(name = "num_rem_events")
  private Integer numRemEvents;

  @Column(name = "duration_rem_sleep_state_seconds")
  private Double remSleepStateSeconds;

  @Column(name = "duration_deep_sleep_state_seconds")
  private Double deepSleepStateSeconds;
}
