package team.dev.sun.fitness.health.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.SneakyThrows;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@Component
public class AuthenticationSupport {

  public static final String USERNAME_VALUE = "root@sundev.team";

  public static final String BEARER_TOKEN_PREFIX = "Bearer ";

  public static final String AUTHORIZATION_HEADER = "Authorization";

  @Autowired
  private RsaJsonWebKey rsaJsonWebKey;

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String issuerUrl;

  public HttpHeaders getAuthHeaders() {

    final String token = generateJWT();
    return getAuthHeadersForToken(token);
  }

  public HttpHeaders getAuthHeaders(final String email) {

    final String token = generateJWT(email);
    return getAuthHeadersForToken(token);
  }

  public HttpHeaders getAuthHeadersForToken(final String token) {

    final HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    return headers;
  }

  public MockHttpServletRequestBuilder authenticate(String email, HttpMethod method, String pathTemplate, Object... uriVars) {

    return request(method, pathTemplate, uriVars)
        .headers(getAuthHeaders(email == null ? USERNAME_VALUE : email))
        .accept(APPLICATION_JSON)
        .contentType(APPLICATION_JSON);
  }

  public MockHttpServletRequestBuilder authenticate(HttpMethod method, String pathTemplate, Object... uriVars) {

    return authenticate(method, APPLICATION_JSON, pathTemplate, uriVars);
  }

  public MockHttpServletRequestBuilder authenticate(
      HttpMethod method, MediaType acceptType, String pathTemplate, Object... uriVars) {

    String jwt = generateJWT();
    return request(method, pathTemplate, uriVars)
        .header(AUTHORIZATION_HEADER, BEARER_TOKEN_PREFIX + jwt)
        .accept(acceptType)
        .contentType(APPLICATION_JSON);
  }

  public MockHttpServletRequestBuilder authenticate(final HttpMethod method,
                                                    final MediaType acceptType,
                                                    final String pathTemplate,
                                                    final String token,
                                                    final Object... uriVars) {

    return request(method, pathTemplate, uriVars)
        .headers(getAuthHeadersForToken(token))
        .accept(acceptType)
        .contentType(APPLICATION_JSON);
  }

  private String generateJWT() {

    return generateJWT(USERNAME_VALUE);
  }

  @SneakyThrows
  private String generateJWT(final String email) {

    // Create the Claims, which will be the content of the JWT
    final JwtClaims claims = new JwtClaims();
    claims.setJwtId(UUID.randomUUID().toString()); // a unique identifier for the token
    claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
    claims.setNotBeforeMinutesInThePast(0); // time before which the token is not yet valid (2 minutes ago)
    claims.setIssuedAtToNow(); // when the token was issued/created (now)
    claims.setAudience("account"); // to whom this token is intended to be sent
    claims.setIssuer(issuerUrl); // who creates the token and signs it
    claims.setSubject(UUID.randomUUID().toString()); // the subject/principal is whom the token is about
    claims.setClaim("typ", "Bearer"); // set type of token
    claims.setClaim("azp", "example-client-id"); // Authorized party  (the party to which this token was issued)
    claims.setClaim(
        "auth_time",
        NumericDate.fromMilliseconds(Instant.now().minus(11, ChronoUnit.SECONDS).toEpochMilli()).getValue()
    ); // time when authentication occured
    claims.setClaim("session_state", UUID.randomUUID().toString());
    claims.setClaim("acr", "0"); //Authentication context class
    claims.setClaim("realm_access", Map.of("roles", List.of("offline_access", "uma_authorization", "user"))); //keycloak roles
    claims.setClaim("resource_access", Map.of(
                        "account",
                        Map.of("roles", List.of("manage-account", "manage-account-links", "view-profile"))
                    )
    ); //keycloak roles
    claims.setClaim("scope", "profile email");
    claims.setClaim("name", "John Doe"); // additional claims/attributes about the subject can be added
    claims.setClaim("email", email);
    claims.setClaim("email_verified", true);
    claims.setClaim("preferred_username", "doe.john");
    claims.setClaim("given_name", "John");
    claims.setClaim("family_name", "Doe");

    // A JWT is a JWS and/or a JWE with JSON claims as the payload.
    // In this example it is a JWS so we create a JsonWebSignature object.
    final JsonWebSignature jws = new JsonWebSignature();

    // The payload of the JWS is JSON content of the JWT Claims
    jws.setPayload(claims.toJson());

    // The JWT is signed using the private key
    jws.setKey(rsaJsonWebKey.getPrivateKey());

    // Set the Key ID (kid) header because it's just the polite thing to do.
    // We only have one key in this example but a using a Key ID helps
    // facilitate a smooth key rollover process
    jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());

    // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
    jws.setAlgorithmHeaderValue(RsaJsonWebKeyConfig.ALGORITHM_ID);

    // set the type header
    jws.setHeader("typ", "JWT");

    // Sign the JWS and produce the compact serialization or the complete JWT/JWS
    // representation, which is a string consisting of three dot ('.') separated
    // base64url-encoded parts in the form Header.Payload.Signature
    return jws.getCompactSerialization();
  }
}
