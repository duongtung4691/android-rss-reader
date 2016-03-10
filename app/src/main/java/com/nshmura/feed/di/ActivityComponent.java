package com.nshmura.feed.di;

import com.nshmura.feed.di.annotations.PerActivity;
import com.nshmura.feed.di.module.ActivityModule;
import com.nshmura.feed.presentation.view.dialog.LoginDialog;
import com.nshmura.feed.presentation.view.page.BaseActivity;
import com.nshmura.feed.presentation.view.page.entrydetail.EntryDetailActivity;
import com.nshmura.feed.presentation.view.page.entrylist.EntryListActivity;
import com.nshmura.feed.presentation.view.page.entrylist.EntryListAdapter;
import com.nshmura.feed.presentation.view.page.home.ChannelViewHolder;
import com.nshmura.feed.presentation.view.page.home.FolderViewHolder;
import com.nshmura.feed.presentation.view.page.home.HomeActivity;
import com.nshmura.feed.presentation.view.page.launcher.LauncherActivity;
import com.nshmura.feed.presentation.view.page.settings.SettingsActivity;
import com.nshmura.feed.presentation.view.page.webview.WebViewActivity;
import dagger.Component;

@PerActivity @Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

  void inject(BaseActivity baseActivity);

  void inject(LauncherActivity launcherActivity);

  void inject(HomeActivity mainActivity);

  void inject(ChannelViewHolder channelViewHolder);

  void inject(FolderViewHolder folderViewHolder);

  void inject(LoginDialog loginDialog);

  void inject(EntryListActivity entryListActivity);

  void inject(EntryDetailActivity entryDetailActivity);

  void inject(WebViewActivity webViewActivity);

  void inject(SettingsActivity settingsActivity);

  void inject(EntryListAdapter.ViewHolder viewHolder);
}