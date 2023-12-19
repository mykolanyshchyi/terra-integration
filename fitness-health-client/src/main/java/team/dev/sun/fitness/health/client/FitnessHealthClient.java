package team.dev.sun.fitness.health.client;

import static java.util.stream.Collectors.joining;

import team.dev.sun.fitness.health.api.dto.FitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.model.DataType;
import team.dev.sun.fitness.health.api.model.FitnessHealthSearchFilters;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FitnessHealthClient {

  private final FitnessHealthClientApi api;

  public FitnessHealthClient(final Retrofit retrofit) {

    this.api = retrofit.create(FitnessHealthClientApi.class);
  }


  public FitnessHealthDataDTO searchFitnessHealthData(FitnessHealthSearchFilters searchFilters) throws FitnessHealthClientException {

    Map<String, String> filters = getRequestFilters(searchFilters);
    Call<FitnessHealthDataDTO> fitnessHealthDataSyncCall = api.getFitnessHealthData(filters);
    Response<FitnessHealthDataDTO> response = execute(fitnessHealthDataSyncCall);
    return response.body();
  }

  private Response<FitnessHealthDataDTO> execute(Call<FitnessHealthDataDTO> call) throws FitnessHealthClientException {

    try {
      return call.execute();
    } catch (IOException e) {
      throw new FitnessHealthClientException(e);
    }
  }

  private Map<String, String> getRequestFilters(FitnessHealthSearchFilters searchFilters) {
    Map<String, String> filters = new HashMap<>();
    addDataTypes(filters, searchFilters.getDataTypes());
    addDeviceId(filters, searchFilters.getDeviceId());
    addIfValueNotNull(filters, "clientId", searchFilters.getClientId());
    addIfValueNotNull(filters, "userId", searchFilters.getUserId());
    addIfValueNotNull(filters, "provider", searchFilters.getProvider());
    addIfValueNotNull(filters, "fromDate", searchFilters.getFromDate());
    addIfValueNotNull(filters, "toDate", searchFilters.getToDate());
    addIfValueNotNull(filters, "dateRange", searchFilters.getDateRange());
    return filters;
  }

  private void addIfValueNotNull(Map<String, String> filters, String key, Long value) {

    if (value != null) {
      filters.put(key, String.valueOf(value));
    }
  }

  private void addIfValueNotNull(Map<String, String> filters, String key, Enum<?> value) {

    if (value != null) {
      filters.put(key, value.name());
    }
  }

  private void addIfValueNotNull(Map<String, String> filters, String key, ZonedDateTime value) {

    if (value != null) {
      filters.put(key, DateTimeFormatter.ISO_DATE_TIME.format(value));
    }
  }

  private void addDataTypes(Map<String, String> filters, Set<DataType> dataTypes) {

    if (dataTypes != null && !dataTypes.isEmpty()) {
      filters.put("dataTypes", dataTypes.stream().map(DataType::name).collect(joining(",")));
    }
  }

  private void addDeviceId(Map<String, String> filters, String deviceId) {

    if (deviceId != null) {
      filters.put("deviceId", deviceId);
    }
  }
}
