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
@Table(name = "hydration_data")
public class Hydration implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "day_total_water_consumption_ml")
  private Integer dayTotalWaterConsumptionMl;

  @OneToMany(mappedBy = "hydration", cascade = ALL)
  private List<HydrationLevel> hydrationLevels;

  @OneToMany(mappedBy = "hydration", cascade = ALL)
  private List<HydrationMeasurement> hydrationMeasurements;
}
