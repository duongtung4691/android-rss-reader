package com.nshmura.feed.presentation.view.page.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import com.nshmura.feed.R;
import com.nshmura.feed.databinding.ActivityWebviewBinding;
import com.nshmura.feed.domain.entity.RssEntry;
import com.nshmura.feed.presentation.view.page.BaseActivity;
import com.nshmura.feed.presentation.viewmodel.WebViewModel;
import javax.inject.Inject;

public class WebViewActivity extends BaseActivity {

  private static final String EXTRA_RSS_ENTRY = "EXTRA_RSS_ENTRY";

  @Inject WebViewModel viewModel;
  private ActivityWebviewBinding binding;

  public static Intent createIntent(Context context, RssEntry rssEntry) {
    Intent intent = new Intent(context, WebViewActivity.class);
    intent.putExtra(EXTRA_RSS_ENTRY, rssEntry);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    component().inject(this);

    binding = bindContentView(R.layout.activity_webview);
    binding.setViewModel(viewModel);

    RssEntry rssEntry = (RssEntry) getIntent().getSerializableExtra(EXTRA_RSS_ENTRY);
    viewModel.rssEntry.set(rssEntry);

    setSupportActionBar(binding.toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle(rssEntry.title);
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    binding.webview.setWebViewClient(new WebViewClient());
    binding.webview.setWebChromeClient(new WebChromeClient());
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    viewModel.destroy();
  }
}
