package team.dev.sun.fitness.health.api.model;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ActivityLevelType {
  UNKNOWN(0), REST(1), INACTIVE(2), LOW_INTENSITY(3), MEDIUM_INTENSITY(4), HIGH_INTENSITY(5);

  private static final Map<Integer, ActivityLevelType> map = new HashMap<>();

  static {
    for (ActivityLevelType type : ActivityLevelType.values()) {
      map.put(type.level, type);
    }
  }

  private final int level;

  public static ActivityLevelType byIntLevel(int type) {

    ActivityLevelType levelType = map.get(type);
    if (levelType == null) {
      throw new IllegalArgumentException("Cannot get ActivityLevelType by: " + type);
    }
    return levelType;
  }
}
