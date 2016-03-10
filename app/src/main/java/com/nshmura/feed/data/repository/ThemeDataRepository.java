package com.nshmura.feed.data.repository;

import android.content.SharedPreferences;
import com.nshmura.feed.data.PreferencesStore;
import com.nshmura.feed.domain.entity.ThemeType;
import com.nshmura.feed.domain.repository.ThemeRepository;
import info.metadude.android.typedpreferences.StringPreference;
import rx.Observable;

public class ThemeDataRepository implements ThemeRepository {

  private final StringPreference themeTypePreference;

  public ThemeDataRepository(SharedPreferences sharedPreferences) {
    this.themeTypePreference = PreferencesStore.getThemeTypePreference(sharedPreferences);
  }

  @Override public Observable<ThemeType> setTheme(ThemeType themeType) {
    themeTypePreference.set(themeType.name());
    return Observable.just(themeType);
  }

  @Override public ThemeType getTheme() {
    return ThemeType.fromString(themeTypePreference.get());
  }
}
