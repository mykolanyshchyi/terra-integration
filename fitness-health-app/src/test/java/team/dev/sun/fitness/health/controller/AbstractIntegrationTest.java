package team.dev.sun.fitness.health.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.noContent;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.RsaJsonWebKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("integration-test")
@RequiredArgsConstructor
public abstract class AbstractIntegrationTest {

  private static final String CLIENT_ID = "eb559b56-d003-4a72-95f4-0f21c451eae2";

  private static final String AUTH_REALM = "sundev-team-central";

  private final RsaJsonWebKey rsaJsonWebKey;

  @Value("${wiremock.server.baseUrl}")
  private String serverBaseUrl;

  private boolean stubsConfigured;

  @PostConstruct
  public void initStubs() {

    if (!stubsConfigured) {
      stubForOpenIdConfiguration();
      stubForCerts();
      stubForCreateClient();
      stubForChangeClientLocation();
      stubForAccessToken();
      stubForClientSecret();
      stubForClientDescription();
      stubForDeleteClient();
      stubsConfigured = true;
    }
  }

  private void stubForOpenIdConfiguration() {

    stubFor(get(
        urlEqualTo(format("/auth/realms/%s/.well-known/openid-configuration", AUTH_REALM)))
                .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBody(getOpenIdConfiguration())));
  }

  private void stubForCerts() {

    stubFor(get(
        urlEqualTo(format("/auth/realms/%s/protocol/openid-connect/certs", AUTH_REALM)))
                .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBody(new JsonWebKeySet(rsaJsonWebKey).toJson())));
  }

  private void stubForCreateClient() {

    stubFor(post(urlEqualTo(format("/auth/realms/%s/protocol/openid-connect/token", AUTH_REALM)))
                .willReturn(aResponse()
                                .withStatus(CREATED.value())
                                .withHeader("Content-Type", "application/json")
                                .withHeader(
                                    "Location", format("%s/auth/admin/realms/%s/clients/%s", serverBaseUrl, AUTH_REALM,
                                                       CLIENT_ID
                                    ))));
  }

  private void stubForChangeClientLocation() {

    stubFor(post(
        urlEqualTo(format("/auth/admin/realms/%s/clients/%s", AUTH_REALM, "df559b56-d003-4a72-95f4-0f21c451eae2")))
                .willReturn(aResponse()
                                .withStatus(NO_CONTENT.value())
                                .withHeader("Content-Type", "application/json")
                                .withHeader(
                                    "Location",
                                    format("%s/auth/admin/realms/%s/clients/%s", serverBaseUrl, AUTH_REALM, CLIENT_ID)
                                )));
  }

  private void stubForAccessToken() {

    stubFor(post(urlEqualTo(format("/auth/realms/%s/protocol/openid-connect/token", AUTH_REALM)))
                .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBody("""
                                              {
                                                  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIyS1k3d3J5MXFpS1NJX0NVYUpkRGZraEZFbkxqLXZidXdrVk9HbUQ2X2w0In0.eyJleHAiOjE2MzEwMzg2NTEsImlhdCI6MTYzMTAzODM1MSwianRpIjoiMGUwNGI1MGItMjNhZi00MTA1LThlOWYtYjNkODJiMzkxZTRkIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTgwL2F1dGgvcmVhbG1zL2ZpcnN0Y2FyZS1pbnNpZ2h0IiwiYXVkIjpbInJlYWxtLW1hbmFnZW1lbnQiLCJhY2NvdW50Il0sInN1YiI6ImViM2RlOWI0LTY2MTMtNDE0NS05NGUzLTRjMGJiMzMxOTU0NyIsInR5cCI6IkJlYXJlciIsImF6cCI6ImZpcnN0Y2FyZS1pbnNpZ2h0LWJlIiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLWZpcnN0Y2FyZS1pbnNpZ2h0Iiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InJlYWxtLW1hbmFnZW1lbnQiOnsicm9sZXMiOlsidmlldy1pZGVudGl0eS1wcm92aWRlcnMiLCJ2aWV3LXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJyZWFsbS1hZG1pbiIsImNyZWF0ZS1jbGllbnQiLCJtYW5hZ2UtdXNlcnMiLCJ1bWFfcHJvdGVjdGlvbiIsInF1ZXJ5LXJlYWxtcyIsInZpZXctYXV0aG9yaXphdGlvbiIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS11c2VycyIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIiwicXVlcnktZ3JvdXBzIl19LCJmaXJzdGNhcmUtaW5zaWdodC1iZSI6eyJyb2xlcyI6WyJ1bWFfcHJvdGVjdGlvbiJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiY2xpZW50SWQiOiJmaXJzdGNhcmUtaW5zaWdodC1iZSIsImNsaWVudEhvc3QiOiIxNzIuMjAuMC4xIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzZXJ2aWNlLWFjY291bnQtZmlyc3RjYXJlLWluc2lnaHQtYmUiLCJjbGllbnRBZGRyZXNzIjoiMTcyLjIwLjAuMSJ9.HqRncLEdbqEEw-aVk2znI2rnypFa5gYerp1kOE99AzNMc43IgadR1Xa-HeBAdC2xgN4e515dBEbjXwGHZT8FUvhgKVH9D5vFFW9TzKxOUUMB2f_5jsdtckvC1caVXdVOIwdsXxyNuGT3eIZFg2Ge6gx-P7zLFtXAnunaC3uwaUWSM4FVCctz633wXGu_7DIFu_Bfwzl31FQqwJzNhm6XXDBVWKduwiBtv3cRoChiDGxOEJajCpGDC0qAyrLqXlbrLXIvfPtxrPPp3ZvRuK7z3CISDIFLQWoYq3OJiaxuCe0NlkHlxrTG6yy47HGeVyjUIJF_FPFPBfHyrQ4dl8eycw",
                                                  "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIyS1k3d3J5MXFpS1NJX0NVYUpkRGZraEZFbkxqLXZidXdrVk9HbUQ2X2w0In0.eyJleHAiOjE2MzEwMzg2NTEsImlhdCI6MTYzMTAzODM1MSwianRpIjoiMGUwNGI1MGItMjNhZi00MTA1LThlOWYtYjNkODJiMzkxZTRkIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTgwL2F1dGgvcmVhbG1zL2ZpcnN0Y2FyZS1pbnNpZ2h0IiwiYXVkIjpbInJlYWxtLW1hbmFnZW1lbnQiLCJhY2NvdW50Il0sInN1YiI6ImViM2RlOWI0LTY2MTMtNDE0NS05NGUzLTRjMGJiMzMxOTU0NyIsInR5cCI6IkJlYXJlciIsImF6cCI6ImZpcnN0Y2FyZS1pbnNpZ2h0LWJlIiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLWZpcnN0Y2FyZS1pbnNpZ2h0Iiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InJlYWxtLW1hbmFnZW1lbnQiOnsicm9sZXMiOlsidmlldy1pZGVudGl0eS1wcm92aWRlcnMiLCJ2aWV3LXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJyZWFsbS1hZG1pbiIsImNyZWF0ZS1jbGllbnQiLCJtYW5hZ2UtdXNlcnMiLCJ1bWFfcHJvdGVjdGlvbiIsInF1ZXJ5LXJlYWxtcyIsInZpZXctYXV0aG9yaXphdGlvbiIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS11c2VycyIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIiwicXVlcnktZ3JvdXBzIl19LCJmaXJzdGNhcmUtaW5zaWdodC1iZSI6eyJyb2xlcyI6WyJ1bWFfcHJvdGVjdGlvbiJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiY2xpZW50SWQiOiJmaXJzdGNhcmUtaW5zaWdodC1iZSIsImNsaWVudEhvc3QiOiIxNzIuMjAuMC4xIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzZXJ2aWNlLWFjY291bnQtZmlyc3RjYXJlLWluc2lnaHQtYmUiLCJjbGllbnRBZGRyZXNzIjoiMTcyLjIwLjAuMSJ9.HqRncLEdbqEEw-aVk2znI2rnypFa5gYerp1kOE99AzNMc43IgadR1Xa-HeBAdC2xgN4e515dBEbjXwGHZT8FUvhgKVH9D5vFFW9TzKxOUUMB2f_5jsdtckvC1caVXdVOIwdsXxyNuGT3eIZFg2Ge6gx-P7zLFtXAnunaC3uwaUWSM4FVCctz633wXGu_7DIFu_Bfwzl31FQqwJzNhm6XXDBVWKduwiBtv3cRoChiDGxOEJajCpGDC0qAyrLqXlbrLXIvfPtxrPPp3ZvRuK7z3CISDIFLQWoYq3OJiaxuCe0NlkHlxrTG6yy47HGeVyjUIJF_FPFPBfHyrQ4dl8eycw",
                                                  "expires_in": 300,
                                                  "refresh_expires_in": 0,
                                                  "token_type": "Bearer",
                                                  "not-before-policy": 0,
                                                  "scope": "email profile"
                                              }""")));
  }

  private void stubForClientSecret() {

    stubFor(post(urlEqualTo(format("/auth/admin/realms/%s/clients/%s/client-secret", AUTH_REALM, CLIENT_ID)))
                .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBody("""
                                              {"value": "secret"}
                                              """)));
  }

  private void stubForClientDescription() {

    stubFor(get(urlEqualTo(format("/auth/admin/realms/%s/clients/%s", AUTH_REALM, CLIENT_ID)))
                .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBody("""
                                              {
                                                            "id" : "eb559b56-d003-4a72-95f4-0f21c451eae2",
                                                            "clientId" : "new client id",
                                                            "name":"client name",
                                                            "description":"client description",
                                                            "serviceAccountsEnabled" : true,
                                                            "secret" :"secret"
                                                        }
                                              """)));
  }

  private void stubForDeleteClient() {

    stubFor(delete(urlEqualTo(format("/auth/admin/realms/%s/clients/%s", AUTH_REALM, CLIENT_ID)))
                .willReturn(noContent()));
  }

  private String getOpenIdConfiguration() {

    String baseUrl = serverBaseUrl + "/auth/realms/" + AUTH_REALM;
    return """
        {
          "issuer": "baseUrlPlaceholder",
          "authorization_endpoint": "baseUrlPlaceholder/protocol/openid-connect/auth",
          "token_endpoint": "baseUrlPlaceholder/protocol/openid-connect/token",
          "token_introspection_endpoint": "baseUrlPlaceholder/protocol/openid-connect/token/introspect",
          "userinfo_endpoint": "baseUrlPlaceholder/protocol/openid-connect/userinfo",
          "end_session_endpoint": "baseUrlPlaceholder/protocol/openid-connect/logout",
          "jwks_uri": "baseUrlPlaceholder/protocol/openid-connect/certs",
          "check_session_iframe": "baseUrlPlaceholder/protocol/openid-connect/login-status-iframe.html",
          "registration_endpoint": "baseUrlPlaceholder/clients-registrations/openid-connect",
          "introspection_endpoint": "baseUrlPlaceholder/protocol/openid-connect/token/introspect"
        }"""
        .replaceAll("baseUrlPlaceholder", baseUrl);
  }
}
