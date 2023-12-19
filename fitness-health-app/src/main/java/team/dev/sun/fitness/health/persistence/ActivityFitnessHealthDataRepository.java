package team.dev.sun.fitness.health.persistence;

import team.dev.sun.fitness.health.api.model.Provider;
import team.dev.sun.fitness.health.model.activity.ActivityFitnessHealthData;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityFitnessHealthDataRepository extends JpaRepository<ActivityFitnessHealthData, Long> {

  @Query("""
      select a from ActivityFitnessHealthData a
      where (:clientId is null or a.clientId = :clientId)
      and (:userId is null or a.userId = :userId)
      and (:deviceId is null or a.deviceId = :deviceId)
      and (:provider is null or a.provider = :provider)
      and (:fromDate is null or a.createdAt >= :fromDate)
      and (:toDate is null or a.createdAt <= :toDate)
      """)
  List<ActivityFitnessHealthData> findAll(@Param("clientId") Long clientId,
                                          @Param("userId") Long userId,
                                          @Param("deviceId") String deviceId,
                                          @Param("provider") Provider provider,
                                          @Param("fromDate") ZonedDateTime fromDate,
                                          @Param("toDate") ZonedDateTime toDate);
}
