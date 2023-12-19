package team.dev.sun.fitness.health.mapper.body;

import static team.dev.sun.fitness.health.api.model.TemperatureType.BODY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import team.dev.sun.fitness.health.api.dto.body.TemperatureDTO;
import team.dev.sun.fitness.health.api.model.TemperatureType;
import team.dev.sun.fitness.health.model.body.Temperature;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class TemperatureMapperTest {

  private static final Long ID = 9832L;

  private static final TemperatureType TYPE = BODY;

  private static final Double TEMPERATURE_CELSIUS = 36.6d;

  private static final ZonedDateTime TIMESTAMP = ZonedDateTime.now();

  @Test
  void mapTemperature() {

    TemperatureDTO expected = getDto();
    TemperatureMapper temperatureMapper = new TemperatureMapper();
    TemperatureDTO actual = temperatureMapper.map(getEntity());
    assertEquals(expected, actual);
  }

  private Temperature getEntity() {

    Temperature temperature = new Temperature();
    temperature.setId(ID);
    temperature.setType(TYPE);
    temperature.setTemperatureCelsius(TEMPERATURE_CELSIUS);
    temperature.setTimestamp(TIMESTAMP);
    return temperature;
  }

  private TemperatureDTO getDto() {

    return TemperatureDTO.builder()
                         .id(ID)
                         .type(TYPE)
                         .temperatureCelsius(TEMPERATURE_CELSIUS)
                         .timestamp(TIMESTAMP)
                         .build();
  }
}