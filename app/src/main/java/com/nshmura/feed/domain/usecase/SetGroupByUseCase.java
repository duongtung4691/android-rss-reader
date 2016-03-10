package com.nshmura.feed.domain.usecase;

import com.nshmura.feed.domain.entity.GroupBy;
import com.nshmura.feed.domain.repository.SettingsRepository;
import javax.inject.Inject;

public class SetGroupByUseCase {

  private final SettingsRepository settingsRepository;

  @Inject public SetGroupByUseCase(SettingsRepository settingsRepository) {
    this.settingsRepository = settingsRepository;
  }

  public void execute(GroupBy groupBy) {
    settingsRepository.setGroupBySetting(groupBy);
  }
}
