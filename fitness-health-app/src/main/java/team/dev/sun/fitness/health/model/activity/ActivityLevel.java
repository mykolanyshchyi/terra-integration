package team.dev.sun.fitness.health.model.activity;

import static jakarta.persistence.GenerationType.IDENTITY;

import team.dev.sun.fitness.health.api.model.ActivityLevelType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "activity_levels_samples")
public class ActivityLevel implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "level")
  @Enumerated(EnumType.STRING)
  private ActivityLevelType level;

  @Column(name = "timestamp", columnDefinition = "TIMESTAMP")
  private ZonedDateTime timestamp;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "active_duration_data_id", nullable = false)
  private ActiveDuration activeDuration;
}
