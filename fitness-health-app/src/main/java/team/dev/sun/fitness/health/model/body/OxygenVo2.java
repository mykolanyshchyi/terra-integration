package team.dev.sun.fitness.health.model.body;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "oxygen_vo2")
public class OxygenVo2 implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "vo2max_ml_per_min_per_kg")
  private Double vo2MaxMlPerMinPerKg;

  @Column(name = "timestamp", columnDefinition = "TIMESTAMP")
  private ZonedDateTime timestamp;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "oxygen_data_id", nullable = false)
  private Oxygen oxygen;
}
