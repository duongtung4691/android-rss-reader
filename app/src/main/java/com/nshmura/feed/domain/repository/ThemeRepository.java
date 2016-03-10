package com.nshmura.feed.domain.repository;

import com.nshmura.feed.domain.entity.ThemeType;
import rx.Observable;

public interface ThemeRepository {

  ThemeType getTheme();

  Observable<ThemeType> setTheme(ThemeType themeType);
}
