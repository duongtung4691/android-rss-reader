package com.nshmura.feed.domain.usecase;

import com.nshmura.feed.domain.entity.ThemeType;
import com.nshmura.feed.domain.repository.ThemeRepository;
import javax.inject.Inject;
import rx.Observable;

public class SwithThemeUseCase {

  private final ThemeRepository themeRepository;

  @Inject
  public SwithThemeUseCase(ThemeRepository themeRepository) {
    this.themeRepository = themeRepository;
  }

  public Observable<ThemeType> execute() {
    ThemeType theme = themeRepository.getTheme();
    ThemeType newThemeType = theme == ThemeType.DARK ? ThemeType.LIGHT : ThemeType.DARK;
    return themeRepository.setTheme(newThemeType);
  }
}
