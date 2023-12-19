package team.dev.sun.fitness.health.configuration;

import static team.dev.sun.fitness.health.api.ApiUrls.CREATE_FITNESS_HEALTH_DATA;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Log4j2
@Configuration
public class OAuth2SecurityConfiguration {

  private final String[] insecureEndpoints;

  private final CorsConfigurationSource corsConfigurationSource;

  private final String issuerUri;

  @Autowired
  public OAuth2SecurityConfiguration(final @Value("${api.insecure.patterns}") String[] endpoints,
                                     final CorsConfigurationSource corsConfigurationSource,
                                     final @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuerUri) {

    this.insecureEndpoints = endpoints;
    this.corsConfigurationSource = corsConfigurationSource;
    this.issuerUri = issuerUri;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity
        .cors(cors -> cors.configurationSource(corsConfigurationSource))
        .httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(seession -> seession.sessionCreationPolicy(STATELESS))
        .logout(logoutConfigurer -> logoutConfigurer
            .logoutUrl("/sso/logout").permitAll()
            .logoutSuccessUrl("/")
        )
        .authorizeHttpRequests(auth -> {
                                 for (String insecureEndpoint : insecureEndpoints) {
                                   auth.requestMatchers(GET, insecureEndpoint).permitAll();
                                 }
                                 auth.requestMatchers(POST, CREATE_FITNESS_HEALTH_DATA).permitAll();
                                 auth.anyRequest().authenticated();
                               }
        )
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                                  jwt -> jwt.jwkSetUri(issuerUri + "/protocol/openid-connect/certs")
                              )
        );

    return httpSecurity.build();
  }

  @PostConstruct
  public void postConstruct() {

    log.info("Using OAuth2 Security configuration");
  }
}
