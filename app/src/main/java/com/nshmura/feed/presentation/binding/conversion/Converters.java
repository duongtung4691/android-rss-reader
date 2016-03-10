package com.nshmura.feed.presentation.binding.conversion;

import android.databinding.BindingConversion;
import com.nshmura.feed.presentation.binding.bindable.BindableBoolean;
import com.nshmura.feed.presentation.binding.bindable.BindableString;

@SuppressWarnings("unused")
public class Converters {

  @BindingConversion
  public static String convertBindableToString(BindableString bindableString) {
    return bindableString.get();
  }

  @BindingConversion
  public static boolean convertBindableToBoolean(BindableBoolean bindableBoolean) {
    return bindableBoolean.get();
  }
}
