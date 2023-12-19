package team.dev.sun.fitness.health.model;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

import team.dev.sun.fitness.health.api.model.DataType;
import team.dev.sun.fitness.health.api.model.Provider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "fitness_health_data")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class FitnessHealthData implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "client_id")
  private Long clientId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "device_id")
  private String deviceId;

  @Column(name = "provider")
  @Enumerated(EnumType.STRING)
  private Provider provider;

  @Column(name = "data_type")
  @Enumerated(EnumType.STRING)
  private DataType dataType;

  @OneToOne(cascade = ALL)
  @JoinColumn(name = "metadata_id")
  private Metadata metadata;

  @Column(name = "created_at", columnDefinition = "TIMESTAMP")
  private ZonedDateTime createdAt;
}
