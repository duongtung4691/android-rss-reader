package com.nshmura.feed.presentation.view.page.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import com.nshmura.feed.R;
import com.nshmura.feed.databinding.ActivityMainBinding;
import com.nshmura.feed.presentation.view.page.BaseActivity;
import com.nshmura.feed.presentation.view.page.settings.SettingsActivity;
import com.nshmura.feed.presentation.viewmodel.MainViewModel;
import javax.inject.Inject;

public class HomeActivity extends BaseActivity {

  @Inject MainViewModel viewModel;
  private ActivityMainBinding binding;
  private MainAdapter mainAdapter;

  public static Intent createIntent(Context context) {
    return new Intent(context, HomeActivity.class);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = bindContentView(R.layout.activity_main);
    component().inject(this);

    setSupportActionBar(binding.toolbar);
    //noinspection ConstantConditions
    getSupportActionBar().setTitle(R.string.title_home);

    mainAdapter = new MainAdapter(getLayoutInflater());
    binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    binding.recyclerView.setAdapter(mainAdapter);
    binding.swipeRefreshLayout.setOnRefreshListener(this::loadData);

    loadData();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    viewModel.destroy();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      startActivity(SettingsActivity.createIntent(this));
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void loadData() {
    viewModel.loadData(items -> {
      viewModel.getGroupBy(groupBy -> {
        mainAdapter.setGroupBy(groupBy);
        mainAdapter.clear();
        mainAdapter.addAll(items);
        binding.swipeRefreshLayout.setRefreshing(false);
      });
    });
  }
}
