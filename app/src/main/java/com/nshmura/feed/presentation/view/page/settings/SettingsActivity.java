package com.nshmura.feed.presentation.view.page.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import com.nshmura.feed.MainApplication;
import com.nshmura.feed.R;
import com.nshmura.feed.databinding.ActivitySettingsBinding;
import com.nshmura.feed.presentation.entity.GroupByName;
import com.nshmura.feed.presentation.entity.SortName;
import com.nshmura.feed.presentation.view.dialog.NamedEnumDialog;
import com.nshmura.feed.presentation.event.ShutdownEvent;
import com.nshmura.feed.presentation.view.page.BaseActivity;
import com.nshmura.feed.presentation.view.page.launcher.LauncherActivity;
import com.nshmura.feed.presentation.view.page.home.HomeActivity;
import com.nshmura.feed.presentation.subscriber.BaseSubscriber;
import com.nshmura.feed.presentation.viewmodel.SettingsViewModel;
import de.greenrobot.event.Subscribe;
import javax.inject.Inject;

public class SettingsActivity extends BaseActivity {

  @Inject SettingsViewModel viewModel;

  public static Intent createIntent(Context context) {
    return new Intent(context, SettingsActivity.class);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    get(this).component().inject(this);

    ActivitySettingsBinding binding = bindContentView(R.layout.activity_settings);
    binding.setViewModel(viewModel);
    binding.setEventHandler(this);

    setSupportActionBar(binding.toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override protected void onResume() {
    super.onResume();
    MainApplication.getBus(this).register(this);
  }

  @Override protected void onPause() {
    super.onPause();
    MainApplication.getBus(this).unregister(this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    viewModel.destroy();
  }

  public void onChangeGroup(View v) {
    NamedEnumDialog.newInstance(viewModel.getGroupByName(), GroupByName.class)
        .show(getSupportFragmentManager(), "dialog");
  }

  public void onChangeSort(View v) {
    NamedEnumDialog.newInstance(viewModel.getSortName(), SortName.class)
        .show(getSupportFragmentManager(), "dialog");
  }

  public void onChangeAutoRead(View v) {
    viewModel.toggleAutoRead();
  }

  public void onSwitchTheme(View v) {
    viewModel.switchTheme();
    startActivity(HomeActivity.createIntent(SettingsActivity.this));
    backgroundBus.post(new ShutdownEvent());
  }

 public void onLogout(View v) {
    viewModel.logout(new BaseSubscriber<Boolean>() {
      @Override public void onError(Throwable e) {
        //TODO
      }

      @Override public void onNext(Boolean aBoolean) {
        startActivity(LauncherActivity.createIntent(SettingsActivity.this));
        backgroundBus.post(new ShutdownEvent());
      }
    });
  }

  @SuppressWarnings("unused") @Subscribe
  public void onGroupBySelected(NamedEnumDialog.SelectedEvent e) {
    if (e.value instanceof GroupByName) {
      viewModel.setGroupByName((GroupByName) e.value);
    } else if (e.value instanceof SortName) {
      viewModel.setSortName((SortName) e.value);
    }
  }
}
