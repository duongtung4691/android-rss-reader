package com.nshmura.feed.presentation.view.page.entrylist;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import com.nshmura.feed.R;
import com.nshmura.feed.databinding.ActivityEntryListBinding;
import com.nshmura.feed.domain.entity.RssChannel;
import com.nshmura.feed.domain.entity.RssChannelDetail;
import com.nshmura.feed.presentation.subscriber.BaseSubscriber;
import com.nshmura.feed.presentation.util.AnimUtil;
import com.nshmura.feed.presentation.util.ColorUtil;
import com.nshmura.feed.presentation.view.page.BaseActivity;
import com.nshmura.feed.presentation.view.transition.TransitionNames;
import com.nshmura.feed.presentation.viewmodel.EntryListViewModel;
import javax.inject.Inject;

public class EntryListActivity extends BaseActivity {

  private static final String EXTRA_RSS_CHANNEL = "EXTRA_RSS_CHANNEL";

  @Inject EntryListViewModel viewModel;
  private RssChannel rssChannel;
  private EntryListAdapter adapter;
  private ActivityEntryListBinding binding;

  public static Intent createIntent(Context context, RssChannel rssChannel) {
    Intent intent = new Intent(context, EntryListActivity.class);
    intent.putExtra(EXTRA_RSS_CHANNEL, rssChannel);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    component().inject(this);
    binding = bindContentView(R.layout.activity_entry_list);

    rssChannel = (RssChannel) getIntent().getSerializableExtra(EXTRA_RSS_CHANNEL);
    binding.setViewModel(viewModel);
    viewModel.setRssChannel(rssChannel);

    setSupportActionBar(binding.toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    adapter = new EntryListAdapter(getLayoutInflater(), rssChannel);
    binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    binding.recyclerView.setAdapter(adapter);

    //Transtion
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      ViewCompat.setTransitionName(binding.container, TransitionNames.HEADER);
      ViewCompat.setTransitionName(binding.toolbarTitle, TransitionNames.TITLE);
      enterAnimation();
      getWindow().setReturnTransition(getWindow().getReturnTransition().clone());
      getWindow().getReturnTransition().addListener(shotReturnHomeListener);
    }

    new Handler().postDelayed(() -> {
      if (!isFinishing()) {
        viewModel.loadData(rssChannel.subscribeId, new OnLoadData());
      }
    }, 300);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    viewModel.destroy();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void enterAnimation() {
    binding.toolbar.setAlpha(0);
    binding.toolbar.animate().alpha(1f).setDuration(TRANSITION_TIME).start();
  }

  private void viewEnterAnimation(View view, float offset, Interpolator interp) {
    view.setTranslationY(offset);
    view.setAlpha(0.8f);
    view.animate()
        .translationY(0f)
        .alpha(1f)
        .setDuration(200)
        .setInterpolator(interp)
        .setListener(null)
        .start();
  }

  private Transition.TransitionListener shotReturnHomeListener =
      new AnimUtil.TransitionListenerAdapter() {
        @Override public void onTransitionStart(Transition transition) {
          ViewCompat.setElevation(binding.toolbar, 0);

          LinearOutSlowInInterpolator interp = new LinearOutSlowInInterpolator();

          super.onTransitionStart(transition);
          binding.toolbar.animate().alpha(0f).setDuration(300).setInterpolator(interp);

          binding.recyclerView.animate().alpha(0f).setDuration(300).setInterpolator(interp);

          Context context = EntryListActivity.this;

          AnimatorSet animator = new AnimatorSet();
          animator.playTogether(
              ObjectAnimator.ofObject(binding.container, "backgroundColor", new ArgbEvaluator(),
                  ColorUtil.readColor(context, R.attr.cardListBackgroundColor),
                  ColorUtil.readColor(context, android.R.attr.colorBackground)),

              ObjectAnimator.ofObject(binding.toolbarTitle, "textColor", new ArgbEvaluator(),
                  ColorUtil.readColor(context, R.attr.toolbarTextColor),
                  ColorUtil.readColor(context, android.R.attr.textColorSecondary)));

          animator.setDuration(TRANSITION_TIME);
          animator.setInterpolator(interp);
          animator.start();
        }
      };

  private class OnLoadData extends BaseSubscriber<RssChannelDetail> {
    @Override public void onNext(RssChannelDetail rssEntries) {
      adapter.addAll(rssEntries.items);
      viewModel.getAutoRead(autoRead -> {
        if (autoRead) {
          viewModel.touchAll(rssChannel.subscribeId);
        }
      });
    }
  }
}
