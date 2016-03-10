package com.nshmura.feed.data.repository;

import android.content.SharedPreferences;
import com.nshmura.feed.data.PreferencesStore;
import info.metadude.android.typedpreferences.StringPreference;

public class ApiKeyRepository {

  private final StringPreference apiKeyPreference;

  public ApiKeyRepository(SharedPreferences sharedPreferences) {
    this.apiKeyPreference = PreferencesStore.getApiKeydPreference(sharedPreferences);
  }

  public String getApiKey() {
    return apiKeyPreference.get();
  }

  public void setApiKey(String apiKey) {
    apiKeyPreference.set(apiKey);
  }
}
