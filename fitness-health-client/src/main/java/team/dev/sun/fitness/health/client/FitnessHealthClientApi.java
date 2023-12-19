package team.dev.sun.fitness.health.client;

import static team.dev.sun.fitness.health.api.ApiUrls.SEARCH_FITNESS_HEALTH_DATA;

import team.dev.sun.fitness.health.api.dto.FitnessHealthDataDTO;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface FitnessHealthClientApi {

  @GET(SEARCH_FITNESS_HEALTH_DATA)
  Call<FitnessHealthDataDTO> getFitnessHealthData(@QueryMap Map<String, String> searchFilters);
}
