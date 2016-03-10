package com.nshmura.feed.presentation.viewmodel.viewholder;

import android.databinding.ObservableField;
import com.nshmura.feed.domain.entity.RssChannel;
import com.nshmura.feed.domain.entity.RssEntry;
import javax.inject.Inject;

public class RowEntryListWebViewViewModel {

  public final ObservableField<RssEntry> rssEntry = new ObservableField<>();
  private RssChannel rssChannel;

  @Inject public RowEntryListWebViewViewModel() {
  }

  public void setRssChannel(RssChannel rssChannel) {
    this.rssChannel = rssChannel;
  }

  public RssChannel geRssChannel() {
    return rssChannel;
  }
}
