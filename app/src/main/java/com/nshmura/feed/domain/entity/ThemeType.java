package com.nshmura.feed.domain.entity;

import java.io.Serializable;

public enum ThemeType implements Serializable {
  DARK, LIGHT;

  public static ThemeType fromString(String themeTypeString) {
    for (ThemeType themeType : values()) {
      if (themeType.name().equals(themeTypeString)) {
        return themeType;
      }
    }
    return DARK;
  }
}
