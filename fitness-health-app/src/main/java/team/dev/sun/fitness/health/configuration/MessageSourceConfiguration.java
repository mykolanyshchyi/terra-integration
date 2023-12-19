package team.dev.sun.fitness.health.configuration;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageSourceConfiguration {

  private static final String MESSAGE_SOURCE_BEAN = "messageSource";

  private static final String MESSAGE_SOURCE_BASENAME = "classpath*:messages*";

  @Bean(MESSAGE_SOURCE_BEAN)
  public ReloadableResourceBundleMessageSource messageSource() {

    ReloadableResourceBundleMessageSource messageBundle = new PathPatternReloadableResourceBundleMessageSource();
    messageBundle.setBasename(MESSAGE_SOURCE_BASENAME);
    messageBundle.setDefaultEncoding(StandardCharsets.UTF_8.name());
    return messageBundle;
  }

  @Bean
  public MessageSourceAccessor messageSourceAccessor() {

    return new MessageSourceAccessor(messageSource(), getLocale());
  }
}
