package team.dev.sun.fitness.health.mapper.body;

import team.dev.sun.fitness.health.api.dto.body.MeasurementDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.body.Measurement;
import org.springframework.stereotype.Component;

@Component
public class MeasurementMapper extends AbstractMapper<Measurement, MeasurementDTO> {

  @Override
  public MeasurementDTO map(final Measurement source) {

    return MeasurementDTO.builder()
                         .id(source.getId())
                         .measurementTime(source.getMeasurementTime())
                         .bmi(source.getBmi())
                         .bmr(source.getBmr())
                         .rmr(source.getRmr())
                         .estimatedFitnessAge(source.getEstimatedFitnessAge())
                         .skinFoldMm(source.getSkinFoldMm())
                         .bodyfatPercentage(source.getBodyfatPercentage())
                         .weightKg(source.getWeightKg())
                         .heightCm(source.getHeightCm())
                         .boneMassG(source.getBoneMassG())
                         .muscleMassG(source.getMuscleMassG())
                         .leanMassG(source.getLeanMassG())
                         .waterPercentage(source.getWaterPercentage())
                         .insulinUnits(source.getInsulinUnits())
                         .insulinType(source.getInsulinType())
                         .urineColor(source.getUrineColor())
                         .build();
  }
}
