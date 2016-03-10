package com.nshmura.feed.di.module;

import android.app.Application;
import android.content.SharedPreferences;
import com.nshmura.feed.StethoInitializer;
import com.nshmura.feed.data.PreferencesStore;
import com.nshmura.feed.data.web.LdrApi;
import com.nshmura.feed.data.web.LoginApi;
import com.nshmura.feed.data.web.PersistentCookieStore;
import com.nshmura.feed.di.annotations.BackgroundBus;
import com.squareup.moshi.Moshi;
import com.squareup.okhttp.OkHttpClient;
import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;
import java.net.CookieManager;
import java.net.CookiePolicy;
import javax.inject.Singleton;
import retrofit.MoshiConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

@Module public class DataModule {

  @Provides @Singleton SharedPreferences providesSharedPreferences(Application application) {
    return PreferencesStore.getSharedPreferences(application);
  }

  @Provides @Singleton Moshi providesMoshi() {
    return new Moshi.Builder().build();
  }

  @Provides @Singleton OkHttpClient providesOkHttpClient(Application application) {
    OkHttpClient okHttpClient = new OkHttpClient();

    okHttpClient.setCookieHandler(
        new CookieManager(new PersistentCookieStore(application), CookiePolicy.ACCEPT_ALL));

    StethoInitializer.appendNetworkIntercepters(okHttpClient);
    return okHttpClient;
  }

  @Provides @Singleton LoginApi providesLoginApi(OkHttpClient okHttpClient) {
    return new LoginApi(okHttpClient);
  }

  @Provides @Singleton LdrApi providesLdrApi(OkHttpClient okHttpClient, Moshi moshi) {
    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://reader.livedoor.com/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    return retrofit.create(LdrApi.class);
  }

  @Provides @Singleton @BackgroundBus EventBus providesBackgroundBus() {
    return new EventBus();
  }
}