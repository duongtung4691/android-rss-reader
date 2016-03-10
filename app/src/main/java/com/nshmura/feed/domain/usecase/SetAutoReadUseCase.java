package com.nshmura.feed.domain.usecase;

import com.nshmura.feed.domain.repository.SettingsRepository;
import javax.inject.Inject;

public class SetAutoReadUseCase {

  private final SettingsRepository settingsRepository;

  @Inject public SetAutoReadUseCase(SettingsRepository settingsRepository) {
    this.settingsRepository = settingsRepository;
  }

  public void execute(boolean checked) {
    settingsRepository.setAutoReadSetting(checked);
  }
}
