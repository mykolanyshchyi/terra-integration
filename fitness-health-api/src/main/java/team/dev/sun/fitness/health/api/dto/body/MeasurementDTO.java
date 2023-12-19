package team.dev.sun.fitness.health.api.dto.body;

import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDTO implements Serializable {

  private Long id;

  private ZonedDateTime measurementTime;

  private Double bmi;

  private Double bmr;

  private Double rmr;

  private Integer estimatedFitnessAge;

  private Double skinFoldMm;

  private Double bodyfatPercentage;

  private Double weightKg;

  private Double heightCm;

  private Double boneMassG;

  private Double muscleMassG;

  private Double leanMassG;

  private Double waterPercentage;

  private Double insulinUnits;

  private String insulinType;

  private String urineColor;
}
