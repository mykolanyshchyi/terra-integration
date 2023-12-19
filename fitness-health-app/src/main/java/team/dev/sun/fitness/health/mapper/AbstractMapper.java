package team.dev.sun.fitness.health.mapper;

import static java.util.Collections.emptyList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

import team.dev.sun.fitness.health.api.dto.BaseFitnessHealthDataDTO;
import team.dev.sun.fitness.health.api.dto.MetadataDTO;
import team.dev.sun.fitness.health.model.FitnessHealthData;
import team.dev.sun.fitness.health.model.Metadata;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractMapper<S, T extends Serializable> {

  public abstract T map(final S source);

  public List<T> map(final List<S> source) {

    if (isEmpty(source)) {
      return emptyList();
    }

    return source.stream()
                 .map(this::map)
                 .toList();
  }

  protected <E extends FitnessHealthData, D extends BaseFitnessHealthDataDTO> D mapBase(E entity, D dto) {

    dto.setId(entity.getId());
    dto.setClientId(entity.getClientId());
    dto.setUserId(entity.getUserId());
    dto.setDeviceId(entity.getDeviceId());
    dto.setProvider(entity.getProvider());
    dto.setDataType(entity.getDataType());
    dto.setMetadata(mapMetadata(entity.getMetadata()));
    dto.setCreatedAt(entity.getCreatedAt());
    return dto;
  }

  protected MetadataDTO mapMetadata(Metadata metadata) {

    if (metadata == null) return null;
    return MetadataDTO.builder()
                      .id(metadata.getId())
                      .startTime(metadata.getStartTime())
                      .endTime(metadata.getEndTime())
                      .build();
  }
}
