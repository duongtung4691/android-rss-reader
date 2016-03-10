package com.nshmura.feed.data.preference;

import android.content.SharedPreferences;
import info.metadude.android.typedpreferences.StringPreference;

public class EnumPreference<T extends Enum<T>> {

  private final StringPreference sharedPreferences;
  private Class<T> clazz;

  public EnumPreference(Class<T> clazz, SharedPreferences sharedPreferences, String key,
      Enum<T> defaultValue) {
    this.clazz = clazz;
    this.sharedPreferences = new StringPreference(sharedPreferences, key, defaultValue.name());
  }

  public T get() {
    return Enum.valueOf(clazz, sharedPreferences.get());
  }

  public void set(T value) {
    sharedPreferences.set(value.name());
  }
}
