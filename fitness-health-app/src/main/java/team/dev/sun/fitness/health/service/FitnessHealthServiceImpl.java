package team.dev.sun.fitness.health.service;

import static team.dev.sun.fitness.health.api.model.DataType.ACTIVITY;
import static team.dev.sun.fitness.health.api.model.DataType.BODY;
import static team.dev.sun.fitness.health.api.model.DataType.DAILY;
import static team.dev.sun.fitness.health.api.model.DataType.NUTRITION;
import static team.dev.sun.fitness.health.api.model.DataType.SLEEP;
import static java.util.Collections.emptyList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

import team.dev.sun.fitness.health.api.dto.FitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.activity.ActivityFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.body.BodyFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.daily.DailyFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.nutrition.NutritionFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.sleep.SleepFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.model.FitnessHealthSearchFilters;
import team.dev.sun.fitness.health.exception.InvalidSignatureException;
import team.dev.sun.fitness.health.handler.WebhookHandler;
import team.dev.sun.fitness.health.mapper.activity.ActivityFitnessHealthDataMapper;
import team.dev.sun.fitness.health.mapper.body.BodyFitnessHealthDataMapper;
import team.dev.sun.fitness.health.mapper.daily.DailyFitnessHealthDataMapper;
import team.dev.sun.fitness.health.mapper.nutrition.NutritionFitnessHealthDataMapper;
import team.dev.sun.fitness.health.mapper.sleep.SleepFitnessHealthDataMapper;
import team.dev.sun.fitness.health.model.WebhookPayload;
import team.dev.sun.fitness.health.model.activity.ActivityFitnessHealthData;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import team.dev.sun.fitness.health.model.daily.DailyFitnessHealthData;
import team.dev.sun.fitness.health.model.nutrition.NutritionFitnessHealthData;
import team.dev.sun.fitness.health.model.sleep.SleepFitnessHealthData;
import team.dev.sun.fitness.health.parser.WebhookPayloadParser;
import team.dev.sun.fitness.health.persistence.ActivityFitnessHealthDataRepository;
import team.dev.sun.fitness.health.persistence.BodyFitnessHealthDataRepository;
import team.dev.sun.fitness.health.persistence.DailyFitnessHealthDataRepository;
import team.dev.sun.fitness.health.persistence.NutritionFitnessHealthDataRepository;
import team.dev.sun.fitness.health.persistence.SleepFitnessHealthDataRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FitnessHealthServiceImpl implements FitnessHealthService {

  private final ActivityFitnessHealthDataRepository activityDataRepository;

  private final BodyFitnessHealthDataRepository bodyDataRepository;

  private final DailyFitnessHealthDataRepository dailyDataRepository;

  private final NutritionFitnessHealthDataRepository nutritionDataRepository;

  private final SleepFitnessHealthDataRepository sleepDataRepository;

  private final WebhookHandler webhookHandler;

  private final ActivityFitnessHealthDataMapper activityDataMapper;

  private final BodyFitnessHealthDataMapper bodyDataMapper;

  private final DailyFitnessHealthDataMapper dailyDataMapper;

  private final NutritionFitnessHealthDataMapper nutritionDataMapper;

  private final SleepFitnessHealthDataMapper sleepDataMapper;

  private final WebhookPayloadParser payloadParser;

  @Override
  public void processFitnessHealthData(final String signatureHeader, final String payload) {

    if (!webhookHandler.isSignatureValid(signatureHeader, payload)) {
      throw new InvalidSignatureException(signatureHeader);
    }
    WebhookPayload webhookPayload = payloadParser.toWebhookPayload(payload);
    webhookHandler.submitPayload(webhookPayload);
  }

  @Override
  @Transactional(readOnly = true)
  public FitnessHealthDataDTO searchFitnessHealthData(final FitnessHealthSearchFilters searchFilters) {

    FitnessHealthDataDTO fitnessHealthDataDTO = new FitnessHealthDataDTO();
    fitnessHealthDataDTO.setActivityData(searchActivityData(searchFilters));
    fitnessHealthDataDTO.setBodyData(searchBodyData(searchFilters));
    fitnessHealthDataDTO.setDailyData(searchDailyData(searchFilters));
    fitnessHealthDataDTO.setNutritionData(searchNutritionData(searchFilters));
    fitnessHealthDataDTO.setSleepData(searchSleepData(searchFilters));
    return fitnessHealthDataDTO;
  }

  private List<ActivityFitnessHealthDataDTO> searchActivityData(final FitnessHealthSearchFilters searchFilters) {

    if (isEmpty(searchFilters.getDataTypes()) || searchFilters.getDataTypes().contains(ACTIVITY)) {

      List<ActivityFitnessHealthData> activityDataList =
          activityDataRepository.findAll(searchFilters.getClientId(), searchFilters.getUserId(),
                                         searchFilters.getDeviceId(),
                                         searchFilters.getProvider(), searchFilters.getFromDate(),
                                         searchFilters.getToDate()
          );
      return activityDataMapper.map(activityDataList);
    }
    return emptyList();
  }

  private List<BodyFitnessHealthDataDTO> searchBodyData(final FitnessHealthSearchFilters searchFilters) {

    if (isEmpty(searchFilters.getDataTypes()) || searchFilters.getDataTypes().contains(BODY)) {
      List<BodyFitnessHealthData> bodyDataList =
          bodyDataRepository.findAll(searchFilters.getClientId(), searchFilters.getUserId(),
                                     searchFilters.getDeviceId(),
                                     searchFilters.getProvider(), searchFilters.getFromDate(),
                                     searchFilters.getToDate()
          );
      return bodyDataMapper.map(bodyDataList);
    }
    return emptyList();
  }

  private List<DailyFitnessHealthDataDTO> searchDailyData(final FitnessHealthSearchFilters searchFilters) {

    if (isEmpty(searchFilters.getDataTypes()) || searchFilters.getDataTypes().contains(DAILY)) {
      List<DailyFitnessHealthData> dailyDataList =
          dailyDataRepository.findAll(searchFilters.getClientId(), searchFilters.getUserId(),
                                      searchFilters.getDeviceId(),
                                      searchFilters.getProvider(), searchFilters.getFromDate(),
                                      searchFilters.getToDate()
          );
      return dailyDataMapper.map(dailyDataList);
    }
    return emptyList();
  }

  private List<NutritionFitnessHealthDataDTO> searchNutritionData(final FitnessHealthSearchFilters searchFilters) {

    if (isEmpty(searchFilters.getDataTypes()) || searchFilters.getDataTypes().contains(NUTRITION)) {
      List<NutritionFitnessHealthData> nutritionDataList =
          nutritionDataRepository.findAll(searchFilters.getClientId(), searchFilters.getUserId(),
                                          searchFilters.getDeviceId(),
                                          searchFilters.getProvider(), searchFilters.getFromDate(),
                                          searchFilters.getToDate()
          );
      return nutritionDataMapper.map(nutritionDataList);
    }
    return emptyList();
  }

  private List<SleepFitnessHealthDataDTO> searchSleepData(final FitnessHealthSearchFilters searchFilters) {

    if (isEmpty(searchFilters.getDataTypes()) || searchFilters.getDataTypes().contains(SLEEP)) {
      List<SleepFitnessHealthData> sleepDataList =
          sleepDataRepository.findAll(searchFilters.getClientId(), searchFilters.getUserId(),
                                      searchFilters.getDeviceId(),
                                      searchFilters.getProvider(), searchFilters.getFromDate(),
                                      searchFilters.getToDate()
          );
      return sleepDataMapper.map(sleepDataList);
    }
    return emptyList();
  }
}
