package com.nshmura.feed.presentation.view.page;

import android.annotation.TargetApi;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Transition;
import android.view.MenuItem;
import com.nshmura.feed.MainApplication;
import com.nshmura.feed.di.ActivityComponent;
import com.nshmura.feed.di.DaggerActivityComponent;
import com.nshmura.feed.di.annotations.BackgroundBus;
import com.nshmura.feed.di.module.ActivityModule;
import com.nshmura.feed.presentation.event.ShutdownEvent;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity {

  public static final long TRANSITION_TIME = 600;

  @Inject @BackgroundBus protected EventBus backgroundBus;
  private ActivityComponent componet;

  public static BaseActivity get(Context context) {
    return ((BaseActivity) context);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    setTheme(MainApplication.get(this).getActivityTheme());
    super.onCreate(savedInstanceState);

    componet = DaggerActivityComponent.builder()
        .applicationComponent(MainApplication.get(this).component())
        .activityModule(new ActivityModule(this))
        .build();

    componet.inject(this);

    backgroundBus.register(this);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        onBackPressed();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    backgroundBus.unregister(this);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP) public Transition getDefaultEnterAnimation() {
    Transition fade = new Fade();
    fade.excludeTarget(android.R.id.statusBarBackground, true);
    fade.excludeTarget(android.R.id.navigationBarBackground, true);
    return fade;
  }

  @SuppressWarnings("unused") @Subscribe public void onShutdown(ShutdownEvent e) {
    finish();
  }

  public <T extends ViewDataBinding> T bindContentView(@LayoutRes int resourceId) {
    return DataBindingUtil.setContentView(this, resourceId);
  }

  public ActivityComponent component() {
    return this.componet;
  }
}
