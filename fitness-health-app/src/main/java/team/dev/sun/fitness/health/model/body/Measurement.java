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
@Table(name = "measurements")
public class Measurement implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "measurement_time", columnDefinition = "TIMESTAMP")
  private ZonedDateTime measurementTime;

  @Column(name = "bmi")
  private Double bmi;

  @Column(name = "bmr")
  private Double bmr;

  @Column(name = "rmr")
  private Double rmr;

  @Column(name = "estimated_fitness_age")
  private Integer estimatedFitnessAge;

  @Column(name = "skin_fold_mm")
  private Double skinFoldMm;

  @Column(name = "bodyfat_percentage")
  private Double bodyfatPercentage;

  @Column(name = "weight_kg")
  private Double weightKg;

  @Column(name = "height_cm")
  private Double heightCm;

  @Column(name = "bone_mass_g")
  private Double boneMassG;

  @Column(name = "muscle_mass_g")
  private Double muscleMassG;

  @Column(name = "lean_mass_g")
  private Double leanMassG;

  @Column(name = "water_percentage")
  private Double waterPercentage;

  @Column(name = "insulin_units")
  private Double insulinUnits;

  @Column(name = "insulin_type")
  private String insulinType;

  @Column(name = "urine_color")
  private String urineColor;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "body_fitness_health_data_id", nullable = false)
  private BodyFitnessHealthData bodyFitnessHealthData;
}
