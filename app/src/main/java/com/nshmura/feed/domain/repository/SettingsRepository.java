package com.nshmura.feed.domain.repository;

import com.nshmura.feed.domain.entity.GroupBy;
import com.nshmura.feed.domain.entity.Sort;
import rx.Observable;

public interface SettingsRepository {

  Observable<GroupBy> getGroupBySetting();

  void setGroupBySetting(GroupBy groupBy);

  Observable<Sort> getSortSettings();

  void setSortSettings(Sort sort);

  Observable<Boolean> getAutoReadSetting();

  void setAutoReadSetting(boolean checked);
}
