package com.nshmura.feed;

import android.app.Application;
import android.content.Context;
import com.nshmura.feed.di.ApplicationComponent;
import com.nshmura.feed.di.DaggerApplicationComponent;
import com.nshmura.feed.di.module.ApplicationModule;
import com.nshmura.feed.domain.entity.ThemeType;
import com.nshmura.feed.domain.repository.ThemeRepository;
import com.nshmura.feed.presentation.util.WebViewUtil;
import de.greenrobot.event.EventBus;
import javax.inject.Inject;
import timber.log.Timber;

public class MainApplication extends Application {

  @Inject ThemeRepository themeRepository;
  private ApplicationComponent mainComponent;

  @Override public void onCreate() {
    super.onCreate();
    mainComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    mainComponent.inject(this);

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
      WebViewUtil.setWebContentsDebuggingEnabled(true);
    }

    StethoInitializer.init(this);
  }

  public static MainApplication get(Context context) {
    return (MainApplication) context.getApplicationContext();
  }

  public static EventBus getBus(@SuppressWarnings("unused") Context context) {
    return EventBus.getDefault();
  }

  public ApplicationComponent component() {
    return mainComponent;
  }

  public int getActivityTheme() {
    return themeRepository.getTheme() == ThemeType.DARK ? R.style.DarkTheme : R.style.LightTheme;
  }

  public int getDialogTheme() {
    return themeRepository.getTheme() == ThemeType.DARK ? R.style.DarkTheme_Dialog
        : R.style.LightTheme_Dialog;
  }
}
