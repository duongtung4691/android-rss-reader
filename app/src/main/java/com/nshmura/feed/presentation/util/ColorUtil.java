package com.nshmura.feed.presentation.util;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.util.TypedValue;

public class ColorUtil {

  @ColorInt
  public static int readColor(Context context, @AttrRes int attrRes) {
    TypedValue typedValue = new TypedValue();
    context.getTheme().resolveAttribute(attrRes, typedValue, true);
    return typedValue.data;
  }
}
