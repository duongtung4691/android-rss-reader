package com.nshmura.feed.presentation.viewmodel;

import android.databinding.ObservableField;
import com.nshmura.feed.domain.entity.RssEntry;
import javax.inject.Inject;

public class WebViewModel extends BaseViewModel {

  public final ObservableField<RssEntry> rssEntry = new ObservableField<>();

  @Inject public WebViewModel() {

  }
}
