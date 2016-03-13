package com.nshmura.feed.presentation.view.page.entrydetail;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewGroupCompat;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;
import com.nshmura.feed.R;
import com.nshmura.feed.databinding.ActivityEntryDetailBinding;
import com.nshmura.feed.domain.entity.RssChannel;
import com.nshmura.feed.domain.entity.RssEntry;
import com.nshmura.feed.presentation.util.ColorUtil;
import com.nshmura.feed.presentation.util.ViewTreeUtil;
import com.nshmura.feed.presentation.view.page.BaseActivity;
import com.nshmura.feed.presentation.view.transition.TransitionNames;
import com.nshmura.feed.presentation.view.widget.EntryWebView;
import com.nshmura.feed.presentation.viewmodel.EntryDetailViewModel;
import javax.inject.Inject;
import timber.log.Timber;

public class EntryDetailActivity extends BaseActivity {

  private static final String EXTRA_RSS_ENTRY = "EXTRA_RSS_ENTRY";
  private static final String EXTRA_RSS_CHANNEL = "EXTRA_RSS_CHANNEL";

  @Inject EntryDetailViewModel viewModel;
  private ActivityEntryDetailBinding binding;
  private boolean isFabVisile;

  public static Intent createIntent(Context context, RssChannel rssChannel, RssEntry rssEntry) {
    Intent intent = new Intent(context, EntryDetailActivity.class);
    intent.putExtra(EXTRA_RSS_CHANNEL, rssChannel);
    intent.putExtra(EXTRA_RSS_ENTRY, rssEntry);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    component().inject(this);

    binding = bindContentView(R.layout.activity_entry_detail);
    binding.setViewModel(viewModel);
    binding.setEventHandler(this);

    RssChannel rssChannel = (RssChannel) getIntent().getSerializableExtra(EXTRA_RSS_CHANNEL);
    RssEntry rssEntry = (RssEntry) getIntent().getSerializableExtra(EXTRA_RSS_ENTRY);
    viewModel.setRssChannel(rssChannel);
    viewModel.setRssEntry(rssEntry);

    setSupportActionBar(binding.toolbar);
    //noinspection ConstantConditions
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    ViewTreeUtil.addOnGlobalLayoutListener(binding.webview, () -> {
      int margin = getResources().getDimensionPixelSize(R.dimen.space_small);
      binding.webview.setTopSpaceDp(binding.header.getBottom() + margin);
      binding.webview.loadEntryData(viewModel.rssEntry.body);
    });

    binding.webview.setOnScrollListener(createOnScrollListener());

    //Transtion
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      ViewCompat.setTransitionName(binding.toolbar, TransitionNames.TOOLBAR);
      ViewCompat.setTransitionName(binding.header, TransitionNames.HEADER);
      ViewCompat.setTransitionName(binding.webviewRect, TransitionNames.CONTENT);
      ViewCompat.setTransitionName(binding.title, TransitionNames.TITLE);
      ViewCompat.setTransitionName(binding.publisher, TransitionNames.PUBLISHER);
      ViewGroupCompat.setTransitionGroup(binding.header, false);
      enterAnimation();
    }
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP) private void enterAnimation() {
    Transition transition = getDefaultEnterAnimation();
    getWindow().setExitTransition(transition);
    getWindow().setEnterTransition(transition);

    binding.webview.setVisibility(View.INVISIBLE);
    binding.fabBrowser.setVisibility(View.INVISIBLE);
    new Handler().postDelayed(() -> {
      if (isFinishing()) {
        return;
      }
      binding.webview.setVisibility(View.VISIBLE);
      binding.webview.setAlpha(0f);
      binding.webview.animate().alpha(1f).setDuration(TRANSITION_TIME);

      binding.fabBrowser.setVisibility(View.VISIBLE);
      showFab(true);
    }, TRANSITION_TIME);
  }

  @Override public void finishAfterTransition() {
    binding.toolbar.setTranslationY(0);
    binding.header.setTranslationY(0);
    binding.fabBrowser.setVisibility(View.GONE);
    binding.webview.setOnScrollListener(null);
    super.finishAfterTransition();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    viewModel.destroy();
  }

  public void onClickBrowser(View v) {
    //share
    Intent shareIntent = ShareCompat.IntentBuilder.from(this)
        .setText(viewModel.rssEntry.link)
        .setType("text/plain")
        .getIntent();
    PendingIntent pendingIntent =
        PendingIntent.getActivity(getApplicationContext(), 0, shareIntent, 0);
    Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_share_white_24dp);

    //build
    CustomTabsIntent intent = new CustomTabsIntent.Builder().setShowTitle(true)
        .setToolbarColor(ColorUtil.readColor(this, android.R.attr.colorBackground))
        .setActionButton(icon, getString(R.string.share), pendingIntent)
        .setStartAnimations(this, R.anim.bottom_enter, R.anim.bottom_exit)
        .setExitAnimations(this, R.anim.bottom_reenter, R.anim.bottom_return)
        .build();

    //start
    intent.launchUrl(this, Uri.parse(viewModel.rssEntry.link));
  }

  private EntryWebView.OnScrollListener createOnScrollListener() {
    int elevation = getResources().getDimensionPixelSize(R.dimen.toolbar_elevation);

    return (x, y, oldX, oldY) -> {
      int diff = y - oldY;

      //toolbar
      int toolbarY = (int) (binding.toolbar.getTranslationY() - diff);
      Timber.d("%s %s %s %s", y, oldY, diff, toolbarY);
      toolbarY = Math.min(0, Math.max(toolbarY, -binding.toolbar.getMeasuredHeight()));
      binding.toolbar.setTranslationY(toolbarY);

      //header
      int headerY = (int) (binding.header.getTranslationY() - diff);
      binding.header.setTranslationY(headerY);

      //toolbar elevation
      int toolbarBottom = binding.toolbar.getMeasuredHeight() + toolbarY;
      int headerTop = binding.header.getTop() + headerY;
      ViewCompat.setElevation(binding.toolbar, toolbarBottom <= headerTop ? 0 : elevation);
    };
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP) private void showFab(boolean visible) {
    if (isFabVisile == visible) {
      return;
    }
    isFabVisile = visible;

    int r = binding.fabBrowser.getHeight() / 2;
    if (visible) {
      binding.fabBrowser.setVisibility(View.VISIBLE);
      ViewAnimationUtils.createCircularReveal(binding.fabBrowser, r, r, 0, r).start();
    } else {
      Animator animator = ViewAnimationUtils.createCircularReveal(binding.fabBrowser, r, r, r, 0);
      animator.addListener(new Animator.AnimatorListener() {
        @Override public void onAnimationStart(Animator animation) {

        }

        @Override public void onAnimationEnd(Animator animation) {
          binding.fabBrowser.setVisibility(View.GONE);
        }

        @Override public void onAnimationCancel(Animator animation) {

        }

        @Override public void onAnimationRepeat(Animator animation) {

        }
      });
      animator.start();
    }
  }
}
