package com.nshmura.feed.di;

import com.nshmura.feed.MainApplication;
import com.nshmura.feed.data.repository.ApiKeyRepository;
import com.nshmura.feed.di.module.ApplicationModule;
import com.nshmura.feed.di.module.DataModule;
import com.nshmura.feed.di.module.RepositoryModule;
import com.nshmura.feed.di.annotations.BackgroundBus;
import com.nshmura.feed.domain.repository.AuthRepository;
import com.nshmura.feed.domain.repository.RssChannelRepository;
import com.nshmura.feed.domain.repository.SettingsRepository;
import com.nshmura.feed.domain.repository.ThemeRepository;
import dagger.Component;
import de.greenrobot.event.EventBus;
import javax.inject.Singleton;

@Singleton @Component(modules = {
    ApplicationModule.class, DataModule.class, RepositoryModule.class
}) public interface ApplicationComponent {
  void inject(MainApplication mainApplication);

  //Exposed to sub-graphs.
  @BackgroundBus EventBus eventBus();

  ApiKeyRepository apiKeyRepository();

  ThemeRepository themeRepository();

  AuthRepository authRepository();

  RssChannelRepository rssChannelRepository();

  SettingsRepository settingsRepository();
}