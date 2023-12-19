package team.dev.sun.fitness.health.model.body;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import team.dev.sun.fitness.health.api.model.BloodGlucoseType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "blood_glucose_samples")
public class BloodGlucoseSample implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "type")
  @Enumerated(value = EnumType.STRING)
  private BloodGlucoseType type;

  @Column(name = "timestamp", columnDefinition = "TIMESTAMP")
  private ZonedDateTime timestamp;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "glucose_data_id", nullable = false)
  private Glucose glucose;

  @Column(name = "blood_glucose_mg_per_dl")
  private Double bloodGlucoseMgPerDl;

  @Column(name = "glucose_level_flag")
  private Integer glucoseLevelFlag;

  @Column(name = "trend_arrow")
  private Integer trendArrow;
}
