package team.dev.sun.fitness.health.persistence;

import team.dev.sun.fitness.health.api.model.Provider;
import team.dev.sun.fitness.health.model.nutrition.NutritionFitnessHealthData;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NutritionFitnessHealthDataRepository extends JpaRepository<NutritionFitnessHealthData, Long> {

  @Query("""
      select n from NutritionFitnessHealthData n
      where (:clientId is null or n.clientId = :clientId)
      and (:userId is null or n.userId = :userId)
      and (:deviceId is null or n.deviceId = :deviceId)
      and (:provider is null or n.provider = :provider)
      and (:fromDate is null or n.createdAt >= :fromDate)
      and (:toDate is null or n.createdAt <= :toDate)
      """)
  List<NutritionFitnessHealthData> findAll(@Param("clientId") Long clientId,
                                           @Param("userId") Long userId,
                                           @Param("deviceId") String deviceId,
                                           @Param("provider") Provider provider,
                                           @Param("fromDate") ZonedDateTime fromDate,
                                           @Param("toDate") ZonedDateTime toDate);
}
