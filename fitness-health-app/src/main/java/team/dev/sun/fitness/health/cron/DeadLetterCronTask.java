package team.dev.sun.fitness.health.cron;

import team.dev.sun.fitness.health.persistence.DeadLetterRepository;
import com.slack.api.Slack;
import com.slack.api.webhook.WebhookResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.requireNonNull;

@Log4j2
@Component
@RequiredArgsConstructor
public class DeadLetterCronTask {

  private static final String TIME_PATTERN = "dd MMM hh:mm";

  private static final String TIME_TEPLATE = "%s - %s";

  private static final String MESSAGE_TEPLATE = """
          {
          	"blocks": [
          		{
          			"type": "header",
          			"text": {
          				"type": "plain_text",
          				"text": "There are new unprocessed messages in Dead-Letter Queue",
          				"emoji": true
          			}
          		},
          		{
          			"type": "section",
          			"fields": [
          				{
          					"type": "mrkdwn",
          					"text": "*Environment:*\\n %s"
          				},
          				{
          					"type": "mrkdwn",
          					"text": "*Amount:*\\n %s"
          				}
          			]
          		},
          		{
          			"type": "section",
          			"fields": [
          				{
          					"type": "mrkdwn",
          					"text": "*Time:*\\n %s"
          				}
          			]
          		}
          	]
          }
      """;

  private final DeadLetterRepository deadLetterRepository;

  private final Slack slack;

  @Value("${slack.webhook-url}")
  private String webhookUrl;

  @Value("${environment}")
  private String environment;

  @Value("${cron.notification.new-dead-letter}")
  private String newDeadLetterCheckCron;

  private DateTimeFormatter dateTimeFormatter;

  private long timeBetweenExecutionsInSeconds;

  @PostConstruct
  private void init() {

    dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
    calculateTimeDifferenceBetweenExecutions();
  }

  @Transactional(readOnly = true)
  @Scheduled(cron = "${cron.notification.new-dead-letter}")
  @SchedulerLock(name = "DeadLetterCronTask_checkNewDeadLetters", lockAtLeastFor = "PT1M", lockAtMostFor = "PT59M")
  public void checkNewDeadLetters() {

    ZonedDateTime now = ZonedDateTime.now();
    ZonedDateTime previousExecution = now.minusSeconds(timeBetweenExecutionsInSeconds);
    long newUnprocessedDeadLetters = deadLetterRepository.countUnprocessed(previousExecution, now);

    if (newUnprocessedDeadLetters > 0) {
      sendSlackNotification(newUnprocessedDeadLetters, previousExecution, now);
    }
  }

  @SneakyThrows
  private void sendSlackNotification(final long newUnprocessedDeadLetters,
                                     final ZonedDateTime previousExecution,
                                     final ZonedDateTime now) {

    String payload = getNotificationPayload(newUnprocessedDeadLetters, previousExecution, now);
    WebhookResponse response = slack.send(webhookUrl, payload);
    log.debug("New Dead-letters slack notification was sent, http status code: {}", response.getCode());
  }

  private String getNotificationPayload(final long newUnprocessedDeadLetters,
                                        final ZonedDateTime previousExecution,
                                        final ZonedDateTime now) {

    String time = TIME_TEPLATE.formatted(previousExecution.format(dateTimeFormatter), now.format(dateTimeFormatter));
    return MESSAGE_TEPLATE.formatted(environment, newUnprocessedDeadLetters, time);
  }

  private void calculateTimeDifferenceBetweenExecutions() {
    CronExpression cronExpression = CronExpression.parse(newDeadLetterCheckCron);
    ZonedDateTime firstExecution = cronExpression.next(ZonedDateTime.now());
    ZonedDateTime secondExecution = cronExpression.next(requireNonNull(firstExecution));
    Duration duration = Duration.between(firstExecution, secondExecution);
    timeBetweenExecutionsInSeconds = duration.getSeconds();
  }
}
