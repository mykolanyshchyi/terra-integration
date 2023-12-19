package team.dev.sun.fitness.health.mapper.body;

import static team.dev.sun.fitness.health.api.model.BloodGlucoseType.BLOOD;
import static org.junit.jupiter.api.Assertions.assertEquals;

import team.dev.sun.fitness.health.api.dto.body.BloodGlucoseSampleDTO;
import team.dev.sun.fitness.health.api.model.BloodGlucoseType;
import team.dev.sun.fitness.health.model.body.BloodGlucoseSample;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class BloodGlucoseSampleMapperTest {

  private static final Long ID = 432L;

  private static final BloodGlucoseType TYPE = BLOOD;

  private static final ZonedDateTime TIMESTAMP = ZonedDateTime.now();

  private static final Double BLOOD_GLUCOSE_MG_PER_DL = 456.56;

  private static final Integer GLUCOSE_LEVEL_FLAG = 4536;

  private static final Integer TREND_ARROW = 53;

  @Test
  void mapBloodGlucoseSample() {

    BloodGlucoseSampleDTO expected = getDto();
    BloodGlucoseSampleMapper bloodGlucoseSampleMapper = new BloodGlucoseSampleMapper();
    BloodGlucoseSampleDTO actual = bloodGlucoseSampleMapper.map(getEntity());
    assertEquals(expected, actual);
  }

  private BloodGlucoseSample getEntity() {

    BloodGlucoseSample sample = new BloodGlucoseSample();
    sample.setId(ID);
    sample.setType(TYPE);
    sample.setTimestamp(TIMESTAMP);
    sample.setBloodGlucoseMgPerDl(BLOOD_GLUCOSE_MG_PER_DL);
    sample.setGlucoseLevelFlag(GLUCOSE_LEVEL_FLAG);
    sample.setTrendArrow(TREND_ARROW);
    return sample;
  }

  private BloodGlucoseSampleDTO getDto() {

    return BloodGlucoseSampleDTO.builder()
                                .id(ID)
                                .type(TYPE)
                                .timestamp(TIMESTAMP)
                                .bloodGlucoseMgPerDl(BLOOD_GLUCOSE_MG_PER_DL)
                                .glucoseLevelFlag(GLUCOSE_LEVEL_FLAG)
                                .trendArrow(TREND_ARROW)
                                .build();
  }
}