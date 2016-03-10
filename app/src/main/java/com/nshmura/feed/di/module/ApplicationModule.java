package com.nshmura.feed.di.module;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class ApplicationModule {
  Application mApplication;

  public ApplicationModule(Application application) {
    mApplication = application;
  }

  @Provides @Singleton Application providesApplication() {
    return mApplication;
  }
}
