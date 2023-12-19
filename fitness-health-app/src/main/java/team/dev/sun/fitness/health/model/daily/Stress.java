package team.dev.sun.fitness.health.model.daily;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
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
@Table(name = "stress_data")
public class Stress implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "rest_stress_duration_seconds")
  private Double restStressDurationSeconds;

  @Column(name = "stress_duration_seconds")
  private Double stressDurationSeconds;

  @Column(name = "activity_stress_duration_seconds")
  private Double activityStressDurationSeconds;

  @Column(name = "avg_stress_level")
  private Double avgStressLevel;

  @Column(name = "low_stress_duration_seconds")
  private Double lowStressDurationSeconds;

  @Column(name = "medium_stress_duration_seconds")
  private Double mediumStressDurationSeconds;

  @Column(name = "high_stress_duration_seconds")
  private Double highStressDurationSeconds;

  @Column(name = "max_stress_level")
  private Double maxStressLevel;

  @OneToMany(mappedBy = "stress", cascade = ALL)
  private List<StressSample> samples;
}
