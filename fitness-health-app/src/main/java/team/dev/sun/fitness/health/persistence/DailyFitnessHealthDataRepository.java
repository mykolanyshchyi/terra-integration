package team.dev.sun.fitness.health.persistence;

import team.dev.sun.fitness.health.api.model.Provider;
import team.dev.sun.fitness.health.model.daily.DailyFitnessHealthData;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyFitnessHealthDataRepository extends JpaRepository<DailyFitnessHealthData, Long> {

  @Query("""
      select d from DailyFitnessHealthData d
      where (:clientId is null or d.clientId = :clientId)
      and (:userId is null or d.userId = :userId)
      and (:deviceId is null or d.deviceId = :deviceId)
      and (:provider is null or d.provider = :provider)
      and (:fromDate is null or d.createdAt >= :fromDate)
      and (:toDate is null or d.createdAt <= :toDate)
      """)
  List<DailyFitnessHealthData> findAll(@Param("clientId") Long clientId,
                                       @Param("userId") Long userId,
                                       @Param("deviceId") String deviceId,
                                       @Param("provider") Provider provider,
                                       @Param("fromDate") ZonedDateTime fromDate,
                                       @Param("toDate") ZonedDateTime toDate);
}
