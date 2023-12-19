package team.dev.sun.fitness.health.configuration;

import team.dev.sun.fitness.health.filter.FHRequestLoggingFilter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.BooleanUtils.toBooleanDefaultIfNull;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Setter
@Configuration
@ConfigurationProperties(prefix = "logging.request")
public class RequestLoggingFilterConfig {

    private static final int DEFAULT_MAX_PAYLOAD_LENGTH = 10000;

    private static final boolean DEFAULT_FLAG_VAL = false;

    private Boolean payload;

    private Boolean headers;

    private Boolean clientInfo;

    private Boolean queryString;

    private Integer maxPayloadLength;

    private List<String> ignorePatterns;

    @Bean
    public FHRequestLoggingFilter logFilter() {

        FHRequestLoggingFilter filter = new FHRequestLoggingFilter(defaultIfNull(ignorePatterns, emptyList()));
        filter.setIncludeQueryString(toBooleanDefaultIfNull(queryString, DEFAULT_FLAG_VAL));
        filter.setIncludeClientInfo(toBooleanDefaultIfNull(clientInfo, DEFAULT_FLAG_VAL));
        filter.setIncludeHeaders(toBooleanDefaultIfNull(headers, DEFAULT_FLAG_VAL));
        filter.setIncludePayload(toBooleanDefaultIfNull(payload, DEFAULT_FLAG_VAL));
        filter.setMaxPayloadLength(maxPayloadLength == null ? DEFAULT_MAX_PAYLOAD_LENGTH : maxPayloadLength);
        return filter;
    }
}
