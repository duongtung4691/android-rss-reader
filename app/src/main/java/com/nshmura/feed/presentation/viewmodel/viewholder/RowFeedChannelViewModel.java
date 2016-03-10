package com.nshmura.feed.presentation.viewmodel.viewholder;

import android.app.Activity;
import android.databinding.ObservableField;
import com.nshmura.feed.domain.entity.RssChannel;
import javax.inject.Inject;

public class RowFeedChannelViewModel {
  public final ObservableField<RssChannel> rssChannel = new ObservableField<>();

  @Inject public RowFeedChannelViewModel() {
  }
}