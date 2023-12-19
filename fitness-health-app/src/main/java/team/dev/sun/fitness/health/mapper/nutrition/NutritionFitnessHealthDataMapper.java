package team.dev.sun.fitness.health.mapper.nutrition;

import static team.dev.sun.fitness.health.util.ObjectUtil.applyNonNull;

import team.dev.sun.fitness.health.api.dto.nutrition.NutritionFitnessHealthDataDTO;
import team.dev.sun.fitness.health.mapper.AbstractMapper;
import team.dev.sun.fitness.health.model.nutrition.NutritionFitnessHealthData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NutritionFitnessHealthDataMapper extends AbstractMapper<NutritionFitnessHealthData, NutritionFitnessHealthDataDTO> {

  private final NutritionMacrosMapper nutritionMacrosMapper;

  private final NutritionMicrosMapper nutritionMicrosMapper;

  @Override
  public NutritionFitnessHealthDataDTO map(final NutritionFitnessHealthData source) {

    NutritionFitnessHealthDataDTO dto = mapBase(source, new NutritionFitnessHealthDataDTO());
    applyNonNull(source::getMacros, dto::setMacros, nutritionMacrosMapper::map);
    applyNonNull(source::getMicros, dto::setMicros, nutritionMicrosMapper::map);
    return dto;
  }
}
