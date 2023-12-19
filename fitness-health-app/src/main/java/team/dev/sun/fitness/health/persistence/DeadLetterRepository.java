package team.dev.sun.fitness.health.persistence;

import team.dev.sun.fitness.health.model.DeadLetter;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeadLetterRepository extends JpaRepository<DeadLetter, Long> {

  @Query("select dl.id from DeadLetter dl where dl.processed = false")
  List<Long> getUnprocessedIds();

  long countDeadLettersByProcessedIsFalseAndCreatedAtBetween(ZonedDateTime from, ZonedDateTime till);

  default long countUnprocessed(ZonedDateTime from, ZonedDateTime till) {
    return countDeadLettersByProcessedIsFalseAndCreatedAtBetween(from, till);
  }
}
