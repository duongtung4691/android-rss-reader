package com.nshmura.feed.domain.usecase;

import com.nshmura.feed.domain.entity.GroupBy;
import com.nshmura.feed.domain.repository.SettingsRepository;
import javax.inject.Inject;
import rx.Observable;

public class GetGroupByUseCase {

  private final SettingsRepository settingsRepository;

  @Inject public GetGroupByUseCase(SettingsRepository settingsRepository) {
    this.settingsRepository = settingsRepository;
  }

  public Observable<GroupBy> execute() {
    return settingsRepository.getGroupBySetting();
  }
}