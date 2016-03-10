package com.nshmura.feed.presentation.binding.conversion;

import android.databinding.BindingAdapter;
import android.webkit.WebView;

@SuppressWarnings("unused")
public class WebViewBindingAdapter {

  @BindingAdapter("loadUrl")
  public static void loadUrl(WebView webView, String url) {
    webView.loadUrl(url);
  }
}
