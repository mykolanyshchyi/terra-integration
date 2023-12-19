package team.dev.sun.fitness.health.api.dto.body;

import team.dev.sun.fitness.health.api.dto.BaseFitnessHealthDataDTO;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BodyFitnessHealthDataDTO extends BaseFitnessHealthDataDTO implements Serializable {

  private List<BloodPressureDTO> bloodPressures;

  private GlucoseDTO glucose;

  private HydrationDTO hydration;

  private List<MeasurementDTO> measurements;

  private OxygenDTO oxygen;

  private List<TemperatureDTO> temperatures;
}
