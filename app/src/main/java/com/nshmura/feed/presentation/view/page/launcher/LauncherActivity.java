package com.nshmura.feed.presentation.view.page.launcher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.nshmura.feed.R;
import com.nshmura.feed.presentation.view.dialog.LoginDialog;
import com.nshmura.feed.presentation.view.page.BaseActivity;
import com.nshmura.feed.presentation.view.page.home.HomeActivity;
import com.nshmura.feed.presentation.viewmodel.LauncherViewModel;
import javax.inject.Inject;

public class LauncherActivity extends BaseActivity {

  private static final long WAIT_TIME = 500;

  @Inject LauncherViewModel viewModel;

  public static Intent createIntent(Context context) {
    return new Intent(context, LauncherActivity.class);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lancher);
    component().inject(this);
  }

  @Override protected void onResume() {
    super.onResume();

    viewModel.isLogined().subscribe(login -> {
      if (isFinishing()) {
        return;
      }
      if (login) {
        startMainActivity();
      } else {
        showLoginView();
      }
    });
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    viewModel.destroy();
  }

  private void showLoginView() {
    LoginDialog.newInstance().show(getSupportFragmentManager(), "dialog");
  }

  private void startMainActivity() {
    new Handler().postDelayed(() -> {
      if (isFinishing()) {
        return;
      }
      Intent intent = HomeActivity.createIntent(LauncherActivity.this);
      startActivity(intent);
    }, WAIT_TIME);
  }
}
