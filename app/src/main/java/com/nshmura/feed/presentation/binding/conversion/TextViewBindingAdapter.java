package com.nshmura.feed.presentation.binding.conversion;

import android.databinding.BindingAdapter;
import android.widget.TextView;
import java.util.Date;

@SuppressWarnings("unused")
public class TextViewBindingAdapter {

  @BindingAdapter("dateText")
  public static void setDateText(TextView textView, long dataTime) {
    textView.setText(new Date(dataTime).toString()); //TODO
  }
}
