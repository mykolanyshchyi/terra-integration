package team.dev.sun.fitness.health.handler;

import static team.dev.sun.fitness.health.util.DateUtil.parseDate;
import static java.util.Collections.emptyList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import team.dev.sun.fitness.health.api.model.Provider;
import team.dev.sun.fitness.health.dlq.DeadLetterQueue;
import team.dev.sun.fitness.health.model.FitnessHealthData;
import team.dev.sun.fitness.health.model.Metadata;
import team.dev.sun.fitness.health.model.Reference;
import team.dev.sun.fitness.health.model.WebhookPayload;
import team.dev.sun.fitness.health.parser.WebhookPayloadParser;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class AbstractHandler<S, T extends FitnessHealthData, R extends JpaRepository<T, Long>>
    implements Consumer<WebhookPayload> {

  private final Logger log = LogManager.getLogger(this.getClass());

  private final DeadLetterQueue deadLetterQueue;

  private final WebhookPayloadParser payloadParser;

  private final R repository;

  private final Class<S> sourceClass;

  @Override
  @Transactional
  public void accept(final WebhookPayload webhookPayload) {

    try {
      List<T> dataList = extractAllData(webhookPayload);
      repository.saveAll(dataList);
    } catch (Throwable t) {
      deadLetterQueue.createDeadLetter(webhookPayload.payload(), t);
    }
  }

  protected void createAndsetMetadata(final T entity, String startTime, String endTime) {

    Metadata metadata = new Metadata();
    metadata.setStartTime(parseDate(startTime));
    metadata.setEndTime(parseDate(endTime));
    entity.setMetadata(metadata);
  }

  protected abstract T extractData(S source);

  protected abstract void setMetadata(S source, T entity);

  private List<T> extractAllData(WebhookPayload payload) {

    List<S> dataList = payloadParser.parseAllData(payload, sourceClass);
    if (isNotEmpty(dataList)) {
      Reference reference = payloadParser.parseReference(payload);
      Provider provider = payloadParser.parseProvider(payload);
      ZonedDateTime now = ZonedDateTime.now();
      return dataList.stream()
                     .map(source -> toFitnessHealthData(source, reference, provider, now))
                     .toList();
    } else {
      log.warn("There is no records to process");
      return emptyList();
    }
  }

  private T toFitnessHealthData(S source, Reference reference, Provider provider, ZonedDateTime createdAt) {

    T data = extractData(source);
    data.setProvider(provider);
    fillReferenceData(data, reference);
    data.setCreatedAt(createdAt);
    setMetadata(source, data);
    return data;
  }

  private void fillReferenceData(T fitnessHealthData, Reference reference) {

    fitnessHealthData.setClientId(reference.clientId());
    fitnessHealthData.setUserId(reference.userId());
    fitnessHealthData.setDeviceId(reference.deviceId());
  }
}
