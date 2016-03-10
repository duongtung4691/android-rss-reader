package com.nshmura.feed.presentation.viewmodel.viewholder;

import android.databinding.ObservableField;
import javax.inject.Inject;

public class RowFeedFolderViewModel {
  public final ObservableField<String> folder = new ObservableField<>();

  @Inject
  public RowFeedFolderViewModel() {

  }
}
