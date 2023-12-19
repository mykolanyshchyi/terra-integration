package team.dev.sun.fitness.health.mapper.body;

import static org.junit.jupiter.api.Assertions.assertEquals;

import team.dev.sun.fitness.health.api.dto.body.MeasurementDTO;
import team.dev.sun.fitness.health.model.body.Measurement;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class MeasurementMapperTest {

  private static final Long ID = 657L;

  private static final ZonedDateTime MEASUREMENT_TIME = ZonedDateTime.now();

  private static final Double BMI = 13d;

  private static final Double BMR = 12d;

  private static final Double RMR = 11d;

  private static final Integer ESTIMATED_FITNESS_AGE = 10;

  private static final Double SKIN_FOLD_MM = 9d;

  private static final Double BODY_FAT_PERCENTAGE = 8d;

  private static final Double WEIGHT_KG = 7d;

  private static final Double HEIGHT_CM = 6d;

  private static final Double BONE_MASS_G = 5d;

  private static final Double MUSCLE_MASS_G = 4d;

  private static final Double LEAN_MASS_G = 3d;

  private static final Double WATER_PERCENTAGE = 2d;

  private static final Double INSULIN_UNITS = 1d;

  private static final String INSULIN_TYPE = "some type";

  private static final String URINE_COLOR = "yellow";

  @Test
  void mapMeasurement() {

    MeasurementDTO expected = getDto();
    MeasurementMapper mapper = new MeasurementMapper();
    MeasurementDTO actual = mapper.map(getEntity());
    assertEquals(expected, actual);
  }

  private Measurement getEntity() {

    Measurement measurement = new Measurement();
    measurement.setId(ID);
    measurement.setMeasurementTime(MEASUREMENT_TIME);
    measurement.setBmi(BMI);
    measurement.setBmr(BMR);
    measurement.setRmr(RMR);
    measurement.setEstimatedFitnessAge(ESTIMATED_FITNESS_AGE);
    measurement.setSkinFoldMm(SKIN_FOLD_MM);
    measurement.setBodyfatPercentage(BODY_FAT_PERCENTAGE);
    measurement.setWeightKg(WEIGHT_KG);
    measurement.setHeightCm(HEIGHT_CM);
    measurement.setBoneMassG(BONE_MASS_G);
    measurement.setMuscleMassG(MUSCLE_MASS_G);
    measurement.setLeanMassG(LEAN_MASS_G);
    measurement.setWaterPercentage(WATER_PERCENTAGE);
    measurement.setInsulinUnits(INSULIN_UNITS);
    measurement.setInsulinType(INSULIN_TYPE);
    measurement.setUrineColor(URINE_COLOR);
    return measurement;
  }

  private MeasurementDTO getDto() {

    return MeasurementDTO.builder()
                         .id(ID)
                         .measurementTime(MEASUREMENT_TIME)
                         .bmi(BMI)
                         .bmr(BMR)
                         .rmr(RMR)
                         .estimatedFitnessAge(ESTIMATED_FITNESS_AGE)
                         .skinFoldMm(SKIN_FOLD_MM)
                         .bodyfatPercentage(BODY_FAT_PERCENTAGE)
                         .weightKg(WEIGHT_KG)
                         .heightCm(HEIGHT_CM)
                         .boneMassG(BONE_MASS_G)
                         .muscleMassG(MUSCLE_MASS_G)
                         .leanMassG(LEAN_MASS_G)
                         .waterPercentage(WATER_PERCENTAGE)
                         .insulinUnits(INSULIN_UNITS)
                         .insulinType(INSULIN_TYPE)
                         .urineColor(URINE_COLOR)
                         .build();
  }
}