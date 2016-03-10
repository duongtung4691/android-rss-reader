package com.nshmura.feed.domain.usecase;

import com.nshmura.feed.domain.entity.Sort;
import com.nshmura.feed.domain.repository.SettingsRepository;
import javax.inject.Inject;
import rx.Observable;

public class GetSortUseCase {

  private final SettingsRepository settingsRepository;

  @Inject public GetSortUseCase(SettingsRepository settingsRepository) {
    this.settingsRepository = settingsRepository;
  }

  public Observable<Sort> execute() {
    return settingsRepository.getSortSettings();
  }
}