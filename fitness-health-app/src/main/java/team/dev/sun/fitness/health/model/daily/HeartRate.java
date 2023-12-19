package team.dev.sun.fitness.health.model.daily;

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
@Table(name = "heart_rate_data")
public class HeartRate implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "max_hr_bpm")
  private Integer maxHrBpm;

  @Column(name = "resting_hr_bpm")
  private Integer restingHrBpm;

  @Column(name = "avg_hrv_rmssd")
  private Double avgHrvRmssd;

  @Column(name = "min_hr_bpm")
  private Integer minHrBpm;

  @Column(name = "user_max_hr_bpm")
  private Integer userMaxHrBpm;

  @Column(name = "avg_hrv_sdnn")
  private Double avgHrvSdnn;

  @Column(name = "avg_hr_bpm")
  private Integer avgHrBpm;
}
