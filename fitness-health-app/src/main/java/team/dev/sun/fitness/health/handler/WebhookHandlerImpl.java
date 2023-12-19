package team.dev.sun.fitness.health.handler;

import static java.nio.charset.StandardCharsets.UTF_8;

import team.dev.sun.fitness.health.model.WebhookPayload;
import jakarta.annotation.PostConstruct;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class WebhookHandlerImpl implements WebhookHandler {

  private static final String SIGNATURE_PARRERN = "t=(?<t>\\d+),v1=(?<s>[\\da-f]+)";

  private static final String ALGORITHM = "HmacSHA256";

  private final ActivityHandler activityHandler;

  private final BodyHandler bodyHandler;

  private final DailyHandler dailyHandler;

  private final NutritionHandler nutritionHandler;

  private final SleepHandler sleepHandler;

  private final Map<String, Consumer<WebhookPayload>> payloadHandlers = new HashMap<>();

  private final Pattern signaturePattern = Pattern.compile(SIGNATURE_PARRERN);

  private final HexFormat hexFormat = HexFormat.of();

  @Value("${terra.signing-secret}")
  private final String signingSecret;

  private ExecutorService executorService;

  @PostConstruct
  private void init() {

    payloadHandlers.put("activity", activityHandler);
    payloadHandlers.put("body", bodyHandler);
    payloadHandlers.put("daily", dailyHandler);
    payloadHandlers.put("nutrition", nutritionHandler);
    payloadHandlers.put("sleep", sleepHandler);

    this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4);
  }

  @Override
  public boolean isSignatureValid(final String headerSignatureValue, final String payload) {

    Matcher matcher = signaturePattern.matcher(headerSignatureValue);

    if (!matcher.matches()) {
      log.debug("Signature header is malformed");
      return false;
    }

    String timestamp = matcher.group("t");
    String signature = matcher.group("s");

    String encodedSignature = getEncodedSignature(timestamp, payload);
    return encodedSignature.equals(signature);
  }

  @Override
  public Future<Boolean> submitPayload(final WebhookPayload payload) {

    Consumer<WebhookPayload> handler = payloadHandlers.get(payload.type());
    if (handler != null) {
      return executorService.submit(() -> {
        handler.accept(payload);
        return true;
      });
    }
    return null;
  }

  private String getEncodedSignature(final String timestamp, final String payload) {

    Mac digest;
    try {
      digest = Mac.getInstance(ALGORITHM);
      digest.init(new SecretKeySpec(signingSecret.getBytes(UTF_8), ALGORITHM));
    } catch (InvalidKeyException ex) {
      throw new IllegalStateException("Secret key is invalid", ex);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("Missing algorithm: " + ALGORITHM, e);
    }

    digest.update(timestamp.getBytes(UTF_8));
    digest.update(".".getBytes(UTF_8));
    digest.update(payload.getBytes(UTF_8));

    return hexFormat.formatHex(digest.doFinal()).toLowerCase();
  }
}
