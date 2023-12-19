package team.dev.sun.fitness.health.model.activity;

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
@Table(name = "active_duration_data")
public class ActiveDuration implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "activity_seconds")
  private Double activitySeconds;

  @Column(name = "rest_seconds")
  private Double restSeconds;

  @Column(name = "low_intensity_seconds")
  private Double lowIntensitySeconds;

  @Column(name = "vigorous_intensity_seconds")
  private Double vigorousIntensitySeconds;

  @Column(name = "num_continuous_inactive_periods")
  private Integer numContinuousInactivePeriods;

  @Column(name = "inactivity_seconds")
  private Double inactivitySeconds;

  @Column(name = "moderate_intensity_seconds")
  private Double moderateIntensitySeconds;

  @OneToMany(mappedBy = "activeDuration", cascade = ALL)
  private List<ActivityLevel> activityLevels;
}
