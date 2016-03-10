package com.nshmura.feed.presentation.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import com.nshmura.feed.domain.usecase.GetAutoReadUseCase;
import com.nshmura.feed.domain.usecase.GetGroupByUseCase;
import com.nshmura.feed.domain.usecase.GetSortUseCase;
import com.nshmura.feed.domain.usecase.LogoutUseCase;
import com.nshmura.feed.domain.usecase.SetAutoReadUseCase;
import com.nshmura.feed.domain.usecase.SetGroupByUseCase;
import com.nshmura.feed.domain.usecase.SetSortUseCase;
import com.nshmura.feed.domain.usecase.SwithThemeUseCase;
import com.nshmura.feed.presentation.entity.GroupByName;
import com.nshmura.feed.presentation.entity.SortName;
import javax.inject.Inject;
import rx.Subscriber;

public class SettingsViewModel extends BaseViewModel {

  public final ObservableInt groupByNameResource = new ObservableInt();
  public final ObservableInt sortNameResource = new ObservableInt();
  public final ObservableBoolean autoRead = new ObservableBoolean();

  private GroupByName groupByName;
  private SortName sortName;

  private SwithThemeUseCase swithThemeUseCase;
  private final LogoutUseCase logoutUseCase;
  private final SetGroupByUseCase setGroupBy;
  private final SetAutoReadUseCase setAutoRead;
  private final SetSortUseCase setSort;

  @Inject public SettingsViewModel(SwithThemeUseCase swithThemeUseCase, LogoutUseCase logoutUseCase,
      GetGroupByUseCase getGroupBy, SetGroupByUseCase setGroupBy, GetAutoReadUseCase getAutoRead,
      SetAutoReadUseCase setAutoRead, GetSortUseCase getSort, SetSortUseCase setSort) {
    this.swithThemeUseCase = swithThemeUseCase;
    this.logoutUseCase = logoutUseCase;
    this.setGroupBy = setGroupBy;
    this.setAutoRead = setAutoRead;
    this.setSort = setSort;

    addSubscription(getGroupBy.execute().map(GroupByName::convert).subscribe(this::setGroupByName));
    addSubscription(getSort.execute().map(SortName::convert).subscribe(this::setSortName));
    addSubscription(getAutoRead.execute().subscribe(this::setAutoRead));
  }


  public GroupByName getGroupByName() {
    return this.groupByName;
  }

  public void setGroupByName(GroupByName groupByName) {
    this.groupByName = groupByName;
    this.groupByNameResource.set(groupByName.getNameResource());
    this.setGroupBy.execute(groupByName.getEntity());
  }

  public SortName getSortName() {
    return this.sortName;
  }

  public void setSortName(SortName sortName) {
    this.sortName = sortName;
    this.sortNameResource.set(sortName.getNameResource());
    this.setSort.execute(sortName.getEntity());
  }

  public void toggleAutoRead() {
    setAutoRead(!autoRead.get());
  }

  public void setAutoRead(boolean autoRead) {
    this.autoRead.set(autoRead);
    this.setAutoRead.execute(autoRead);
  }

  public void switchTheme() {
    swithThemeUseCase.execute();
  }

  public void logout(Subscriber<Boolean> subscriber) {
    addSubscription(logoutUseCase.execute().subscribe(subscriber));
  }
}
