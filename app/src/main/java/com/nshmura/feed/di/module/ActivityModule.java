package com.nshmura.feed.di.module;

import android.app.Activity;
import com.nshmura.feed.di.annotations.PerActivity;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
  private final Activity activity;

  public ActivityModule(Activity activity) {
    this.activity = activity;
  }

  @Provides @PerActivity Activity activity() {
    return this.activity;
  }
}