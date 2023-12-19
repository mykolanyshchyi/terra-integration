package team.dev.sun.fitness.health.handler;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.resourceToString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import team.dev.sun.fitness.health.model.WebhookPayload;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class WebhookHandlerImplTest {

  private static final String API_KEY = "cdc83d494bd199e09f88c1462c89f5105d63b5d32494615d";

  @InjectMocks
  private WebhookHandlerImpl webhookHandler;

  @Mock
  private ExecutorService executorService;

  @Mock
  private WebhookPayload payload;

  @Nested
  class IsSignatureValid {

    @BeforeEach
    void setUp() {

      ReflectionTestUtils.setField(webhookHandler, "signingSecret", API_KEY);
    }

    @Test
    void signatureNotValid() {

      String headerSignatureValue = "some signature";
      String payload = "some payload";
      boolean result = webhookHandler.isSignatureValid(headerSignatureValue, payload);
      assertFalse(result);
    }

    @Test
    void signatureValid() throws IOException {

      String headerSignatureValue = "t=1683327800,v1=453147e5f77349f77a2f499b71ee6409c010791887c65cf461ef2390dc4621c5";
      String payload = resourceToString("unit-test-data/signatureCheck.json", UTF_8, this.getClass().getClassLoader());
      boolean result = webhookHandler.isSignatureValid(headerSignatureValue, payload);
      assertTrue(result);
    }
  }

  @Nested
  class SubmitPayload {

    @BeforeEach
    void setUp() {

      ReflectionTestUtils.setField(webhookHandler, "executorService", executorService);
    }

    @Test
    void skippedAsNoRegisteredHandler() {

      when(payload.type()).thenReturn("activity");
      webhookHandler.submitPayload(payload);
      verifyNoInteractions(executorService);
    }

    @Test
    void successfullySubmitPayload() {

      Map<String, Consumer<WebhookPayload>> payloadHandlers = new HashMap<>();
      payloadHandlers.put("daily", mock(DailyHandler.class));
      ReflectionTestUtils.setField(webhookHandler, "payloadHandlers", payloadHandlers);
      when(payload.type()).thenReturn("daily");
      webhookHandler.submitPayload(payload);
      verify(executorService).submit(any(Callable.class));
    }
  }
}