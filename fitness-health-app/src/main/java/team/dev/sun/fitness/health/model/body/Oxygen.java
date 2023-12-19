package team.dev.sun.fitness.health.model.body;

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
@Table(name = "oxygen_data")
public class Oxygen implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "avg_saturation_percentage")
  private Double avgSaturationPercentage;

  @Column(name = "vo2max_ml_per_min_per_kg")
  private Double vo2MaxMlPerMinPerKg;

  @OneToMany(mappedBy = "oxygen", cascade = ALL)
  private List<OxygenSaturation> oxygenSaturations;

  @OneToMany(mappedBy = "oxygen", cascade = ALL)
  private List<OxygenVo2> oxygenVo2s;
}
