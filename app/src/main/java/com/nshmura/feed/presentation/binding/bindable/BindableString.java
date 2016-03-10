package com.nshmura.feed.presentation.binding.bindable;

import android.databinding.BaseObservable;
import com.nshmura.feed.util.Objects;

public class BindableString extends BaseObservable {
  private String value;
  public String get() {
    return value != null ? value : "";
  }
  public void set(String value) {
    if (!Objects.equals(this.value, value)) {
      this.value = value;
      notifyChange();
    }
  }
  public boolean isEmpty() {
    return value == null || value.isEmpty();
  }
}