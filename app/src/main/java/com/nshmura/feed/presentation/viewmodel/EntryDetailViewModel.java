package com.nshmura.feed.presentation.viewmodel;

import com.nshmura.feed.domain.entity.RssChannel;
import com.nshmura.feed.domain.entity.RssEntry;
import javax.inject.Inject;

public class EntryDetailViewModel extends BaseViewModel {

  public RssEntry rssEntry;
  public RssChannel rssChannel;

  @Inject public EntryDetailViewModel() {
  }

  public void setRssEntry(RssEntry rssEntry) {
    this.rssEntry = rssEntry;
  }

  public void setRssChannel(RssChannel rssChannel) {
    this.rssChannel = rssChannel;
  }
}
