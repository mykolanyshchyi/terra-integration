package team.dev.sun.fitness.health.controller;

import lombok.SneakyThrows;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(IntegrationTestCondition.class)
public class RsaJsonWebKeyConfig {

  public static final String ALGORITHM_ID = AlgorithmIdentifiers.RSA_USING_SHA256;

  @Bean
  @SneakyThrows
  public RsaJsonWebKey rsaJsonWebKey() {

    final RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
    rsaJsonWebKey.setKeyId("k1");
    rsaJsonWebKey.setAlgorithm(ALGORITHM_ID);
    rsaJsonWebKey.setUse("sig");
    return rsaJsonWebKey;
  }
}