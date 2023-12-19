package team.dev.sun.fitness.health.controller;

import static team.dev.sun.fitness.health.api.ApiUrls.CREATE_FITNESS_HEALTH_DATA;
import static team.dev.sun.fitness.health.api.ApiUrls.SEARCH_FITNESS_HEALTH_DATA;

import team.dev.sun.fitness.health.api.dto.FitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.model.FitnessHealthSearchFilters;
import team.dev.sun.fitness.health.service.FitnessHealthService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Fitness & Health")
@RequiredArgsConstructor
public class FitnessHealthController {

  private final FitnessHealthService fitnessHealthService;

  @Hidden
  @CrossOrigin(origins = {"https://18.133.218.210", "https://18.169.82.189", "https://18.132.162.19"})
  @PostMapping(CREATE_FITNESS_HEALTH_DATA)
  @Operation(summary = "Create new fitness&health data")
  public void processFitnessHealthData(@RequestHeader("terra-signature") String signatureHeader, @RequestBody String payload) {

    fitnessHealthService.processFitnessHealthData(signatureHeader, payload);
  }

  @GetMapping(SEARCH_FITNESS_HEALTH_DATA)
  @Operation(summary = "Search fitness&health data")
  public FitnessHealthDataDTO searchFitnessHealthData(@ModelAttribute @ParameterObject FitnessHealthSearchFilters searchFilters) {

    return fitnessHealthService.searchFitnessHealthData(searchFilters);
  }
}
