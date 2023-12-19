package team.dev.sun.fitness.health.mapper.body;

import static org.junit.jupiter.api.Assertions.assertEquals;

import team.dev.sun.fitness.health.api.dto.body.HydrationMeasurementDTO;
import team.dev.sun.fitness.health.model.body.HydrationMeasurement;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class HydrationMeasurementMapperTest {

  private static final Long ID = 56L;

  private static final Double HYDRATION_KG = 34.2d;

  private static final ZonedDateTime TIMESTAMP = ZonedDateTime.now();

  @Test
  void mapHydrationMeasurement() {

    HydrationMeasurementDTO expected = getDto();
    HydrationMeasurementMapper mapper = new HydrationMeasurementMapper();
    HydrationMeasurementDTO actual = mapper.map(getEntity());
    assertEquals(expected, actual);
  }

  private HydrationMeasurement getEntity() {

    HydrationMeasurement hydrationMeasurement = new HydrationMeasurement();
    hydrationMeasurement.setId(ID);
    hydrationMeasurement.setHydrationKg(HYDRATION_KG);
    hydrationMeasurement.setTimestamp(TIMESTAMP);
    return hydrationMeasurement;
  }

  private HydrationMeasurementDTO getDto() {

    return HydrationMeasurementDTO.builder()
                                  .id(ID)
                                  .hydrationKg(HYDRATION_KG)
                                  .timestamp(TIMESTAMP)
                                  .build();
  }
}