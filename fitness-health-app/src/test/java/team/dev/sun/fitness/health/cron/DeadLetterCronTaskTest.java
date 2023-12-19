package team.dev.sun.fitness.health.cron;

import team.dev.sun.fitness.health.persistence.DeadLetterRepository;
import com.slack.api.Slack;
import com.slack.api.webhook.WebhookResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeadLetterCronTaskTest {

  private static final String EXPECTED_PAYLOAD_TEPLATE = """
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
          					"text": "*Environment:*\\n stage"
          				},
          				{
          					"type": "mrkdwn",
          					"text": "*Amount:*\\n 49"
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

  @Mock
  private DeadLetterRepository deadLetterRepository;

  @Mock
  private Slack slack;

  @InjectMocks
  private DeadLetterCronTask deadLetterCronTask;

  @Captor
  private ArgumentCaptor<String> payloadCaptor;

  @Mock
  private WebhookResponse webhookResponse;

  @ParameterizedTest
  @MethodSource("differentCronArguments")
  void checkNewDeadLettersAndSendNotificationToSlackChennelWithOneHourCron(String cron, ZonedDateTime from, ZonedDateTime to) throws IOException {

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM hh:mm");
    String time = "%s - %s".formatted(from.format(dateTimeFormatter), to.format(dateTimeFormatter));
    String expected = EXPECTED_PAYLOAD_TEPLATE.formatted(time);

    when(deadLetterRepository.countUnprocessed(any(), any())).thenReturn(49L);
    when(slack.send(anyString(), anyString())).thenReturn(webhookResponse);
    when(webhookResponse.getCode()).thenReturn(200);

    ReflectionTestUtils.setField(deadLetterCronTask, "webhookUrl", "https://api.slack.com/sundev-team/notification");
    ReflectionTestUtils.setField(deadLetterCronTask, "environment", "stage");
    ReflectionTestUtils.setField(deadLetterCronTask, "newDeadLetterCheckCron", cron);
    ReflectionTestUtils.setField(deadLetterCronTask, "dateTimeFormatter", dateTimeFormatter);
    ReflectionTestUtils.invokeMethod(deadLetterCronTask, "calculateTimeDifferenceBetweenExecutions");

    deadLetterCronTask.checkNewDeadLetters();

    verify(deadLetterRepository).countUnprocessed(any(), any());
    verify(slack).send(anyString(), payloadCaptor.capture());

    String payload = payloadCaptor.getValue();
    assertEquals(expected, payload);
  }

  private static Stream<Arguments> differentCronArguments() {
    ZonedDateTime now = ZonedDateTime.now();
    return Stream.of(
            Arguments.of("0 0 * ? * *", now.minusHours(1), now), // every hour
            Arguments.of("0 0 */2 ? * *", now.minusHours(2), now), // every 2 hours
            Arguments.of("0 0 */3 ? * *", now.minusHours(3), now), // every 3 hours
            Arguments.of("0 0 */4 ? * *", now.minusHours(4), now), // every 4 hours
            Arguments.of("0 0 0 ? * *", now.minusDays(1), now) // once a day
    );
  }

  @Test
  void checkNewDeadLettersWoSlackNotificationAsThereIsNoNewLetters() {

    when(deadLetterRepository.countUnprocessed(any(), any())).thenReturn(0L);

    deadLetterCronTask.checkNewDeadLetters();

    verify(deadLetterRepository).countUnprocessed(any(), any());
    verifyNoInteractions(slack);
  }
}
