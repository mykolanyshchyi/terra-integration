package team.dev.sun.fitness.health.persistence;

import team.dev.sun.fitness.health.api.model.Provider;
import team.dev.sun.fitness.health.model.sleep.SleepFitnessHealthData;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SleepFitnessHealthDataRepository extends JpaRepository<SleepFitnessHealthData, Long> {

  @Query("""
      select s from SleepFitnessHealthData s
      where (:clientId is null or s.clientId = :clientId)
      and (:userId is null or s.userId = :userId)
      and (:deviceId is null or s.deviceId = :deviceId)
      and (:provider is null or s.provider = :provider)
      and (:fromDate is null or s.createdAt >= :fromDate)
      and (:toDate is null or s.createdAt <= :toDate)
      """)
  List<SleepFitnessHealthData> findAll(@Param("clientId") Long clientId,
                                       @Param("userId") Long userId,
                                       @Param("deviceId") String deviceId,
                                       @Param("provider") Provider provider,
                                       @Param("fromDate") ZonedDateTime fromDate,
                                       @Param("toDate") ZonedDateTime toDate);
}
