package com.nshmura.feed.domain.usecase;

import com.nshmura.feed.domain.entity.Sort;
import com.nshmura.feed.domain.repository.SettingsRepository;
import javax.inject.Inject;

public class SetSortUseCase {

  private final SettingsRepository settingsRepository;

  @Inject public SetSortUseCase(SettingsRepository settingsRepository) {
    this.settingsRepository = settingsRepository;
  }

  public void execute(Sort sort) {
    settingsRepository.setSortSettings(sort);
  }
}
