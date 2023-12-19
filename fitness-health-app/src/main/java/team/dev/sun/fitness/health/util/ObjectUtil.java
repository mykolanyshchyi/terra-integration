package team.dev.sun.fitness.health.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectUtil {

  public static <S, R> void applyNonNull(Supplier<S> getter, Consumer<R> setter, Function<S, R> converter) {

    final S source = getter.get();
    if (source != null) {
      final R result = converter.apply(source);
      // Prevents overriding default values.
      if (result != null) {
        setter.accept(result);
      }
    }
  }
}
