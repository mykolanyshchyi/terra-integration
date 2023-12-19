package team.dev.sun.fitness.health.model;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "dead_letters")
public class DeadLetter {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "data")
  private String data;

  @Column(name = "error")
  private String error;

  @Column(name = "stack_trace")
  private String stackTrace;

  @Column(name = "processed")
  private Boolean processed;

  @Column(name = "created_at", columnDefinition = "TIMESTAMP")
  private ZonedDateTime createdAt;

  @Column(name = "processed_at", columnDefinition = "TIMESTAMP")
  private ZonedDateTime processedAt;
}
