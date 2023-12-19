package team.dev.sun.fitness.health.controller;

import static java.util.Arrays.deepEquals;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import team.dev.sun.fitness.health.api.dto.FitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.activity.ActiveDurationDTO;
import team.dev.sun.fitness.health.api.dto.activity.ActivityFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.body.BodyFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.body.GlucoseDTO;
import team.dev.sun.fitness.health.api.dto.body.HydrationDTO;
import team.dev.sun.fitness.health.api.dto.body.MeasurementDTO;
import team.dev.sun.fitness.health.api.dto.body.OxygenDTO;
import team.dev.sun.fitness.health.api.dto.body.TemperatureDTO;
import team.dev.sun.fitness.health.api.dto.daily.DailyFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.nutrition.NutritionFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.sleep.SleepFitnessHealthDataDTO;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FitnessHealthDataAssertions {

  public static void assertEqualsFitnessHealthData(FitnessHealthDataDTO expected, FitnessHealthDataDTO actual) {

    assertEqualsActivityData(expected.getActivityData(), actual.getActivityData());
    assertEqualsBodyData(expected.getBodyData(), actual.getBodyData());
    assertEqualsDailyData(expected.getDailyData(), actual.getDailyData());
    assertEqualsNutritionData(expected.getNutritionData(), actual.getNutritionData());
    assertEqualsSleepData(expected.getSleepData(), actual.getSleepData());
  }

  private static void assertEqualsActivityData(List<ActivityFitnessHealthDataDTO> expected,
                                               List<ActivityFitnessHealthDataDTO> actual) {

    if (isEmpty(expected)) {
      assertTrue(isEmpty(actual));
    } else {
      assertTrue(isNotEmpty(actual));
      assertEquals(expected.size(), actual.size());
      for (int i = 0; i < expected.size(); i++) {
        ActivityFitnessHealthDataDTO e = expected.get(i);
        ActivityFitnessHealthDataDTO a = actual.get(i);
        assertEqualsActiveDuration(e.getActiveDuration(), a.getActiveDuration());
        assertEquals(e.getCalories(), a.getCalories());
        assertEquals(e.getDistance(), a.getDistance());
        assertEquals(e.getMetadata(), a.getMetadata());
      }
    }
  }

  private static void assertEqualsActiveDuration(ActiveDurationDTO expected, ActiveDurationDTO actual) {

    if (expected == null) {
      assertNull(actual);
    } else {
      assertNotNull(actual);
      if (isNotEmpty(expected.getActivityLevels())) {
        assertTrue(isNotEmpty(actual.getActivityLevels()));
        assertTrue(deepEquals(expected.getActivityLevels().toArray(), actual.getActivityLevels().toArray()));
        expected.setActivityLevels(null);
        actual.setActivityLevels(null);
      }
      assertEquals(expected, actual);
    }
  }

  private static void assertEqualsBodyData(List<BodyFitnessHealthDataDTO> expected, List<BodyFitnessHealthDataDTO> actual) {

    if (isEmpty(expected)) {
      assertTrue(isEmpty(actual));
    } else {
      assertTrue(isNotEmpty(actual));
      assertEquals(expected.size(), actual.size());
      for (int i = 0; i < expected.size(); i++) {
        BodyFitnessHealthDataDTO e = expected.get(i);
        BodyFitnessHealthDataDTO a = actual.get(i);

        if (isEmpty(e.getBloodPressures())) {
          assertTrue(isEmpty(a.getBloodPressures()));
        } else {
          assertFalse(isEmpty(a.getBloodPressures()));
          assertTrue(deepEquals(e.getBloodPressures().toArray(), a.getBloodPressures().toArray()));
        }

        assertEqualGlucose(e.getGlucose(), a.getGlucose());
        assertEqualHydration(e.getHydration(), a.getHydration());
        assertEqualMeasurement(e.getMeasurements(), a.getMeasurements());
        assertEqualOxygen(e.getOxygen(), a.getOxygen());
        assertEqualTemperature(e.getTemperatures(), a.getTemperatures());
        assertEquals(e.getMetadata(), a.getMetadata());
      }
    }
  }

  private static void assertEqualGlucose(GlucoseDTO expected, GlucoseDTO actual) {

    if (expected == null) {
      assertNull(actual);
    } else {
      assertNotNull(actual);
      assertEquals(expected.getDayAvgBloodGlucoseMgPerDl(), actual.getDayAvgBloodGlucoseMgPerDl());
      if (isEmpty(expected.getSamples())) {
        assertTrue(isEmpty(actual.getSamples()));
      } else {
        assertFalse(isEmpty(actual.getSamples()));
        assertTrue(deepEquals(expected.getSamples().toArray(), actual.getSamples().toArray()));
      }
    }
  }

  private static void assertEqualHydration(HydrationDTO expected, HydrationDTO actual) {

    if (expected == null) {
      assertNull(actual);
    } else {
      assertNotNull(actual);
      assertEquals(expected.getDayTotalWaterConsumptionMl(), actual.getDayTotalWaterConsumptionMl());

      if (isEmpty(expected.getHydrationLevels())) {
        assertTrue(isEmpty(actual.getHydrationLevels()));
      } else {
        assertFalse(isEmpty(actual.getHydrationLevels()));
        assertTrue(deepEquals(expected.getHydrationLevels().toArray(), actual.getHydrationLevels().toArray()));
      }

      if (isEmpty(expected.getHydrationMeasurements())) {
        assertTrue(isEmpty(actual.getHydrationMeasurements()));
      } else {
        assertFalse(isEmpty(actual.getHydrationMeasurements()));
        assertTrue(deepEquals(expected.getHydrationMeasurements().toArray(), actual.getHydrationMeasurements().toArray()));
      }
    }
  }

  private static void assertEqualMeasurement(List<MeasurementDTO> expected, List<MeasurementDTO> actual) {

    if (isEmpty(expected)) {
      assertTrue(isEmpty(actual));
    } else {
      assertFalse(isEmpty(actual));
      assertTrue(deepEquals(expected.toArray(), actual.toArray()));
    }
  }

  private static void assertEqualOxygen(OxygenDTO expected, OxygenDTO actual) {

    if (expected == null) {
      assertNull(actual);
    } else {
      assertEquals(expected.getAvgSaturationPercentage(), actual.getAvgSaturationPercentage());
      assertEquals(expected.getVo2MaxMlPerMinPerKg(), actual.getVo2MaxMlPerMinPerKg());

      if (isEmpty(expected.getOxygenSaturations())) {
        assertTrue(isEmpty(actual.getOxygenSaturations()));
      } else {
        assertFalse(isEmpty(actual.getOxygenSaturations()));
        assertTrue(deepEquals(expected.getOxygenSaturations().toArray(), actual.getOxygenSaturations().toArray()));
      }

      if (isEmpty(expected.getOxygenVo2s())) {
        assertTrue(isEmpty(actual.getOxygenVo2s()));
      } else {
        assertFalse(isEmpty(actual.getOxygenVo2s()));
        assertTrue(deepEquals(expected.getOxygenVo2s().toArray(), actual.getOxygenVo2s().toArray()));
      }
    }
  }

  private static void assertEqualTemperature(List<TemperatureDTO> expected, List<TemperatureDTO> actual) {

    if (isEmpty(expected)) {
      assertTrue(isEmpty(actual));
    } else {
      assertFalse(isEmpty(actual));
      assertTrue(deepEquals(expected.toArray(), actual.toArray()));
    }
  }

  private static void assertEqualsDailyData(List<DailyFitnessHealthDataDTO> expected, List<DailyFitnessHealthDataDTO> actual) {

    if (isEmpty(expected)) {
      assertTrue(isEmpty(actual));
    } else {
      assertTrue(isNotEmpty(actual));
      assertEquals(expected.size(), actual.size());
      for (int i = 0; i < expected.size(); i++) {
        DailyFitnessHealthDataDTO e = expected.get(i);
        DailyFitnessHealthDataDTO a = actual.get(i);
        assertEquals(e.getHeartRate(), a.getHeartRate());
        assertEquals(e.getScore(), a.getScore());
        assertEquals(e.getStress(), a.getStress());
        assertEquals(e.getMetadata(), a.getMetadata());
      }
    }
  }

  private static void assertEqualsNutritionData(List<NutritionFitnessHealthDataDTO> expected,
                                                List<NutritionFitnessHealthDataDTO> actual) {

    if (isEmpty(expected)) {
      assertTrue(isEmpty(actual));
    } else {
      assertTrue(isNotEmpty(actual));
      assertEquals(expected.size(), actual.size());
      for (int i = 0; i < expected.size(); i++) {
        expected.get(i).setCreatedAt(null);
        actual.get(i).setCreatedAt(null);
        assertEquals(expected.get(i).getMetadata(), actual.get(i).getMetadata());
        assertEquals(expected.get(i), actual.get(i));
      }
    }
  }

  private static void assertEqualsSleepData(List<SleepFitnessHealthDataDTO> expected, List<SleepFitnessHealthDataDTO> actual) {

    if (isEmpty(expected)) {
      assertTrue(isEmpty(actual));
    } else {
      assertTrue(isNotEmpty(actual));
      assertEquals(expected.size(), actual.size());
      for (int i = 0; i < expected.size(); i++) {
        expected.get(i).setCreatedAt(null);
        actual.get(i).setCreatedAt(null);
        assertEquals(expected.get(i).getMetadata(), actual.get(i).getMetadata());
        assertEquals(expected.get(i), actual.get(i));
      }
    }
  }
}
