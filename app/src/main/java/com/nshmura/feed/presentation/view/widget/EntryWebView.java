package com.nshmura.feed.presentation.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.nshmura.feed.presentation.util.FileUtil;
import java.io.IOException;
import timber.log.Timber;

public class EntryWebView extends WebView {

  private static final String CSS_LIST = "style_list.css";
  private static final String CSS_DETAIL = "style_detail.css";

  private int topSpaceDp;
  private OnScrollListener listener;

  public EntryWebView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setup();
  }

  @SuppressLint("SetJavaScriptEnabled") private void setup() {
    setWebViewClient(new EntryWebViewClient());
    setWebChromeClient(new EntryWebChromeClient());

    WebSettings settings = getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setLoadWithOverviewMode(true);
  }

  @Override protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    if (listener != null) {
      listener.onScrollChanged(l, t, oldl, oldt);
    }
  }

  public void setOnScrollListener(OnScrollListener listener) {
    this.listener = listener;
  }

  public interface OnScrollListener {

    void onScrollChanged(int x, int y, int oldX, int oldY);
  }

  public void setTopSpaceDp(int topSpacePx) {
    this.topSpaceDp = (int) (topSpacePx / getResources().getDisplayMetrics().density);
    loadTopSpace();
  }

  private void loadTopSpace() {
    if (topSpaceDp > 0) {
      loadUrl(String.format("javascript:setTopSpace(%s);", topSpaceDp));
    }
  }

  public void loadListData(String body) {
    loadDataWithBaseURL("file:///android_asset/", createHtml(body, CSS_LIST), "text/html", "utf-8",
        null);
  }

  public void loadEntryData(String body) {
    loadDataWithBaseURL("file:///android_asset/", createHtml(body, CSS_DETAIL), "text/html",
        "utf-8", null);
  }

  private String createHtml(String body, String css) {
    try {
      String template = FileUtil.readAssetsFile(getContext(), "entry/entry_webview.html");

      return TextUtils.replace(template, new String[] { "@body@", "@css@" },
          new String[] { body, css }).toString();
    } catch (IOException e) {
      //ignore
    }
    return "";
  }

  private class EntryWebViewClient extends WebViewClient {
    @Override public void onPageFinished(WebView view, String url) {
      loadTopSpace();
    }
  }

  private class EntryWebChromeClient extends WebChromeClient {
    @Override public void onReceivedTitle(WebView view, String title) {
      super.onReceivedTitle(view, title);
      loadTopSpace();
    }

    @Override public boolean onConsoleMessage(ConsoleMessage cm) {
      Timber.d(cm.message() + " -- From line " + cm.lineNumber() + " of " + cm.sourceId());
      return true;
    }
  }
}
