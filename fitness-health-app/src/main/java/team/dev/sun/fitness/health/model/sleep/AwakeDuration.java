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
@Table(name = "sleep_durations_awake")
public class AwakeDuration implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "duration_short_interruption_seconds")
  private Double shortInterruptionSeconds;

  @Column(name = "duration_awake_state_seconds")
  private Double awakeStateSeconds;

  @Column(name = "duration_long_interruption_seconds")
  private Double longInterruptionSeconds;

  @Column(name = "num_wakeup_events")
  private Integer numWakeupEvents;

  @Column(name = "wake_up_latency_seconds")
  private Double wakeUpLatencySeconds;

  @Column(name = "num_out_of_bed_events")
  private Integer numOutOfBedEvents;

  @Column(name = "sleep_latency_seconds")
  private Double sleepLatencySeconds;
}
