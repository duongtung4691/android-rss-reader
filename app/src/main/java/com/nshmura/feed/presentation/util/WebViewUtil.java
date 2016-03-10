package com.nshmura.feed.presentation.util;

import android.os.Build;
import android.webkit.WebView;

public class WebViewUtil {
  public static void setWebContentsDebuggingEnabled(boolean enabled) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      WebView.setWebContentsDebuggingEnabled(enabled);
    }
  }
}
