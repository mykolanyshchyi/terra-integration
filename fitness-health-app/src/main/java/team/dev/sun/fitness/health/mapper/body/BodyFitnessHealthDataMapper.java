package team.dev.sun.fitness.health.mapper.body;

import static team.dev.sun.fitness.health.util.ObjectUtil.applyNonNull;

import team.dev.sun.fitness.health.api.dto.body.BodyFitnessHealthDataDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BodyFitnessHealthDataMapper extends AbstractMapper<BodyFitnessHealthData, BodyFitnessHealthDataDTO> {

  private final BloodPressureMapper bloodPressureMapper;

  private final GlucoseMapper glucoseMapper;

  private final HydrationMapper hydrationMapper;

  private final MeasurementMapper measurementMapper;

  private final OxygenMapper oxygenMapper;

  private final TemperatureMapper temperatureMapper;

  @Override
  public BodyFitnessHealthDataDTO map(final BodyFitnessHealthData data) {

    BodyFitnessHealthDataDTO dto = mapBase(data, new BodyFitnessHealthDataDTO());
    dto.setBloodPressures(bloodPressureMapper.map(data.getBloodPressures()));
    dto.setMeasurements(measurementMapper.map(data.getMeasurements()));
    dto.setTemperatures(temperatureMapper.map(data.getTemperatures()));

    applyNonNull(data::getGlucose, dto::setGlucose, glucoseMapper::map);
    applyNonNull(data::getHydration, dto::setHydration, hydrationMapper::map);
    applyNonNull(data::getOxygen, dto::setOxygen, oxygenMapper::map);
    return dto;
  }
}
