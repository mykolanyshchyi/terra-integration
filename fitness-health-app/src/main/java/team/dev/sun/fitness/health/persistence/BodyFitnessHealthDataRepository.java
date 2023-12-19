package team.dev.sun.fitness.health.persistence;

import team.dev.sun.fitness.health.api.model.Provider;
import team.dev.sun.fitness.health.model.body.BodyFitnessHealthData;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyFitnessHealthDataRepository extends JpaRepository<BodyFitnessHealthData, Long> {

  @Query("""
      select b from BodyFitnessHealthData b
      where (:clientId is null or b.clientId = :clientId)
      and (:userId is null or b.userId = :userId)
      and (:deviceId is null or b.deviceId = :deviceId)
      and (:provider is null or b.provider = :provider)
      and (:fromDate is null or b.createdAt >= :fromDate)
      and (:toDate is null or b.createdAt <= :toDate)
      """)
  List<BodyFitnessHealthData> findAll(@Param("clientId") Long clientId,
                                      @Param("userId") Long userId,
                                      @Param("deviceId") String deviceId,
                                      @Param("provider") Provider provider,
                                      @Param("fromDate") ZonedDateTime fromDate,
                                      @Param("toDate") ZonedDateTime toDate);
}
