package team.dev.sun.fitness.health.filter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
public class FHRequestLoggingFilter extends CommonsRequestLoggingFilter {

    private final Set<AntPathRequestMatcher> ignorePatternMatchers;

    public FHRequestLoggingFilter(List<String> ignorePatterns) {

        this.ignorePatternMatchers = ignorePatterns.stream().map(AntPathRequestMatcher::new).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    protected boolean shouldLog(final @NotNull HttpServletRequest request) {

        return super.shouldLog(request) && shouldNotBeIgnored(request);
    }

    private boolean shouldNotBeIgnored(final @NotNull HttpServletRequest request) {

        for (AntPathRequestMatcher matcher : ignorePatternMatchers) {
            if (matcher.matches(request)) {
                if (log.isTraceEnabled()) {
                    log.trace("Request ignored because of match with pattern: {}", matcher.getPattern());
                }
                return false;
            }
        }
        return true;
    }

}
