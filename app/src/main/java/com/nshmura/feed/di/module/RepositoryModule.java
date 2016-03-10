package com.nshmura.feed.di.module;

import android.content.SharedPreferences;
import com.nshmura.feed.data.repository.ApiKeyRepository;
import com.nshmura.feed.data.repository.AuthDataRepository;
import com.nshmura.feed.data.repository.RssChannelDataRepository;
import com.nshmura.feed.data.repository.SettingsDataRepository;
import com.nshmura.feed.data.repository.ThemeDataRepository;
import com.nshmura.feed.data.web.LdrApi;
import com.nshmura.feed.data.web.LoginApi;
import com.nshmura.feed.domain.repository.AuthRepository;
import com.nshmura.feed.domain.repository.RssChannelRepository;
import com.nshmura.feed.domain.repository.SettingsRepository;
import com.nshmura.feed.domain.repository.ThemeRepository;
import dagger.Provides;
import javax.inject.Singleton;

@dagger.Module public class RepositoryModule {

  @Provides @Singleton ApiKeyRepository providesApiKeyRepository(
      SharedPreferences sharedPreferences) {
    return new ApiKeyRepository(sharedPreferences);
  }

  @Provides @Singleton ThemeRepository providesThemeRepository(
      SharedPreferences sharedPreferences) {
    return new ThemeDataRepository(sharedPreferences);
  }

  @Provides @Singleton AuthRepository providesAuthRepository(SharedPreferences sharedPreferences,
      LoginApi loginApi, ApiKeyRepository apiKeyRepository) {
    return new AuthDataRepository(sharedPreferences, apiKeyRepository, loginApi);
  }

  @Provides @Singleton RssChannelRepository providesRssChannelRepository(LdrApi ldrApi,
      ApiKeyRepository apiKeyRepository) {
    return new RssChannelDataRepository(ldrApi, apiKeyRepository);
  }

  @Provides @Singleton SettingsRepository providesSettingsRepository(
      SharedPreferences sharedPreferences) {
    return new SettingsDataRepository(sharedPreferences);
  }
}