package team.dev.sun.fitness.health.configuration;

import static org.springframework.core.io.support.ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX;

import java.io.IOException;
import java.util.Properties;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class PathPatternReloadableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

  private static final String PROPERTIES_SUFFIX = ".properties";

  private final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

  @Override
  protected PropertiesHolder refreshProperties(String filename, PropertiesHolder propHolder) {

    if (filename.startsWith(CLASSPATH_ALL_URL_PREFIX)) {
      return refreshClassPathProperties(filename, propHolder);
    } else {
      return super.refreshProperties(filename, propHolder);
    }
  }

  private PropertiesHolder refreshClassPathProperties(String filename, PropertiesHolder propHolder) {

    Properties properties = new Properties();
    long lastModified = -1;
    try {
      Resource[] resources = resolver.getResources(filename + PROPERTIES_SUFFIX);
      for (Resource resource : resources) {
        String sourcePath = resource.getURI().toString().replace(PROPERTIES_SUFFIX, "");
        PropertiesHolder holder = super.refreshProperties(sourcePath, propHolder);
        properties.putAll(holder.getProperties());
        if (lastModified < resource.lastModified()) {
          lastModified = resource.lastModified();
        }
      }
    } catch (IOException ignored) {
      //ignored
    }
    return new PropertiesHolder(properties, lastModified);
  }
}
