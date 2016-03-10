package com.nshmura.feed.presentation.viewmodel;

import android.databinding.ObservableField;
import com.nshmura.feed.domain.entity.RssChannel;
import com.nshmura.feed.domain.entity.RssChannelDetail;
import com.nshmura.feed.domain.usecase.GetAutoReadUseCase;
import com.nshmura.feed.domain.usecase.GetRssEtriesUserCase;
import com.nshmura.feed.domain.usecase.TouchAllUseCase;
import javax.inject.Inject;
import rx.Observer;
import rx.functions.Action1;

public class EntryListViewModel extends BaseViewModel {

  public final ObservableField<RssChannel> rssChannel = new ObservableField<>();
  private final GetRssEtriesUserCase getRssEtriesUserCase;
  private final TouchAllUseCase touchAllUseCase;
  private final GetAutoReadUseCase getAutoRead;

  @Inject public EntryListViewModel(GetRssEtriesUserCase getRssEtriesUserCase,
      TouchAllUseCase touchAllUseCase, GetAutoReadUseCase getAutoRead) {
    this.getRssEtriesUserCase = getRssEtriesUserCase;
    this.touchAllUseCase = touchAllUseCase;
    this.getAutoRead = getAutoRead;
  }

  public void setRssChannel(RssChannel rssChannel) {
    this.rssChannel.set(rssChannel);
  }

  public void loadData(String subscribeId, Observer<RssChannelDetail> observer) {
    getRssEtriesUserCase.execute(subscribeId).subscribe(observer);
  }

  public void touchAll(String subscribeId) {
    touchAllUseCase.execute(subscribeId).subscribe();
  }

  public void getAutoRead(Action1<Boolean> onNext) {
    addSubscription(getAutoRead.execute().subscribe(onNext));
  }
}
