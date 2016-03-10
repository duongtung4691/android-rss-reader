package com.nshmura.feed.presentation.viewmodel;

import com.nshmura.feed.domain.entity.GroupBy;
import com.nshmura.feed.domain.entity.RssChannel;
import com.nshmura.feed.domain.usecase.GetGroupByUseCase;
import com.nshmura.feed.domain.usecase.GetRssChannelsUseCase;
import java.util.List;
import javax.inject.Inject;
import rx.functions.Action1;

public class MainViewModel extends BaseViewModel {

  private final GetRssChannelsUseCase getResChannelsUseCase;
  private final GetGroupByUseCase getGroupByUseCase;

  @Inject public MainViewModel(GetRssChannelsUseCase getResChannelsUseCase,
      GetGroupByUseCase getGroupByUseCase) {
    this.getResChannelsUseCase = getResChannelsUseCase;
    this.getGroupByUseCase = getGroupByUseCase;
  }

  public void loadData(Action1<List<RssChannel>> onNext) {
    getResChannelsUseCase.execute(1, 0, 0).subscribe(onNext);
  }

  public void getGroupBy(Action1<GroupBy> onNext) {
    getGroupByUseCase.execute().subscribe(onNext);
  }
}
