package team.dev.sun.fitness.health.model.activity;

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
@Table(name = "calories_data")
public class Calories implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "net_intake_calories")
  private Double netIntakeCalories;

  @Column(name = "bmr_calories")
  private Double bmrCalories;

  @Column(name = "total_burned_calories")
  private Double totalBurnedCalories;

  @Column(name = "net_activity_calories")
  private Double netActivityCalories;
}
