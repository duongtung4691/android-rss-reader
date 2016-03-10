package com.nshmura.feed.data.repository;

import android.content.SharedPreferences;
import com.nshmura.feed.data.PreferencesStore;
import com.nshmura.feed.data.preference.EnumPreference;
import com.nshmura.feed.domain.entity.GroupBy;
import com.nshmura.feed.domain.entity.Sort;
import com.nshmura.feed.domain.repository.SettingsRepository;
import info.metadude.android.typedpreferences.BooleanPreference;
import rx.Observable;

public class SettingsDataRepository implements SettingsRepository {

  private final EnumPreference<GroupBy> groupByPreference;
  private final EnumPreference<Sort> sortPreference;
  private final BooleanPreference autoReadPreference;

  public SettingsDataRepository(SharedPreferences sharedPreferences) {
    this.groupByPreference = PreferencesStore.getGroupBySetting(sharedPreferences);
    this.sortPreference = PreferencesStore.getSortSetting(sharedPreferences);
    this.autoReadPreference = PreferencesStore.getAutoReadSetting(sharedPreferences);
  }

  @Override public Observable<GroupBy> getGroupBySetting() {
    return Observable.just(groupByPreference.get());
  }

  @Override public void setGroupBySetting(GroupBy groupBy) {
    groupByPreference.set(groupBy);
  }

  @Override public Observable<Sort> getSortSettings() {
    return Observable.just(sortPreference.get());
  }

  @Override public void setSortSettings(Sort sort) {
    sortPreference.set(sort);
  }

  @Override public Observable<Boolean> getAutoReadSetting() {
    return Observable.just(autoReadPreference.get());
  }

  @Override public void setAutoReadSetting(boolean checked) {
    autoReadPreference.set(checked);
  }
}