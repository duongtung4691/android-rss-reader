package com.nshmura.feed.domain.usecase;

import com.nshmura.feed.domain.repository.SettingsRepository;
import javax.inject.Inject;
import rx.Observable;

public class GetAutoReadUseCase {

  private final SettingsRepository settingsRepository;

  @Inject public GetAutoReadUseCase(SettingsRepository settingsRepository) {
    this.settingsRepository = settingsRepository;
  }

  public Observable<Boolean> execute() {
    return settingsRepository.getAutoReadSetting();
  }
}