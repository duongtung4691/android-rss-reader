package com.nshmura.feed.data;

import android.content.Context;
import android.content.SharedPreferences;
import com.nshmura.feed.data.preference.EnumPreference;
import com.nshmura.feed.domain.entity.GroupBy;
import com.nshmura.feed.domain.entity.Sort;
import info.metadude.android.typedpreferences.BooleanPreference;
import info.metadude.android.typedpreferences.StringPreference;

import static android.content.Context.MODE_PRIVATE;

public class PreferencesStore {

  public static SharedPreferences getSharedPreferences(Context context) {
    return context.getSharedPreferences("app", MODE_PRIVATE);
  }

  public static StringPreference getThemeTypePreference(SharedPreferences sharedPreferences) {
    return new StringPreference(sharedPreferences, "theme_type");
  }

  public static StringPreference getIdPreference(SharedPreferences sharedPreferences) {
    return new StringPreference(sharedPreferences, "id");
  }

  public static StringPreference getPaswordPreference(SharedPreferences sharedPreferences) {
    return new StringPreference(sharedPreferences, "password");
  }

  public static StringPreference getApiKeydPreference(SharedPreferences sharedPreferences) {
    return new StringPreference(sharedPreferences, "api_key");
  }

  public static EnumPreference<GroupBy> getGroupBySetting(SharedPreferences sharedPreferences) {
    return new EnumPreference<>(GroupBy.class, sharedPreferences, "group_by", GroupBy.FOLDER);
  }

  public static EnumPreference<Sort> getSortSetting(SharedPreferences sharedPreferences) {
    return new EnumPreference<>(Sort.class, sharedPreferences, "sort", Sort.MODIFIED_ON_DESC);
  }

  public static BooleanPreference getAutoReadSetting(SharedPreferences sharedPreferences) {
    return new BooleanPreference(sharedPreferences,  "auto_read", true);
  }
}