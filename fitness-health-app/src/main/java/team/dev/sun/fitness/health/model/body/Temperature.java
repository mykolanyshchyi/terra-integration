package team.dev.sun.fitness.health.model.body;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import team.dev.sun.fitness.health.api.model.TemperatureType;
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
@Table(name = "temperature_data")
public class Temperature implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private TemperatureType type;

  @Column(name = "temperature_celsius")
  private Double temperatureCelsius;

  @Column(name = "timestamp", columnDefinition = "TIMESTAMP")
  private ZonedDateTime timestamp;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "body_fitness_health_data_id", nullable = false)
  private BodyFitnessHealthData bodyFitnessHealthData;
}
