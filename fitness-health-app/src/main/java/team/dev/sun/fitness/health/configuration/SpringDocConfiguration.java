package team.dev.sun.fitness.health.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

  public static final String KEYCLOAK_OIDC = "Keycloak Identity Provider";

  @Bean
  public OpenAPI openAPI(@Value("${springdoc.server}") final String[] serverUrls,
                         @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") final String issuerUri) {

    return new OpenAPI()
        .info(new Info().title("Fitness&Health API")
                        .description("Fitness&Health Service API")
                        .version("v1.0.0")
                        .contact(getContact()))
        .servers(getServers(serverUrls))
        .components(new Components()
                        .addSecuritySchemes(KEYCLOAK_OIDC, createOAuthScheme(getTokenEndpoint(issuerUri)))
        )
        .addSecurityItem(new SecurityRequirement()
                             .addList(KEYCLOAK_OIDC, List.of("email", "openid", "profile"))
        );
  }

  private List<Server> getServers(final String[] serverUrls) {

    return Stream.of(serverUrls)
                 .map(url -> new Server().url(url))
                 .toList();
  }

  private Contact getContact() {

    Contact contact = new Contact();
    contact.setEmail("notifications@sundev.team");
    contact.setName("Sundev Team");
    return contact;
  }

  private SecurityScheme createOAuthScheme(String tokenUrl) {

    OAuthFlows flows = createOAuthFlows(tokenUrl);

    return new SecurityScheme()
        .type(SecurityScheme.Type.OAUTH2)
        .flows(flows);
  }

  private OAuthFlows createOAuthFlows(String tokenUrl) {

    return new OAuthFlows().password(createPasswordFlow(tokenUrl));
  }

  private OAuthFlow createPasswordFlow(String tokenUrl) {

    return new OAuthFlow()
        .tokenUrl(tokenUrl)
        .scopes(new Scopes()
                    .addString("email", "User's email")
                    .addString("openid", "Openid")
                    .addString("profile", "Basic profile information")
        );
  }

  private String getTokenEndpoint(final String issuerUri) {

    return String.format("%s/protocol/openid-connect/token", issuerUri);
  }
}
